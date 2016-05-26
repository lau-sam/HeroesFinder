package fr.epsi.i4.myapplication.helper;

import android.app.Activity;
import android.content.Context;
import android.util.Log;

import java.lang.reflect.Array;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;

import fr.epsi.i4.myapplication.R;
import fr.epsi.i4.myapplication.model.Character;
import fr.epsi.i4.myapplication.model.Feature;

/**
 * Created by tuannguyen on 14/04/16.
 */

public class ScoringMotor {

    private static final String TAG = "ScoringMotor";
    private ArrayList<Character> _characters;
    private ArrayList<Character> _charactersCopy;
    private  InternalStorageFile isf;
    private ArrayList<String> _questions;
    private ArrayList<Feature> _answerFeatures;
    private boolean _isCharacterMatch;
    private boolean _newCharacterSet;


/**** CONSTRUCTOR + INIT *****/

    public ScoringMotor(){}

    public ScoringMotor(Context context){
        isf = new InternalStorageFile(context);
        _charactersCopy = isf.getCharactersFromCSVFormat(context);
        initQuestions();

    }

    public void initQuestions(){
        _characters = (ArrayList<Character>)_charactersCopy.clone();
        _answerFeatures = new ArrayList<Feature>();
        _isCharacterMatch = true;
        _newCharacterSet = false;

        _questions = new ArrayList<>();
        ArrayList<Feature> features =  _characters.get(1).get_characterFeatures();
        for(Feature feature : features){
            _questions.add(feature.get_featureLabel());
        }
    }


/**** END OF CONSTRUCTOR + INIT *****/


/**** CHARACTER *****/

    /**
     * remove character on this
     * @param characterToRemoveName
     */
    public void removeCharacterOnList(String characterToRemoveName){

        Iterator<Character> it = this._characters.iterator();
        while (it.hasNext()) {
            if (it.next().get_characterName().equals(characterToRemoveName)) {
                it.remove();
                break;
            }
        }
    }

    /**
     * remove character on this
     * @param score
     */
    public void removeCharactersWithScoreHigh(int score){
        Iterator<Character> it = this._characters.iterator();
        while (it.hasNext()) {
            if (it.next().get_characterScore() <= score) {
                it.remove();
            }
        }
    }

    /**
     * only for yes_default or no answer
     * @param userAnswer
     */
    public void removeCharactersFromUserAnswer(Feature userAnswer) {
        Iterator<Character> it = this._characters.iterator();

        while (it.hasNext()) {
            ArrayList<Feature> characterFeatures = it.next().get_characterFeatures();
            Iterator<Feature> itFeature = characterFeatures.iterator();
            while (itFeature.hasNext()) {
                Feature currentFeature = itFeature.next();
                if (currentFeature.get_featureLabel().equals(userAnswer.get_featureLabel())) {
                    String currentChoice = currentFeature.get_featureChoice();
                    if ( !currentChoice.isEmpty() && !currentChoice.equals(userAnswer.get_featureChoice())) {
                        it.remove();
                    }
                }
            }
        }
    }

    public void setUserAnswer(Feature feature){
        _answerFeatures.add(feature);
        if(feature.get_featureChoice().equals("oui")|| feature.get_featureChoice().equals("non")){
            removeCharactersFromUserAnswer(feature);
        }
        else{
            modifyCharactersScore(feature);
        }
        _questions.remove(feature.get_featureLabel());
    }

    public void removeQuestion(Feature feature){
        _questions.remove(feature.get_featureLabel());
    }

    public void modifyCharactersScore(Feature featureAnswer) {
        String featureLabel = featureAnswer.get_featureLabel();
        String userAnswer = featureAnswer.get_featureChoice();
        for (Character character : _characters) {
            Feature feature = findFeatureByLabel(featureLabel, character.get_characterFeatures());
            if (!feature.equals(null)) {
                if (userAnswer.equals(R.string.probably)) {
                    if (feature.get_featureChoice().equals("oui")) {
                        character.set_characterScore(character.get_characterScore() + 1);
                    } else if(feature.get_featureChoice().equals("non")){
                        character.set_characterScore(character.get_characterScore() - 1);
                    }
                } else if (userAnswer.equals(R.string.probably_not)) {
                    if (feature.get_featureChoice().equals("non")) {
                        character.set_characterScore(character.get_characterScore() + 1);
                    } else if(feature.get_featureChoice().equals("oui")){
                        character.set_characterScore((character.get_characterScore() - 1));
                    }
                }
                if (character.get_characterScore() <= -2)
                    _characters.remove(character);
            }
        }
    }

/**** END OF CHARACTER *****/


/**** FEATURE *****/


    /**
     * @param featureLabel feature label to find
     * @param characterFeatures list of current character features
     * @return feature, null if not found in character features
     */
    public Feature findFeatureByLabel(String featureLabel, ArrayList<Feature> characterFeatures) {
        Feature outputFeature = null;
        int i = 0;
        while (i < characterFeatures.size() && outputFeature==null) {
            if (characterFeatures.get(i).get_featureLabel() == featureLabel) {
                outputFeature = characterFeatures.get(i);
            }
            i++;
        }
        return outputFeature;
    }

    public String proposeCharacter(){
        if(_characters.size() != 0){
            return _characters.get(0).get_characterName();
        }
        return "";
    }

    public ArrayList<Feature> getListUserAnswers(){
        return _answerFeatures;
    }

    /**
     * remove the best question in questions still on race : this
     * @return the best question to answer next
     */
    public String nextQuestion(){
        if(_isCharacterMatch){
            String bestQuestion = "";
            double bestEntropie = 0;

            //foreach question
            for(String question : _questions){
                double entropieQuestion=0;
                int yes = 0, no = 0;

                //save "yes_default/no" heroes values
                for (Character character : _characters){
                    //if true
                    Feature feat = findFeatureByLabel(question,character.get_characterFeatures());
                    if( feat != null){
                        if( feat.get_featureChoice().equals("oui")){
                            yes++;
                        }else if(feat.get_featureChoice().equals("non")){
                            no++;
                        }
                    }
                }
                //calculate entropie
                if(yes>no){
                    entropieQuestion = Double.valueOf(no)/Double.valueOf(yes);
                }else
                    entropieQuestion = Double.valueOf(yes)/Double.valueOf(no);

                //save best question
                if(entropieQuestion > bestEntropie){
                    bestEntropie=entropieQuestion;
                    bestQuestion = question;
                }
            }

            return bestQuestion;
        }
        else{
            if(_questions.size() != 0)
                return _questions.get(0);
            else
                return "";
        }
    }

    // on pose des qustions jusqu'à trouver une reponse qui diffère du personnage restant
    public void tryDifferenciateFromLastCharacter(Feature userAnswer){
        _answerFeatures.add(userAnswer);
        String answer = userAnswer.get_featureChoice();
        if(answer.equals("oui") || answer.equals("non")){
            Iterator<Feature> itFeature = _characters.get(0).get_characterFeatures().iterator();
            while (itFeature.hasNext()) {
                    Feature currentFeature = itFeature.next();
                if (currentFeature.get_featureLabel().equals(userAnswer.get_featureLabel())) {
                    if (!currentFeature.get_featureChoice().equals(userAnswer.get_featureChoice())){
                        _newCharacterSet = true;
                    }
                    else{
                        _questions.remove(userAnswer.get_featureLabel());
                    }

                }
            }
        }
    }


/**** END OF FEATURE *****/


/**** GETTERs, SETTERs *****/


    //getters setters
    public ArrayList<Character> get_characters() {
        return _characters;
    }

    public void set_characters(ArrayList<Character> _characters) {
        this._characters = _characters;
    }

    public void set_isCharacterMatch(boolean isCharacterMatch) {
        _isCharacterMatch = isCharacterMatch;
    }

    public ArrayList<Character> get_charactersCopy() {
        return _charactersCopy;
    }

    public void addNewCharacter(Character character){
        _charactersCopy.add(character);
    }

    public boolean is_isCharacterMatch() {
        return _isCharacterMatch;
    }

    public boolean is_newCharacterSet() {
        return _newCharacterSet;
    }

    /**** END OF GETTERs, SETTERs *****/


}
