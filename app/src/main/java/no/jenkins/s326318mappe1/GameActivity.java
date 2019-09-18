package no.jenkins.s326318mappe1;

import android.content.Context;
import android.content.SharedPreferences;
import android.os.Bundle;

import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.android.material.snackbar.Snackbar;

import androidx.annotation.NonNull;
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
        // Game Launch
        gamePlay();
        /* Listener for knappene*/
        startKeyListeners();
        // gameplay();
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
        findViewById(R.id.answer_key).setOnClickListener(view -> checkAnswer());
        findViewById(R.id.delete_key).setOnClickListener(view -> deleteKeyInput());
    }

    public void addKeyInput(String keyInput) {
        TextView answerQuestionView = findViewById(R.id.math_answer_view);
        answerQuestionView.append(keyInput);
    }

    public void deleteKeyInput() {
        TextView answerQuestionView = findViewById(R.id.math_answer_view);
        if (answerQuestionView != null)
        {
            String currentInput = answerQuestionView.getText().toString();
            if (currentInput.length() > 0)
            {
                currentInput = currentInput.substring(0, currentInput.length() -1);
                answerQuestionView.setText(currentInput);
            }
        }
    }

    public void checkAnswer(){
        TextView answerQuestionView = findViewById(R.id.math_answer_view);
        TextView checkAnswerView = findViewById(R.id.check_answer);
        String currentInput = answerQuestionView.getText().toString();

    if(currentInput.equals(prefLengthMathQuestions.get(currentQuestion).getAnswer())){
            checkAnswerView.setText("Riktig svar");
            currentQuestion++;
            gamePlay();
            Log.d("CheckAnswer","Riktig svar");
        } else {
            checkAnswerView.setText("Galt svar");
            currentQuestion++;
            gamePlay();
            Log.d("CheckAnswer","Galt svar");
        }
    }

    @Override
    protected void onSaveInstanceState(Bundle outState) {
        super.onSaveInstanceState(outState);
        // lagre spill, input, posisjon i spi.ll
        /*
        outState.putInt();
        outState.
         */
    }

    @Override
    protected void onRestoreInstanceState(@NonNull Bundle savedInstanceState) {
        super.onRestoreInstanceState(savedInstanceState);
         // restore spillet da
    }
}
