package fr.epsi.i4.myapplication.model;

/**
 * Created by tuannguyen on 14/04/16.
 */
public class Feature {
    private String _featureLabel;
    private String _featureChoice;

    public Feature(){

    }

    public Feature(String featureLabel, String featureChoice){
        this._featureLabel = featureLabel;
        this._featureChoice = featureChoice;
    }

    public String get_featureLabel() {
        return _featureLabel;
    }

    public void set_featureLabel(String _featureLabel) {
        this._featureLabel = _featureLabel;
    }

    public String get_featureChoice() {
        return _featureChoice;
    }

    public void set_featureChoice(String _featureChoice) {
        this._featureChoice = _featureChoice;
    }
}
