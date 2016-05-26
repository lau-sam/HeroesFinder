package fr.epsi.i4.myapplication.helper;

import android.app.Activity;
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
import java.io.FileWriter;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.util.ArrayList;

import fr.epsi.i4.myapplication.MainActivity;
import fr.epsi.i4.myapplication.R;

import fr.epsi.i4.myapplication.model.Character;
import fr.epsi.i4.myapplication.model.Feature;

/**
 * Created by tuannguyen on 14/04/16.
 */
public class InternalStorageFile {

    Context _context;

    public InternalStorageFile(Context context){
        _context = context;
    }

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

    public void insertNewCharacter(String characterName, ArrayList<Feature> newCharacterFeatures){
        ArrayList<String> listFeatures = getFeatureLabels();
        PrintWriter csvWriter;
        try {
        /*1. declare stringBuffer*/
            StringBuffer oneLineStringBuffer = new StringBuffer();

            File file = new File("CharactersList.csv");

            csvWriter = new PrintWriter(new FileWriter(file, true));

        /*2. append to stringBuffer*/
            oneLineStringBuffer.append("\n");
            oneLineStringBuffer.append(characterName + ";");
            for(String featureString : listFeatures){
                for(Feature feature : newCharacterFeatures){
                    if(featureString.equals(feature.get_featureLabel())){
                        String featureChoice = feature.get_featureChoice();
                        oneLineStringBuffer.append(featureChoice+";");
                        break;
                    }
                }
            }
            //oneLineStringBuffer.deleteCharAt(oneLineStringBuffer.length()-1);

        /*3. print to csvWriter*/
            csvWriter.print(oneLineStringBuffer);

            csvWriter.close();

        } catch (Exception e) {
            e.printStackTrace();
        }
    }


    public void writeFile(String characterName, ArrayList<Feature> newCharacterFeatures){
        ArrayList<String> listFeatures = getFeatureLabels();
        String text = "\n"+ characterName + ";";

        for(String featureString : listFeatures){
            for(Feature feature : newCharacterFeatures){
                if(featureString.equals(feature.get_featureLabel())){
                    String featureChoice = feature.get_featureChoice();
                    text += (featureChoice+";");
                    break;
                }
            }
        }

        FileOutputStream fos = null;

        try {
            fos = _context.openFileOutput("CharactersList.csv", _context.MODE_PRIVATE);
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        }

        try {
            fos.write(text.getBytes());
            fos.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }


    public ArrayList<String> getFeatureLabels(){
        ArrayList<Character> characters = getCharactersFromCSVFormat(_context);
        ArrayList<String> featureLabels = new ArrayList<String>();
        for(Feature feature : characters.get(1).get_characterFeatures()){
            featureLabels.add(feature.get_featureLabel());
        }
        return featureLabels;
    }
}
