package fr.epsi.i4.myapplication.helper;

import java.lang.reflect.Array;
import java.util.ArrayList;
import java.util.Iterator;

import fr.epsi.i4.myapplication.model.Character;

/**
 * Created by tuannguyen on 14/04/16.
 */
public class ScoringMotor {

    public boolean removeCharacterOnList(Character characterToRemove, ArrayList<Character> characters){

        boolean done = false;

        Iterator<Character> it = characters.iterator();
        while (it.hasNext()) {
            if (it.next().get_characterName().equals(characterToRemove.get_characterName())) {
                it.remove();
                done = true;
                break;
            }
        }
        return done;
    }

}
