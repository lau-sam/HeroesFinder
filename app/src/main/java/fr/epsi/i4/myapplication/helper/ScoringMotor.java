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
    private final InternalStorageFile isf = new InternalStorageFile();

    public ScoringMotor(){

    }

    public ScoringMotor(Context context){
        _characters = isf.getCharactersFromCSVFormat(context);
    }

    //usefull methods
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
            ArrayList<Feature> characterFeatures = it.next().get_characterFeatures();
            Iterator<Feature> itFeature = characterFeatures.iterator();
            while (itFeature.hasNext()){
                Feature currentFeature = itFeature.next();
                if(currentFeature.get_featureLabel().equals(userAnswer.get_featureLabel())){
                    boolean currentChoice = currentFeature.is_featureChoice();
                    if ( currentChoice != userAnswer.is_featureChoice()){
                        it.remove();
                    }
                }
            }
        }
    }

    //getters setters
    public ArrayList<Character> get_characters() {
        return _characters;
    }

    public void set_characters(ArrayList<Character> _characters) {
        this._characters = _characters;
    }

}
