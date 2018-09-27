package com.teige.tim.randomnamenorsk;

import android.content.Context;
import android.content.res.AssetManager;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.CheckBox;
import android.widget.RadioButton;
import android.widget.Toast;
import org.apache.commons.math3.distribution.EnumeratedDistribution;
import org.apache.commons.math3.util.Pair;

import java.io.*;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class MainActivity extends AppCompatActivity {
    private Map<String, List<Pair<String, Double>>> nameFrequencyMap = new HashMap<>();
    private NameGenerator nameGenerator = new NameGenerator();


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        initializeNameFrequencies();
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
    }

    public void generateNames(View view) {
        RadioButton male = findViewById(R.id.maleRadioButton);
        boolean maleChecked = male.isChecked();
        boolean rare = ((CheckBox) findViewById(R.id.rarityCheckBox)).isChecked();
        boolean first = ((CheckBox) findViewById(R.id.firstNameCheckBox)).isChecked();
        boolean last = ((CheckBox) findViewById(R.id.lastNameCheckBox)).isChecked();
        String firstName = null;
        String lastName = null;

        List<Pair<String, Double>> firstNameFrequencyList;
        List<Pair<String, Double>> lastNameFrequencyList = nameFrequencyMap.get("last");

        if (!first && !last) {
            Context context = getApplicationContext();
            CharSequence text = "Fornavn eller etternavn må være valgt";
            int duration = Toast.LENGTH_SHORT;
            Toast toast = Toast.makeText(context, text, duration);
            toast.show();
            return;
        }

        if (first) {
            if (maleChecked) {
                firstNameFrequencyList = nameFrequencyMap.get("male");
            } else {
                firstNameFrequencyList = nameFrequencyMap.get("female");
            }
            firstName = new EnumeratedDistribution<>(firstNameFrequencyList).sample();
        }

        if (last) {
            lastName = new EnumeratedDistribution<>(lastNameFrequencyList).sample();
        }
        Log.i("OUTPUT", firstName + " " + lastName);
    }

    private void initializeNameFrequencies() {
        AssetManager assetManager = getAssets();
        try {
            InputStream femaleNames = assetManager.open(getString(R.string.female_names));
            List<Pair<String, Double>> femaleFrequency = nameGenerator.createFrequencyList(femaleNames);
            nameFrequencyMap.put("female", femaleFrequency);
        } catch (IOException e) {
            try {
                InputStream femaleNames = openFileInput(getString(R.string.female_names));
                List<Pair<String, Double>> femaleFrequency = nameGenerator.createFrequencyList(femaleNames);
                nameFrequencyMap.put("female", femaleFrequency);
            } catch (FileNotFoundException ignored) {
            } catch (IOException e1) {
                e1.printStackTrace();
            }
        }
        try {
            InputStream maleNames = assetManager.open(getString(R.string.male_names));
            List<Pair<String, Double>> maleFrequency = nameGenerator.createFrequencyList(maleNames);
            nameFrequencyMap.put("male", maleFrequency);
        } catch (IOException e) {
            try {
                InputStream maleNames = openFileInput(getString(R.string.male_names));
                List<Pair<String, Double>> maleFrequency = nameGenerator.createFrequencyList(maleNames);
                nameFrequencyMap.put("male", maleFrequency);
            } catch (FileNotFoundException ignored) {
            } catch (IOException e1) {
                e1.printStackTrace();
            }
        }
        try {
            InputStream lastNames = assetManager.open(getString(R.string.last_names));
            List<Pair<String, Double>> lastNameFrequency = nameGenerator.createFrequencyList(lastNames);
            nameFrequencyMap.put("last", lastNameFrequency);
        } catch (IOException e) {
            try {
                InputStream lastNames = openFileInput(getString(R.string.last_names));
                List<Pair<String, Double>> lastNameFrequency = nameGenerator.createFrequencyList(lastNames);
                nameFrequencyMap.put("last", lastNameFrequency);
            } catch (FileNotFoundException ignored) {

            } catch (IOException e1) {
                e1.printStackTrace();
            }
        }
    }
}
