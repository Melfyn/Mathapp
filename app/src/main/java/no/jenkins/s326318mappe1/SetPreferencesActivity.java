package no.jenkins.s326318mappe1;

import android.content.SharedPreferences;
import android.content.res.Configuration;
import android.content.res.Resources;
import android.os.Bundle;
import android.preference.ListPreference;
import android.preference.PreferenceFragment;
import android.preference.PreferenceManager;
import android.util.DisplayMetrics;
import android.view.View;

import androidx.appcompat.app.AppCompatActivity;

import java.util.Locale;

public class SetPreferencesActivity extends AppCompatActivity {
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        getFragmentManager().beginTransaction().replace(android.R.id.content,new PrefsFragment()).commit();
    }

    @Override
    protected void onResume() {
        super.onResume();
        SharedPreferences sp = PreferenceManager.getDefaultSharedPreferences(this);
        sp.registerOnSharedPreferenceChangeListener(myPreferenceListener);
    }

    public static class PrefsFragment extends PreferenceFragment {
        @Override
        public void onCreate(Bundle savedInstanceState) {
            super.onCreate(savedInstanceState);
            addPreferencesFromResource(R.xml.preferences);
        }
    }

    public void changeLanguage(String language){
        Resources res=getResources();
        DisplayMetrics dm=res.getDisplayMetrics();
        Configuration cf=res.getConfiguration();
        cf.setLocale(new Locale(language));
        res.updateConfiguration(cf,dm);
    }

    SharedPreferences.OnSharedPreferenceChangeListener myPreferenceListener = new SharedPreferences.OnSharedPreferenceChangeListener() {
        @Override
        public void onSharedPreferenceChanged(SharedPreferences sharedPreferences, String s) {
            String value = sharedPreferences.getString(s, "");
            switch (value) {
                case "no": {
                    changeLanguage("no");
                    recreate();
                    break;
                }

                case "de": {
                    changeLanguage("de");
                    recreate();
                    break;
                }
            }
        }
    };

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
}
