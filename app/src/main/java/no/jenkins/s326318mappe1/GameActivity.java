package no.jenkins.s326318mappe1;

import android.content.Context;
import android.content.DialogInterface;
import android.content.SharedPreferences;
import android.content.res.Configuration;
import android.os.Bundle;

import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.android.material.snackbar.Snackbar;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

import android.os.PersistableBundle;
import android.preference.PreferenceManager;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;
import java.util.Locale;

public class GameActivity extends AppCompatActivity {

    ArrayList<MathQuestion> prefLengthMathQuestions = new ArrayList<>();
    int currentQuestion = 0;
    int score = 0;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_game);
        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        // Create random math questions
        createRandomMathArrays();
        // initiate score screen
        scoreScreen();
        // Game Launch
        gamePlay();
        /* Listener for knappene*/
        startKeyListeners();
    }

    public void createRandomMathArrays() {
        // Retrieves selected game length value from preferences. Default value is 5
        SharedPreferences sharedPreferences = PreferenceManager.getDefaultSharedPreferences(this);
        String gameLengthPreference =  sharedPreferences.getString("preference_number_of_questions", "5");
        int gameLength = Integer.parseInt(gameLengthPreference);

        // Create String arrays from XML String arrays
        String[] questions = getResources().getStringArray(R.array.questions);
        String[] answers = getResources().getStringArray(R.array.answers);

        ArrayList<MathQuestion> mathQuestions = new ArrayList<>();

        //add questions and answers to arraylist.
        for(int i = 0; i< 25; i++){
            String question = questions[i];
            String answer = answers[i];
            mathQuestions.add(new MathQuestion(question, answer));
        }

        // Randomize arraylist
        Collections.shuffle(mathQuestions);

        // Create arraylist with preferred length
        for(int i = 0; i < gameLength; i++){
            MathQuestion oneQuestion = mathQuestions.get(i);
            prefLengthMathQuestions.add(oneQuestion);
        }

        // Log test
        String  logArray = "";
        for(MathQuestion oneQuestion : prefLengthMathQuestions) {
            logArray += "Q: "+ oneQuestion.getQuestion()+" A: "+oneQuestion.getAnswer()+"\n";
        }
        Log.d("Matharray:", logArray);
    }

    public void gamePlay(){
        String questionText = getResources().getString(R.string.question_field_text);
        TextView mathQuestionView = findViewById(R.id.math_question_view);
        mathQuestionView.setText(questionText +" "+ "\n" + prefLengthMathQuestions.get(currentQuestion).getQuestion());
    }

    public void scoreScreen(){
        // NB denne kodebiten finnes også i create math array. burde kanskje kuttes eller gjøres bedre
        // Retrieves selected game length value from preferences. Default value is 5
        SharedPreferences sharedPreferences = PreferenceManager.getDefaultSharedPreferences(this);
        String gameLengthPreference =  sharedPreferences.getString("preference_number_of_questions", "5");
        String scoreScreenTxt = getResources().getString(R.string.score_txt);
        int gameLength = Integer.parseInt(gameLengthPreference);

        TextView scoreView = findViewById(R.id.in_game_stats);
        scoreView.setText(scoreScreenTxt+" "+score + " / " + gameLength+" ");
    }

    public void startKeyListeners(){
        findViewById(R.id.key0).setOnClickListener(view -> addKeyInput("0"));
        findViewById(R.id.key1).setOnClickListener(view -> addKeyInput("1"));
        findViewById(R.id.key2).setOnClickListener(view -> addKeyInput("2"));
        findViewById(R.id.key3).setOnClickListener(view -> addKeyInput("3"));
        findViewById(R.id.key4).setOnClickListener(view -> addKeyInput("4"));
        findViewById(R.id.key5).setOnClickListener(view -> addKeyInput("5"));
        findViewById(R.id.key6).setOnClickListener(view -> addKeyInput("6"));
        findViewById(R.id.key7).setOnClickListener(view -> addKeyInput("7"));
        findViewById(R.id.key8).setOnClickListener(view -> addKeyInput("8"));
        findViewById(R.id.key9).setOnClickListener(view -> addKeyInput("9"));
        findViewById(R.id.answer_key).setOnClickListener(view -> checkGamePosition());
        findViewById(R.id.delete_key).setOnClickListener(view -> deleteKeyInput());
    }

    public void addKeyInput(String keyInput) {
        TextView answerQuestionView = findViewById(R.id.math_answer_view);
        answerQuestionView.append(keyInput);
    }

    public void deleteKeyInput() {
        TextView answerQuestionView = findViewById(R.id.math_answer_view);
        if (answerQuestionView != null) {
            String currentInput = answerQuestionView.getText().toString();
            if (currentInput.length() > 0) {
                currentInput = currentInput.substring(0, currentInput.length() -1);
                answerQuestionView.setText(currentInput);
            }
        }
    }

    public void checkGamePosition(){
        SharedPreferences sharedPreferences = PreferenceManager.getDefaultSharedPreferences(this);
        String gameLengthPreference =  sharedPreferences.getString("preference_number_of_questions", "5");
        int gameLength = Integer.parseInt(gameLengthPreference);

        TextView checkAnswerView = findViewById(R.id.check_answer);

        checkAnswer(gameLength);
    }

    public void checkAnswer(int gameLength){
        TextView answerQuestionView = findViewById(R.id.math_answer_view);
        TextView checkAnswerView = findViewById(R.id.check_answer);
        String currentInput = answerQuestionView.getText().toString();
        String rightAnswer = getResources().getString(R.string.right_answer);
        String wrongAnswer = getResources().getString(R.string.wrong_answer);

        if(currentInput.equals(prefLengthMathQuestions.get(currentQuestion).getAnswer())){
            checkAnswerView.setText(rightAnswer);
            score++;
            scoreScreen();
            answerQuestionView.setText("");
            Log.d("CheckAnswer","Riktig svar");
        } else {
            checkAnswerView.setText(wrongAnswer);
            answerQuestionView.setText("");
            Log.d("CheckAnswer","Galt svar");
        }

        if(currentQuestion == gameLength -1) {
            checkAnswerView.setText("spillet er over");
            saveAndExit();
        } else {
            currentQuestion++;
            gamePlay();
        }
    }

    private void saveAndExit(){
        // save data
        // access gamelength
        SharedPreferences sharedPreferences = PreferenceManager.getDefaultSharedPreferences(this);
        String gameLengthPreference =  sharedPreferences.getString("preference_number_of_questions", "5");

        int gameLength = Integer.parseInt(gameLengthPreference);

        SharedPreferences scoreData = PreferenceManager.getDefaultSharedPreferences(this);
        SharedPreferences.Editor scoreEditor = scoreData.edit();

        scoreEditor.putInt("lastScore", score);
        scoreEditor.putInt("gameLength", gameLength);
        scoreEditor.apply();  // end adding last score

        // Add total score
        int totalScore =  sharedPreferences.getInt("totalScore", 0);
        int totalLength = sharedPreferences.getInt("totalLength",0);
        totalScore += score;
        totalLength += gameLength;

        scoreEditor.putInt("totalScore", totalScore);
        scoreEditor.putInt("totalLength", totalLength);
        scoreEditor.apply();  // end adding total score

        // end dialog
        AlertDialog.Builder gameEndDialog = new AlertDialog.Builder(this);
        gameEndDialog.setTitle(getString(R.string.game_end_title));
        gameEndDialog.setMessage(getString(R.string.game_end_msg));
        gameEndDialog.setNeutralButton(getString(R.string.ok), new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialogInterface, int i) {
                finish();
            }
        });
        gameEndDialog.setCancelable(false);
        gameEndDialog.create().show();
    }

    @Override
    protected void onSaveInstanceState(Bundle outState) {
        super.onSaveInstanceState(outState);
        // Store game values when screen is rotated
        // lagre spill, input, posisjon i spi.ll
        TextView answerQuestionView = findViewById(R.id.math_answer_view);
        String currentInput = answerQuestionView.getText().toString();
        outState.putInt("currentQuestion",currentQuestion);
        outState.putString("currentInput",currentInput);

        // Save language setting when changing rotation
        String language = PreferenceManager.getDefaultSharedPreferences(this).getString("preference_languages", "default");
        Configuration config = getResources().getConfiguration();
        if( language.equals("default") ) language = Locale.getDefault().getLanguage();
        config.locale = new Locale(language);
        getResources().updateConfiguration(config, getResources().getDisplayMetrics());
    }

    @Override
    protected void onRestoreInstanceState(@NonNull Bundle savedInstanceState) {
        super.onRestoreInstanceState(savedInstanceState);
         // Restore values when screen is rotated
        savedInstanceState.getInt("currentQuestion");
        savedInstanceState.getString("currentInput");
    }
}
