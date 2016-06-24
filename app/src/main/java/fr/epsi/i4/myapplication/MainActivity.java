package fr.epsi.i4.myapplication;

import android.annotation.SuppressLint;
import android.content.pm.ActivityInfo;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.os.Handler;
import android.text.Layout;
import android.util.Log;
import android.view.MotionEvent;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;

import java.lang.reflect.Field;
import java.util.ArrayList;
import java.util.HashMap;

import fr.epsi.i4.myapplication.helper.InternalStorageFile;
import fr.epsi.i4.myapplication.helper.ScoringMotor;
import fr.epsi.i4.myapplication.model.Character;
import fr.epsi.i4.myapplication.model.Feature;

/**
 * An example full-screen activity that shows and hides the system UI (i.e.
 * status bar and navigation/system bar) with user interaction.
 */
public class MainActivity extends AppCompatActivity {
    /**
     * Whether or not the system UI should be auto-hidden after
     * {@link #AUTO_HIDE_DELAY_MILLIS} milliseconds.
     */
    private static final boolean AUTO_HIDE = true;

    /**
     * If {@link #AUTO_HIDE} is set, the number of milliseconds to wait after
     * user interaction before hiding the system UI.
     */
    private static final int AUTO_HIDE_DELAY_MILLIS = 3000;

    /**
     * Some older devices needs a small delay between UI widget updates
     * and a change of the status and navigation bar.
     */
    private static final int UI_ANIMATION_DELAY = 300;
    private static final String TAG = "MainActivity";
    private final Handler mHideHandler = new Handler();
    private View mContentView;
    private final Runnable mHidePart2Runnable = new Runnable() {
        @SuppressLint("InlinedApi")
        @Override
        public void run() {
            // Delayed removal of status and navigation bar

            // Note that some of these constants are new as of API 16 (Jelly Bean)
            // and API 19 (KitKat). It is safe to use them, as they are inlined
            // at compile-time and do nothing on earlier devices.
            mContentView.setSystemUiVisibility(View.SYSTEM_UI_FLAG_LOW_PROFILE
                    | View.SYSTEM_UI_FLAG_FULLSCREEN
                    | View.SYSTEM_UI_FLAG_LAYOUT_STABLE
                    | View.SYSTEM_UI_FLAG_IMMERSIVE_STICKY
                    | View.SYSTEM_UI_FLAG_LAYOUT_HIDE_NAVIGATION
                    | View.SYSTEM_UI_FLAG_HIDE_NAVIGATION);
        }
    };
    private View mControlsView;
    private final Runnable mShowPart2Runnable = new Runnable() {
        @Override
        public void run() {
            // Delayed display of UI elements
            ActionBar actionBar = getSupportActionBar();
            if (actionBar != null) {
                actionBar.show();
            }
            mControlsView.setVisibility(View.VISIBLE);
        }
    };
    private boolean mVisible;
    private final Runnable mHideRunnable = new Runnable() {
        @Override
        public void run() {
            hide();
        }
    };
    /**
     * Touch listener to use for in-layout UI controls to delay hiding the
     * system UI. This is to prevent the jarring behavior of controls going away
     * while interacting with activity UI.
     */
    private final View.OnTouchListener mDelayHideTouchListener = new View.OnTouchListener() {
        @Override
        public boolean onTouch(View view, MotionEvent motionEvent) {
            if (AUTO_HIDE) {
                delayedHide(AUTO_HIDE_DELAY_MILLIS);
            }
            return false;
        }
    };

    // Init helpers
    private ScoringMotor sm;
    private String question = "";
    HashMap<String, Integer> images;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.activity_main);

        mVisible = true;

        mContentView = findViewById(R.id.fullscreen_content);

        TextView mTextView = (TextView) findViewById(R.id.fullscreen_content);
        mTextView.setTextSize(15);
        mTextView.setPadding(0,150,0,0);
        int color = Integer.parseInt("142f3d", 16)+0xFF000000;
        mTextView.setTextColor(color);


        // init scoring motor with characters
        sm = new ScoringMotor(this.getApplicationContext());
        newQuestion();
        //logCharacters();
        Log.e("Début de partie", "______________________________________________");

        images = new HashMap<String, Integer>();
        for(Character character : sm.get_characters()){
            String nomPerso = bienNommer(character.get_characterName());
            images.put( nomPerso, getResId(nomPerso, R.drawable.class));
        }
    }

    private String bienNommer(String input){
        return input.replaceAll(" ","").toLowerCase();
    }

    public static int getResId(String resName, Class<?> c) {

        try {
            Field idField = c.getDeclaredField(resName);
            return idField.getInt(idField);
        } catch (Exception e) {
            e.printStackTrace();
            return -1;
        }
    }

    @Override
    protected void onPostCreate(Bundle savedInstanceState) {
        super.onPostCreate(savedInstanceState);

        // Trigger the initial hide() shortly after the activity has been
        // created, to briefly hint to the user that UI controls
        // are available.
        delayedHide(100);
        setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_PORTRAIT);
    }

    private void toggle() {
        if (mVisible) {
            hide();
        } else {
            show();
        }
    }

    private void hide() {
        // Hide UI first
        ActionBar actionBar = getSupportActionBar();
        if (actionBar != null) {
            actionBar.hide();
        }

        mVisible = false;

        // Schedule a runnable to remove the status and navigation bar after a delay
        mHideHandler.removeCallbacks(mShowPart2Runnable);
        mHideHandler.postDelayed(mHidePart2Runnable, UI_ANIMATION_DELAY);
    }

    @SuppressLint("InlinedApi")
    private void show() {
        // Show the system bar
        mContentView.setSystemUiVisibility(View.SYSTEM_UI_FLAG_LAYOUT_FULLSCREEN
                | View.SYSTEM_UI_FLAG_LAYOUT_HIDE_NAVIGATION);
        mVisible = true;

        // Schedule a runnable to display UI elements after a delay
        mHideHandler.removeCallbacks(mHidePart2Runnable);
        mHideHandler.postDelayed(mShowPart2Runnable, UI_ANIMATION_DELAY);
    }

    /**
     * Schedules a call to hide() in [delay] milliseconds, canceling any
     * previously scheduled calls.
     */
    private void delayedHide(int delayMillis) {
        mHideHandler.removeCallbacks(mHideRunnable);
        mHideHandler.postDelayed(mHideRunnable, delayMillis);
    }

    public void newQuestion(){
        if(!sm.is_newCharacterSet()){
            question = sm.nextQuestion();
            TextView mTextView = (TextView) findViewById(R.id.fullscreen_content);
            if(!question.isEmpty()){
                mTextView.setText(question);
                sm.setNbQuestionsPosees(sm.getNbQuestionsPosees()+1);
            }
            else{
                setCharacterProposal();
            }
        }
    }

    private void changeLayout(int idLayouDisplayed){
        LinearLayout layout = (LinearLayout) findViewById(R.id.layout_answers);
        layout.setVisibility(View.GONE);
        layout = (LinearLayout) findViewById(R.id.layout_proposal);
        layout.setVisibility(View.GONE);
        layout = (LinearLayout) findViewById(R.id.layout_final);
        layout.setVisibility(View.GONE);
        layout = (LinearLayout) findViewById(R.id.layout_newCharacterEntry);
        layout.setVisibility(View.GONE);
        layout = (LinearLayout) findViewById(R.id.layout_continuer);
        layout.setVisibility(View.GONE);
        layout = (LinearLayout) findViewById(R.id.layout_choixProposerQuestion);
        layout.setVisibility(View.GONE);
        layout = (LinearLayout) findViewById(R.id.layout_proposerQuestion);
        layout.setVisibility(View.GONE);

        LinearLayout layoutToDisplay = (LinearLayout) findViewById(idLayouDisplayed);
        layoutToDisplay.setVisibility(View.VISIBLE);
    }

    private void setCharacterProposal() {
        TextView mTextView = (TextView) findViewById(R.id.fullscreen_content);

        String characterName = sm.proposeCharacter();
        mTextView.setText(" Vous avez pensé à "+ characterName);
        changeLayout(R.id.layout_proposal);
        ImageView imageView = (ImageView) findViewById(R.id.imageViewResult);
        imageView.setImageResource(0);


        //convert reponse in good format for my ViewResult without Uppercase
        String reponse = bienNommer(characterName);
        try{
            imageView.setImageResource(images.get(reponse).intValue());
        }
        catch(Exception e){}
    }

    private void displayStats(){
        changeLayout(R.id.layout_final);
        TextView mTextView = (TextView) findViewById(R.id.fullscreen_content);
        mTextView.setText("Trouvé en " + sm.getNbQuestionsPosees() + " questions !");
    }

    public void logCharacters(){
        String text ="";
        for (Character character : sm.get_characters()){
            text += character.get_characterName()+";";
        }
        Log.e("Characters : " , text);
    }

    public void saisirNouveauPersonnage(){
        changeLayout(R.id.layout_newCharacterEntry);
        TextView mTextView = (TextView) findViewById(R.id.fullscreen_content);
        mTextView.setText("Tu m'as posé une colle ! Entre le nom du nouveau personnage :");
    }

    public void onAnswerButtonClick(View view) {

        Feature userAnswer;

        if(sm.is_isCharacterMatch()){
            switch (view.getId()) {

                case R.id.yesBtn :
                    Log.e(question, "oui");
                    userAnswer = new Feature(question,"oui");

                    sm.setUserAnswer(userAnswer);
                    newQuestion();
                    //logCharacters();
                    break;

                case R.id.noBtn :
                    Log.e(question, "non");
                    userAnswer = new Feature(question,"non");
                    sm.setUserAnswer(userAnswer);
                    newQuestion();
                    //logCharacters();
                    break;

                case R.id.don_t_knowBtn :
                    Log.e(question, "je ne sais pas");
                    userAnswer = new Feature(question,"dont know");
                    sm.removeQuestion(userAnswer);
                    newQuestion();
                    //logCharacters();
                    break;

                case R.id.probablyBtn :
                    Log.e(question, "probablement");
                    userAnswer = new Feature(question,"Probably");
                    sm.setUserAnswer(userAnswer);
                    newQuestion();
                    //logCharacters();
                    break;

                case R.id.probably_notBtn :
                    Log.e(question, "probablement pas");
                    userAnswer = new Feature(question,"ProbablyNot");
                    sm.setUserAnswer(userAnswer);
                    newQuestion();
                    //logCharacters();
                    break;

                case R.id.yesProposalBtn :
                    displayStats();

                    break;
                case R.id.noProposalBtn :
                    //changeLayout(R.id.layout_answers);
                    sm.set_isCharacterMatch(false);
                    //changeLayout(R.id.layout_continuer);
                    TextView mTextView = (TextView) findViewById(R.id.fullscreen_content);
                    mTextView.setText("Choisissez une option :");


                    if(sm.get_characters().size() > 1){
                        sm.get_characters().remove(1);
                        setCharacterProposal();
                    }
                    else{
                        changeLayout(R.id.layout_continuer);
                        sm.set_isCharacterMatch(false);
                        //newQuestion();
                    }

                    //newQuestion();
                    break;
                case R.id.resetBtn :
                    changeLayout(R.id.layout_answers);
                    sm.initQuestions();
                    newQuestion();
                    break;
                case R.id.resetBtnFinal :
                    EditText editText = (EditText) findViewById(R.id.characterName);
                    String characterName = editText.getText().toString();

                    changeLayout(R.id.layout_answers);
                    Character newCharacter = new Character(characterName);

                    InternalStorageFile isf = new InternalStorageFile(getApplicationContext());
                    newCharacter.set_characterFeatures(isf.getFeatureLabels(), sm.getListUserAnswers());
                    sm.addNewCharacter(newCharacter);
                    sm.initQuestions();
                    newQuestion();
                    break;
                default: Log.e(TAG,"invalid button");
                    break;
            }
        }

        // on a éliminé tous les personnages et on essaie de distinguer le dernier perso proposé par des réponses qui diffèrent
        else{
            switch (view.getId()) {

                case R.id.yesBtn :
                    Log.e(question, "oui");
                    userAnswer = new Feature(question,"oui");

                    sm.tryDifferenciateFromLastCharacter(userAnswer);
                    if(!sm.is_newCharacterSet())
                        newQuestion();
                    else
                        saisirNouveauPersonnage();

                    //logCharacters();
                    break;

                case R.id.noBtn :
                    Log.e(question, "non");
                    userAnswer = new Feature(question,"non");

                    sm.tryDifferenciateFromLastCharacter(userAnswer);
                    if(!sm.is_newCharacterSet())
                        newQuestion();
                    else
                        saisirNouveauPersonnage();


                    //logCharacters();
                    break;

                case R.id.don_t_knowBtn :
                    Log.e(question, "je ne sais pas");
                    userAnswer = new Feature(question,"dont know");
                    sm.removeQuestion(userAnswer);
                    newQuestion();
                    //logCharacters();
                    break;


                case R.id.probablyBtn :
                    Log.e(question, "probablement");
                    userAnswer = new Feature(question,"Probably");
                    sm.removeQuestion(userAnswer);
                    newQuestion();
                    //logCharacters();
                    break;

                case R.id.probably_notBtn :
                    Log.e(question, "probablement pas");
                    userAnswer = new Feature(question,"ProbablyNot");
                    sm.removeQuestion(userAnswer);
                    newQuestion();
                    //logCharacters();
                    break;

                case R.id.yesProposalBtn :
                    displayStats();

                    break;
                case R.id.noProposalBtn :
                    if(sm.get_characters().size() > 1){
                        sm.get_characters().remove(1);
                        setCharacterProposal();
                    }
                    else{
                        changeLayout(R.id.layout_answers);
                        sm.set_isCharacterMatch(false);
                        newQuestion();
                    }
                    break;
                case R.id.resetBtn :
                    changeLayout(R.id.layout_answers);
                    sm.initQuestions();
                    newQuestion();
                    break;
                case R.id.resetBtnFinal :
                    EditText editText = (EditText) findViewById(R.id.characterName);
                    //String characterName = editText.getText().toString();
                    sm.set_newCharacterName(editText.getText().toString());
                    changeLayout(R.id.layout_choixProposerQuestion);

                    TextView mTextView = (TextView) findViewById(R.id.fullscreen_content);
                    mTextView.setText("Choisissez une option :");
                    /*Character newCharacter = new Character(characterName);

                    InternalStorageFile isf = new InternalStorageFile(getApplicationContext());
                    newCharacter.set_characterFeatures(isf.getFeatureLabels(), sm.getListUserAnswers());
                    sm.addNewCharacter(newCharacter);*/
                    //sm.initQuestions();
                    //newQuestion();
                    break;
                case R.id.continuerBtn :
                    changeLayout(R.id.layout_answers);
                    newQuestion();
                    break;
                case R.id.arreterBtn :
                    saisirNouveauPersonnage();
                    break;
                case R.id.proposerBtn :
                    changeLayout(R.id.layout_proposerQuestion);
                    TextView mTextVieww = (TextView) findViewById(R.id.fullscreen_content);
                    mTextVieww.setText("Entrez une question déterminante (réponse = oui) :");
                    break;
                case R.id.recommencerBtn :
                    Character newCharacter = new Character(sm.get_newCharacterName());

                    InternalStorageFile isf = new InternalStorageFile(getApplicationContext());
                    newCharacter.set_characterFeatures(isf.getFeatureLabels(), sm.getListUserAnswers());
                    sm.addNewCharacter(newCharacter);

                    changeLayout(R.id.layout_answers);
                    sm.initQuestions();
                    newQuestion();
                    break;
                case R.id.proposerOkBtn :
                    proposerQuestion();

                    Character newCharacter2 = new Character(sm.get_newCharacterName());

                    InternalStorageFile isf2 = new InternalStorageFile(getApplicationContext());
                    newCharacter2.set_characterFeatures(isf2.getFeatureLabels(), sm.getListUserAnswers());

                    sm.addNewCharacter(newCharacter2);

                    changeLayout(R.id.layout_answers);
                    sm.initQuestions();
                    newQuestion();
                    break;
                case R.id.proposerAutreBtn :
                    proposerQuestion();
                    break;

                default: Log.e(TAG,"invalid button");
                    break;
            }
        }
    }

    private void proposerQuestion(){
        TextView mTextView = (TextView) findViewById(R.id.fullscreen_content);
        mTextView.setText("Entrez une question déterminante (réponse = oui) :");
        EditText editTextNewQuestion = (EditText) findViewById(R.id.proposerLabel);
        String newQuestion = editTextNewQuestion.getText().toString();
        sm.setUserAnswer(new Feature(newQuestion,"oui"));
        sm.addNewQuestion(newQuestion);
        editTextNewQuestion.setText("");
    }
}
