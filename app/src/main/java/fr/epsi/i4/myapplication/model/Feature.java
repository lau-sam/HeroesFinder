package fr.epsi.i4.myapplication.model;

/**
 * Created by tuannguyen on 14/04/16.
 */
public class Feature {
    private String _featureLabel;
    private boolean _featureChoice;
    private int _id;

    public Feature(){

    }

    public Feature(String featureLabel, boolean featureChoice, int id){
        this._featureLabel = featureLabel;
        this._featureChoice = featureChoice;
        this._id = id;
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

    public int get_id() {
        return _id;
    }

    public void set_id(int _id) {
        this._id = _id;
    }
}
