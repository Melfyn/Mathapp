package no.jenkins.s326318mappe1;

import android.content.DialogInterface;
import android.content.SharedPreferences;
import android.content.res.Configuration;
import android.os.Bundle;

import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.android.material.snackbar.Snackbar;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

import android.preference.PreferenceManager;
import android.view.View;
import android.widget.TextView;

import org.w3c.dom.Text;

import java.util.Locale;

public class StatisticsActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_statistics);
        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        deleteBtnListener();
        showTotalScore();
        showLastScore();
    }

    // Save language setting when changing rotation
    @Override
    protected void onSaveInstanceState(Bundle outState) {
        super.onSaveInstanceState(outState);

        String language = PreferenceManager.getDefaultSharedPreferences(this).getString("preference_languages", "default");
        Configuration config = getResources().getConfiguration();
        if( language.equals("default") ) language = Locale.getDefault().getLanguage();
        config.locale = new Locale(language);
        getResources().updateConfiguration(config, getResources().getDisplayMetrics());
    }

    public void showTotalScore(){
        SharedPreferences sharedPreferences = PreferenceManager.getDefaultSharedPreferences(this);

        int totalScore =  sharedPreferences.getInt("totalScore", 0);
        int totalLength = sharedPreferences.getInt("totalLength", 0);

        TextView totalScoreView = findViewById(R.id.total_game_score);
        totalScoreView.setText(totalScore+" / "+ totalLength);
    }

    public void showLastScore(){
        SharedPreferences sharedPreferences = PreferenceManager.getDefaultSharedPreferences(this);
        String gameLengthPreference =  sharedPreferences.getString("preference_number_of_questions", "5");
        int lastScore =  sharedPreferences.getInt("lastScore", 0);

        TextView lastScoreView = findViewById(R.id.last_game_score);
        lastScoreView.setText(lastScore+" / "+ gameLengthPreference);
    }

    public void deleteStatistics(){
        SharedPreferences sharedPreferences = PreferenceManager.getDefaultSharedPreferences(this);
        SharedPreferences scoreData = PreferenceManager.getDefaultSharedPreferences(this);
        SharedPreferences.Editor scoreEditor = scoreData.edit();
        // set values to 0 in total score
        scoreEditor.putInt("totalScore", 0);
        scoreEditor.putInt("totalLength", 0);
        scoreEditor.apply();
        // set new values in total score
        int totalScore =  sharedPreferences.getInt("totalScore", 0);
        int totalLength = sharedPreferences.getInt("totalLength", 0);
        /* set values to 0 in last score
        scoreEditor.putInt("lastScore", 0);
        scoreEditor.putInt("gameLength", 0);
        scoreEditor.apply();
        //set new values in last score
        int lastScore =  sharedPreferences.getInt("lastScore", 0);
        int gameLength = sharedPreferences.getInt("gameLength", 0);
        TextView lastScoreView = findViewById(R.id.last_game_score);
        lastScoreView.setText(lastScore+" / "+gameLength); */
        // set reset score in views
        TextView totalScoreView = findViewById(R.id.total_game_score);
        totalScoreView.setText(totalScore+" / "+ totalLength);
    }

    public void deleteBtnListener() {
        //keylistener for delete button
        findViewById(R.id.delete_statistics_btn).setOnClickListener(view -> deleteStatsDialog());
    }

    public void deleteStatsDialog(){
        AlertDialog.Builder deleteStatsDialog = new AlertDialog.Builder(this);
        deleteStatsDialog.setTitle(getString(R.string.delete_stats_title));
        deleteStatsDialog.setMessage(getString(R.string.delete_stats_txt));
        deleteStatsDialog.setPositiveButton(getString(R.string.yes), new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialogInterface, int i) {
                deleteStatistics();
            }
        });
        deleteStatsDialog.setNegativeButton(getString(R.string.no), new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialogInterface, int i) {
                dialogInterface.dismiss();
            }
        });
        deleteStatsDialog.setCancelable(false);
        deleteStatsDialog.create().show();
    }
}
