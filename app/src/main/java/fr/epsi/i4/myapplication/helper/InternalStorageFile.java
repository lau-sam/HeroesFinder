package fr.epsi.i4.myapplication.helper;

import android.content.Context;
import android.content.res.AssetManager;
import android.content.res.Resources;
import android.util.Log;

import java.io.BufferedInputStream;
import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.ArrayList;

import fr.epsi.i4.myapplication.R;

import fr.epsi.i4.myapplication.model.Character;
import fr.epsi.i4.myapplication.model.Feature;

/**
 * Created by tuannguyen on 14/04/16.
 */
public class InternalStorageFile {

    public ArrayList<Character> getCharactersFromCSVFormat(Context context){
        ArrayList<Character> charactersList = new ArrayList<Character>();
        AssetManager assetManager = context.getAssets();
        InputStream inputStream = null;
        try {
            inputStream = assetManager.open("CharactersList.csv");
            BufferedReader reader = new BufferedReader(new InputStreamReader(inputStream));

            String csvLine;
            csvLine = reader.readLine();
            String[] features = csvLine.split(";");
            while ((csvLine = reader.readLine()) != null)
            {
                String[] answers = csvLine.split(";");
                Character character = new Character(answers[0]);
                for(int j = 1 ; j < answers.length; j++){
                    Feature feature;
                    if(answers[j].equals("oui") || answers[j].equals("non")){
                        feature = new Feature(features[j],answers[j]);
                    }
                    else{
                        feature = new Feature(features[j],"oui");
                    }
                    character.addFeature(feature);
                }
                charactersList.add(character);
            }
        }
        catch (IOException ex) {
            throw new RuntimeException("Error in reading CSV file: "+ex);
        }
        finally {
            try {
                inputStream.close();
            }
            catch (IOException e) {
                throw new RuntimeException("Error while closing input stream: "+e);
            }
        }
        return charactersList;
    }
}
