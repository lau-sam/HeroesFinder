package fr.epsi.i4.myapplication.model;

/**
 * Created by tuannguyen on 14/04/16.
 */
public class Character {
    private String _characterName;
    private Feature _characterFeature;
    private int _characterScore;

    public Character(){

    }

    public Character( String characterName, Feature characterFeature, int characterScore ){
        this._characterName = characterName;
        this._characterFeature = characterFeature;
        this._characterScore = characterScore;
    }

    public String get_characterName() {
        return _characterName;
    }

    public void set_characterName(String _characterName) {
        this._characterName = _characterName;
    }

    public Feature get_characterFeature() {
        return _characterFeature;
    }

    public void set_characterFeature(Feature _characterFeature) {
        this._characterFeature = _characterFeature;
    }
}
