package fr.epsi.i4.myapplication.helper;

import java.util.ArrayList;
import java.util.Iterator;

import fr.epsi.i4.myapplication.model.Character;
import fr.epsi.i4.myapplication.model.Feature;

/**
 * Created by tuannguyen on 14/04/16.
 */
public class ScoringMotor {

    private ArrayList<Character> _characters;
    private ArrayList<Feature> _features;

    public ScoringMotor(){

    }

    public ScoringMotor(ArrayList<Character> characters, ArrayList<Feature> features){
        this.set_characters(characters);
        this.set_features(features);

    }

    //usefull methods
    public boolean removeCharacterOnList(Character characterToRemove){

        boolean done = false;

        Iterator<Character> it = this._characters.iterator();
        while (it.hasNext()) {
            if (it.next().get_characterName().equals(characterToRemove.get_characterName())) {
                it.remove();
                done = true;
                break;
            }
        }
        return done;
    }

    public ArrayList<Character> get_characters() {
        return _characters;
    }

    public void set_characters(ArrayList<Character> _characters) {
        this._characters = _characters;
    }

    public ArrayList<Feature> get_features() {
        return _features;
    }

    public void set_features(ArrayList<Feature> _features) {
        this._features = _features;
    }
}
