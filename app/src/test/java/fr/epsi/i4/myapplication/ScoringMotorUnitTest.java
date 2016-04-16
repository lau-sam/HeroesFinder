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

        //characterToRemove features set
        ArrayList<Feature> characterToRemoveFeatures = new ArrayList<Feature>();
        characterToRemoveFeatures.add(new Feature("sexeMan",true));
        characterToRemoveFeatures.add(new Feature("redColor",true));
        characterToRemoveFeatures.add(new Feature("haveWeapon",true));
        characterToRemoveFeatures.add(new Feature("bad",true));
        characterToRemoveFeatures.add(new Feature("transforms",true));
        //characterToRemove set
        Character characterToRemove = new Character("Superman",characterToRemoveFeatures, 0);

        //Data to test
        Character superman = new Character("Superman",characterToRemoveFeatures, 10);
        Character batman = new Character("Batman",characterToRemoveFeatures, 10);
        Character hellboy = new Character("Hellboy",characterToRemoveFeatures, 20);
        Character hulk = new Character("Hulk",characterToRemoveFeatures, 30);
        Character ironman = new Character("Ironman",characterToRemoveFeatures, 40);
        Character harrypotter = new Character("HarryPotter",characterToRemoveFeatures, 50);
        Character sangoku = new Character("Sangoku",characterToRemoveFeatures, 60);
        Character shrek = new Character("Shrek",characterToRemoveFeatures, 80);
        Character tombraider = new Character("TombRaider",characterToRemoveFeatures, 10);
        Character catwoman = new Character("Catwoman",characterToRemoveFeatures, 20);


        //list characters still on race
        ArrayList<Character> characters = new ArrayList<Character>();
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

        sm.set_characters(characters);

        boolean done = sm.removeCharacterOnList(characterToRemove);

        assertEquals(true,done);
    }
}