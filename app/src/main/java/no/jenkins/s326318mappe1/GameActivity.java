package no.jenkins.s326318mappe1;

import android.content.Context;
import android.content.SharedPreferences;
import android.os.Bundle;

import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.android.material.snackbar.Snackbar;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

import android.preference.PreferenceManager;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class GameActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_game);
        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        /* Create random math questions*/
        createRandomMathArrays();
        /* Listener for knappene*/
        startKeyListeners();
        gameLaunch();
        gameplay();
        // getString("preference_number_of_questions","5");
    }

    public String getString (String key, String defaultkey){
        Context context = getApplicationContext();
        SharedPreferences sharedpref = context.getSharedPreferences(key, Context.MODE_PRIVATE);
        return sharedpref.getString(key, null);
    }

    public void createRandomMathArrays() {
        /*
        ArrayList questions = new ArrayList();
        ArrayList answers = new ArrayList();
        */

        // Alternativ 1
        SharedPreferences sharedPreferences = PreferenceManager.getDefaultSharedPreferences(this);
        String horePreference =  sharedPreferences.getString("preference_number_of_questions", "5");

        Log.d("alternativ 1",horePreference);

        Context context = getApplicationContext();
        SharedPreferences sharedpref = context.getSharedPreferences("preference_number_of_questions", Context.MODE_PRIVATE);
        Log.d("alternativ 2", sharedpref.toString());

        String[] questions = getResources().getStringArray(R.array.questions);
        String[] answers = getResources().getStringArray(R.array.answers);
        Log.d("Questions array", "arr: " + Arrays.toString(questions));
        Log.d("Answers array", "arr: " + Arrays.toString(answers));



    }

    public void gameLaunch(){
        String[] questions = getResources().getStringArray(R.array.questions);
        String questionText = getResources().getString(R.string.question_field_text);
        TextView mathQuestionView = findViewById(R.id.math_question_view);
        mathQuestionView.setText(questionText +" "+ "\n" + questions[0]);
    }



    public void gameplay() {
        // TextView answerQuestionView = findViewById(R.id.math_answer_view);
        /*
        Button keyOne = findViewById(R.id.key1);
        String buttonText = keyOne.getText().toString();
        answerQuestionView.setText(buttonText);
        */

       // findViewById(R.id.key1).setOnClickListener(view -> addKeyInput());
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
        String currentInput = answerQuestionView.getText().toString();
        String[] answers = getResources().getStringArray(R.array.answers);

        if(currentInput.equals(answers[0])){

            Log.d("CheckAnswer", "Svaret er riktig ");
        } else {
            Log.d("CheckAnswer","Svaret er galt");
        }
    }

}
