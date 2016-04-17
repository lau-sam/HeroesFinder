package fr.epsi.i4.myapplication.helper;

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

    public ScoringMotor(){

    }

    public ScoringMotor(ArrayList<Character> characters){
        this.set_characters(characters);

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

    public void removeCharactersNotInUserAnswer(Feature userAnswer){

        Iterator<Character> itCharacters = this._characters.iterator();

        while (itCharacters.hasNext()) {
            Character currentCharacter = itCharacters.next();
            ArrayList<Feature> characterFeatures = currentCharacter.get_characterFeatures();

            if(characterFeatures.contains(userAnswer)) {
                itCharacters.remove();
            }
        }
    }

    public ArrayList<Character> get_characters() {
        return _characters;
    }

    public void set_characters(ArrayList<Character> _characters) {
        this._characters = _characters;
    }

}
