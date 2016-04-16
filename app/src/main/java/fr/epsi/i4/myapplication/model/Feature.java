package fr.epsi.i4.myapplication.model;

/**
 * Created by tuannguyen on 14/04/16.
 */
public class Feature {
    private String _featureLabel;
    private boolean _featureChoice;

    public Feature(){

    }

    public Feature(String featureLabel, boolean featureChoice){
        this._featureLabel = featureLabel;
        this._featureChoice = featureChoice;
    }

    public String get_featureLabel() {
        return _featureLabel;
    }

    public void set_featureLabel(String _featureLabel) {
        this._featureLabel = _featureLabel;
    }

    public boolean is_featureChoice() {
        return _featureChoice;
    }

    public void set_featureChoice(boolean _featureChoice) {
        this._featureChoice = _featureChoice;
    }
}
