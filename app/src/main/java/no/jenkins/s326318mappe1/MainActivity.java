package no.jenkins.s326318mappe1;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;

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
