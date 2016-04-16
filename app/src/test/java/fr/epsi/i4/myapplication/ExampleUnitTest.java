package fr.epsi.i4.myapplication;

import org.junit.Test;

import fr.epsi.i4.myapplication.helper.ScoringMotor;

import static org.junit.Assert.*;

/**
 * To work on unit tests, switch the Test Artifact in the Build Variants view.
 */
public class ExampleUnitTest {

    @Test
    public void shouldAdd(){
        int a = 3;
        int b = 4;

        ScoringMotor sm = new ScoringMotor();

        int addResult = sm.add(a,b);
        assertEquals(7,addResult);
    }
}