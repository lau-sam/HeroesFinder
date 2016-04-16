package fr.epsi.i4.myapplication.model;

/**
 * Created by tuannguyen on 14/04/16.
 */
public class Character {
    private int _characterId;
    private String _characterName;
    private Feature _characterFeature;

    public Character(){

    }

    public Character(int characterId, String characterName, Feature characterFeature){
        this._characterId = characterId;
        this._characterName = characterName;
        this._characterFeature = characterFeature;
    }

    public int get_characterId() {
        return _characterId;
    }

    public void set_characterId(int _characterId) {
        this._characterId = _characterId;
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
