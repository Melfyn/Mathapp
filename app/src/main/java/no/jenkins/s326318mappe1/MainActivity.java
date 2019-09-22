package no.jenkins.s326318mappe1;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.content.res.Configuration;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.view.View;

import java.util.Locale;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
    }

    public void startGame(View v){
        Intent intent=new Intent(this,GameActivity.class);
        startActivity(intent);
    }

    public void showStatistics(View v){
        Intent intent=new Intent(this,StatisticsActivity.class);
        startActivity(intent);
    }

    public void showPreferences(View v){
        Intent intent=new Intent(this,SetPreferencesActivity.class);
        startActivity(intent);
    }
}
