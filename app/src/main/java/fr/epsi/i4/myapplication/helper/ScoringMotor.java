package fr.epsi.i4.myapplication.helper;

import android.content.Context;
import android.util.Log;

import java.util.ArrayList;
import java.util.Iterator;

import fr.epsi.i4.myapplication.model.Character;
import fr.epsi.i4.myapplication.model.Feature;

/**
 * Created by tuannguyen on 14/04/16.
 */
public class ScoringMotor {

    private static final String TAG = "ScoringMotor";
    private ArrayList<Character> _characters;
    private ArrayList<Feature> _missingFeatures;
    private final InternalStorageFile isf = new InternalStorageFile();

    public ScoringMotor(){

    }

    public ScoringMotor(Context context){
        _characters = isf.getCharactersFromCSVFormat(context);
        _missingFeatures = isf.getFeaturesList(context);
    }

    //usefull methods

    public Feature findFeatureById(int featureId){
        Feature outputFeature = null;
        boolean featureFound = false;
        for(int i =0; i< _missingFeatures.size() && featureFound == false;i++){
            if(_missingFeatures.get(i).get_id() == featureId){
                outputFeature = _missingFeatures.get(i);
                featureFound = true;
            }
        }
        return outputFeature;
    }

    public void deleteFeatureById(int featureId){
        boolean featureFound = false;
        for(int i =0; i< _missingFeatures.size() && featureFound == false;i++){
            if(_missingFeatures.get(i).get_id() == featureId){
                _missingFeatures.remove(i);
                featureFound = true;
            }
        }
    }


    public int nextQuestion(){
        // this is a ranking for the features. The best feature is choosed.
        ArrayList<ArrayList<Integer>> featurePoints = new ArrayList<ArrayList<Integer>>();

        for(Feature feature : _missingFeatures){
            int howManyTrue = 0;
            int howManyFalse = 0;
            for(Character character : _characters){
                if(character.findFeatureById(feature.get_id()).is_featureChoice())
                    howManyTrue ++;
                else
                    howManyFalse ++;
            }
            ArrayList<Integer> array = new ArrayList<Integer>();
            array.add(feature.get_id());
            array.add(howManyFalse * howManyTrue);
            featurePoints.add(array);
        }
        int idBestQuestion = 0;
        Integer mostPoints = 0;
        for(ArrayList arrayList : featurePoints){
            if((Integer)arrayList.get(1) > mostPoints){
                idBestQuestion = (Integer)arrayList.get(0);
                mostPoints = (Integer)arrayList.get(1);
            }
        }
        return idBestQuestion;
    }

    public void removeCharacterOnList(String characterToRemoveName){

        Iterator<Character> it = this._characters.iterator();
        while (it.hasNext()) {
            if (it.next().get_characterName().equals(characterToRemoveName)) {
                it.remove();
                break;
            }
        }
    }

    public void removeCharactersWithScoreHigh(int score){
        Iterator<Character> it = this._characters.iterator();
        while (it.hasNext()) {
            if (it.next().get_characterScore() <= score) {
                it.remove();
            }
        }
    }

    public void removeCharactersFromUserAnswer(Feature userAnswer){
        Iterator<Character> it = this._characters.iterator();

        while (it.hasNext()) {
            Character character = it.next();
            if (character.findFeatureById(userAnswer.get_id()).is_featureChoice() != userAnswer.is_featureChoice()) {
                it.remove();
            }
        }
        deleteFeatureById(userAnswer.get_id());
    }

    //getters setters
    public ArrayList<Character> get_characters() {
        return _characters;
    }

    public void set_characters(ArrayList<Character> _characters) {
        this._characters = _characters;
    }

}
