package fr.epsi.i4.myapplication;

import org.junit.Test;

import java.util.ArrayList;

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

        //  GIVEN

        //features set
        ArrayList<Feature> features = new ArrayList<Feature>();
        features.add(new Feature("sexeMan",true));
        features.add(new Feature("redColor",true));
        features.add(new Feature("haveWeapon",true));
        features.add(new Feature("bad",true));
        features.add(new Feature("transforms",true));
        //set
        Character characterToRemove = new Character("Superman",features, 0);

        //Data to test
        Character superman = new Character("Superman",features, 10);
        Character batman = new Character("Batman",features, 10);
        Character hellboy = new Character("Hellboy",features, 20);
        Character hulk = new Character("Hulk",features, 30);
        Character ironman = new Character("Ironman",features, 40);
        Character harrypotter = new Character("HarryPotter",features, 50);
        Character sangoku = new Character("Sangoku",features, 60);
        Character shrek = new Character("Shrek",features, 80);
        Character tombraider = new Character("TombRaider",features, 10);
        Character catwoman = new Character("Catwoman",features, 20);
        
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

        //WHEN
        int startSize = sm.get_characters().size();
        sm.removeCharacterOnList(characterToRemove);

        //THEN
        assertEquals( "10 9", startSize + " " + characters.size() );
    }

    @Test
    public void shouldRemoveCharactersWithHighScore(){

        //GIVEN
        int score = 5;
        //features set
        ArrayList<Feature> features = new ArrayList<Feature>();
        features.add(new Feature("sexeMan",true));
        features.add(new Feature("redColor",true));
        features.add(new Feature("haveWeapon",true));
        features.add(new Feature("bad",true));
        features.add(new Feature("transforms",true));

        //score <= 5
        Character superman = new Character("Superman",features, 1);
        Character batman = new Character("Batman",features, 3);
        Character hellboy = new Character("Hellboy",features, 4);
        Character hulk = new Character("Hulk",features, 5);

        //score > 5
        Character ironman = new Character("Ironman",features, 6);
        Character harrypotter = new Character("HarryPotter",features, 7);
        Character sangoku = new Character("Sangoku",features, 9);
        Character shrek = new Character("Shrek",features, 6);
        Character tombraider = new Character("TombRaider",features, 10);
        Character catwoman = new Character("Catwoman",features, 7);


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
        int startSize = sm.get_characters().size();

        //WHEN
        sm.removeCharactersWithScoreHigh(score);

        //THEN
        assertEquals( "10 6", startSize + " " + characters.size() );
    }

    @Test
    public void shouldRemoveCharactersNotInUserAnswer(){

        //GIVEN
        Feature sexeMan = new Feature("sexeMan",true);
        Feature redColor = new Feature("redColor",true);
        //featuresIn set
        ArrayList<Feature> featuresIn = new ArrayList<Feature>();
        featuresIn.add(sexeMan);
        featuresIn.add(redColor);

        //featuresOut1 set
        ArrayList<Feature> featuresOut1 = new ArrayList<Feature>();
        featuresOut1.add(new Feature("sexeMan",false));
        featuresOut1.add(new Feature("redColor",true));

        //featuresOut2 set
        ArrayList<Feature> featuresOut2 = new ArrayList<Feature>();
        featuresOut2.add(new Feature("sexeMan",false));
        featuresOut2.add(new Feature("redColor",false));



        //4 characters in
        Character superman = new Character("Superman",featuresIn, 1);
        Character batman = new Character("Batman",featuresIn, 3);
        Character hellboy = new Character("Hellboy",featuresIn, 4);
        Character hulk = new Character("Hulk",featuresIn, 5);

        //4 characters filter by featuresOut1
        Character ironman = new Character("Ironman",featuresIn, 6);
        Character harrypotter = new Character("HarryPotter",featuresIn, 7);
        Character sangoku = new Character("Sangoku",featuresIn, 9);
        Character shrek = new Character("Shrek",featuresIn, 6);

        //2 characters filter by featuresOut2
        Character tombraider = new Character("TombRaider",featuresIn, 10);
        Character catwoman = new Character("Catwoman",featuresIn, 7);



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

        //WHEN
        int startSize = sm.get_characters().size();
        sm.removeCharactersNotInUserAnswer(sexeMan);

        int middleSize = sm.get_characters().size();
        sm.removeCharactersNotInUserAnswer(redColor);

        //THEN
        assertEquals( "10 6 4", startSize + " " + middleSize + " " + characters.size() );
    }
}