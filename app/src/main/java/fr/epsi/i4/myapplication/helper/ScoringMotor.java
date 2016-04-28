package fr.epsi.i4.myapplication.helper;

import android.content.Context;
import android.util.Log;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;

import fr.epsi.i4.myapplication.model.Character;
import fr.epsi.i4.myapplication.model.Feature;

/**
 * Created by tuannguyen on 14/04/16.
 */

public class ScoringMotor {

    private static final String TAG = "ScoringMotor";
    private ArrayList<Character> _characters;
    private final InternalStorageFile isf = new InternalStorageFile();
    private ArrayList<String> _questions;


/**** CONSTRUCTOR + INIT *****/

    public ScoringMotor(){}

    public ScoringMotor(Context context){
        _characters = isf.getCharactersFromCSVFormat(context);
        initQuestions();

    }

    private void initQuestions(){
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
                    boolean currentChoice = currentFeature.is_featureChoice();
                    if (currentChoice != userAnswer.is_featureChoice()) {
                        it.remove();
                    }
                }
            }
        }
    }

    // TODO //for DontKnow, Probably, ProbablyNot
    public void modifyCharactersScore(String featureLabel, String userAnswer){
        for(Character character : _characters){

            if(userAnswer == "Probably"){
                if(!findFeatureByLabel(featureLabel,character.get_characterFeatures()).equals(null)
                        && findFeatureByLabel(featureLabel,character.get_characterFeatures()).is_featureChoice() == true){
                    character.set_characterScore(character.get_characterScore()+1);
                }
            }else if(userAnswer == "ProbablyNot"){
                if(!findFeatureByLabel(featureLabel,character.get_characterFeatures()).equals(null)
                        && findFeatureByLabel(featureLabel,character.get_characterFeatures()).is_featureChoice() == false){
                    character.set_characterScore(character.get_characterScore()+1);
                }
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

    /**
     * remove the best question in questions still on race : this
     * @return the best question to answer next
     */
    public String nextQuestion(){
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
                if( !feat.equals(null) && feat.is_featureChoice() == true){
                    yes++;
                }else no++;
            }
        //calculate entropie
            if(yes>no){
                entropieQuestion = Double.valueOf(no)/Double.valueOf(yes);
            }else entropieQuestion = Double.valueOf(yes)/Double.valueOf(no);

        //save best question
            if(bestEntropie<entropieQuestion){
                bestEntropie=entropieQuestion;
                bestQuestion = question;
            }
            Log.e(TAG,String.valueOf(entropieQuestion));
        }

        return bestQuestion;
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


/**** END OF GETTERs, SETTERs *****/


}
