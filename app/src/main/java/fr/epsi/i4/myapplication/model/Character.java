package fr.epsi.i4.myapplication.model;

import java.util.ArrayList;

/**
 * Created by tuannguyen on 14/04/16.
 */
public class Character {
    private String _characterName;
    private ArrayList<Feature> _characterFeatures;
    private int _characterScore;

    public Character(){

    }

    public Character( String characterName, ArrayList<Feature> characterFeatures, int characterScore ){
        this.set_characterName(characterName);
        this.set_characterFeatures(characterFeatures);
        this.set_characterScore(characterScore);
    }

    public Character( String characterName){
        _characterName = characterName;
        _characterFeatures = new ArrayList<>();
        _characterScore = 0;
    }

    public String get_characterName() {
        return _characterName;
    }

    public void set_characterName(String _characterName) {
        this._characterName = _characterName;
    }

    public ArrayList<Feature> get_characterFeatures() {
        return _characterFeatures;
    }

    public void set_characterFeatures(ArrayList<Feature> _characterFeatures) {
        this._characterFeatures = _characterFeatures;
    }

    public void set_characterFeatures(ArrayList<String> featureStrings, ArrayList<Feature> answerFeatures){
        ArrayList<Feature> characterFeatures = new ArrayList<Feature>();
        for(String featureString : featureStrings){
            Feature currentFeature = null;
            boolean featureFound = false;
            for(Feature feature : answerFeatures){
                if(featureString.equals(feature.get_featureLabel())){
                    if(feature.get_featureChoice().equals("oui") || feature.get_featureChoice().equals("non")){
                        currentFeature = feature;
                        featureFound = true;
                    }
                    break;
                }
            }
            if(!featureFound){
                characterFeatures.add(new Feature(featureString, ""));
            }
            else{
                characterFeatures.add(currentFeature);
            }
        }
        _characterFeatures = (ArrayList<Feature>) characterFeatures.clone();
    }

    public int get_characterScore() {
        return _characterScore;
    }

    public void set_characterScore(int _characterScore) {
        this._characterScore = _characterScore;
    }

    public void addFeature(Feature feature){
        _characterFeatures.add(feature);
    }
}
