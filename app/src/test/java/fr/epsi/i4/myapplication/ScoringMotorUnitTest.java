package fr.epsi.i4.myapplication;

import org.junit.Test;

import java.util.ArrayList;
import java.util.List;

import fr.epsi.i4.myapplication.helper.ScoringMotor;
import fr.epsi.i4.myapplication.model.Character;
import fr.epsi.i4.myapplication.model.Feature;

import static org.junit.Assert.*;

/**
 * To work on unit tests, switch the Test Artifact in the Build Variants view.
 */
public class ScoringMotorUnitTest {

    ScoringMotor sm = new ScoringMotor();

    @Test
    public void shouldRemoveCharacterOnList(){

        //character to test
        Character characterToRemove = new Character("Superman",new Feature("sexeMan",true), 0);

        //list characters still on race
        ArrayList<Character> characters = new ArrayList<Character>();

        //Data to test
        Character superman = new Character("Superman",new Feature("sexeMan",true), 10);
        Character batman = new Character("Batman",new Feature("sexeMan",true), 10);
        Character hellboy = new Character("Hellboy",new Feature("sexeMan",true), 20);
        Character hulk = new Character("Hulk",new Feature("sexeMan",true), 30);
        Character ironman = new Character("Ironman",new Feature("sexeMan",true), 40);
        Character harrypotter = new Character("HarryPotter",new Feature("sexeMan",true), 50);
        Character sangoku = new Character("Sangoku",new Feature("sexeMan",true), 60);
        Character shrek = new Character("Shrek",new Feature("sexeMan",true), 80);
        Character tombraider = new Character("TombRaider",new Feature("sexeMan",false), 10);
        Character catwoman = new Character("Catwoman",new Feature("sexeMan",false), 20);

        characters.add(batman);
        characters.add(hellboy);
        characters.add(hulk);
        characters.add(ironman);
        characters.add(harrypotter);
        characters.add(sangoku);
        characters.add(superman);
        characters.add(shrek);
        characters.add(tombraider);
        characters.add(catwoman);

        boolean done = sm.removeCharacterOnList(characterToRemove, characters);

        assertEquals(true,done);
    }
}