package com.teige.tim.randomnamenorsk;

import android.content.pm.PackageManager;
import android.content.res.AssetManager;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;

import java.io.*;
import java.util.HashMap;
import java.util.Map;

public class MainActivity extends AppCompatActivity {
    private Map<String, Map<String, Double>> NameFrequency = new HashMap<>();

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

    }

    private void initializeNameFrequencies() {
        AssetManager assetManager = getAssets();
        try {
            InputStream femaleNames = assetManager.open(getString(R.string.female_names));
            Map<String, Double> femaleFrequency = createFrequencyMap(femaleNames);
            NameFrequency.put("female", femaleFrequency);
        } catch (IOException e) {
            try {
                InputStream femaleNames = openFileInput(getString(R.string.female_names));
                Map<String, Double> femaleFrequency = createFrequencyMap(femaleNames);
                NameFrequency.put("female", femaleFrequency);
            } catch (FileNotFoundException ignored) {
            } catch (IOException e1) {
                e1.printStackTrace();
            }
        }
        try {
            InputStream maleNames = assetManager.open(getString(R.string.male_names));
            Map<String, Double> maleFrequency = createFrequencyMap(maleNames);
            NameFrequency.put("male", maleFrequency);
        } catch (IOException e) {
            try {
                InputStream maleNames = openFileInput(getString(R.string.male_names));
                Map<String, Double> maleFrequency = createFrequencyMap(maleNames);
                NameFrequency.put("male", maleFrequency);
            } catch (FileNotFoundException ignored) {
            } catch (IOException e1) {
                e1.printStackTrace();
            }
        }
        try {
            InputStream lastNames = assetManager.open(getString(R.string.last_names));
            Map<String, Double> lastNameFrequency = createFrequencyMap(lastNames);
            NameFrequency.put("last", lastNameFrequency);
        } catch (IOException e) {
            try {
                InputStream lastNames = openFileInput(getString(R.string.last_names));
                Map<String, Double> lastNameFrequency = createFrequencyMap(lastNames);
                NameFrequency.put("last", lastNameFrequency);
            } catch (FileNotFoundException ignored) {

            } catch (IOException e1) {
                e1.printStackTrace();
            }
        }
    }

    private Map<String, Double> createFrequencyMap(InputStream inputStream) throws IOException {
        DataInputStream data = new DataInputStream(inputStream);
        BufferedReader bData = new BufferedReader(new InputStreamReader(data));
        String bufferedLine;
        Map<String, Double> frequencyMap = new HashMap<>();
        while ((bufferedLine = bData.readLine()) != null) {
            String[] split = bufferedLine.split(",");
            if (split[0].equals("")) {
                continue;
            }
            String name = split[0];
            try {
                Double frequency = Double.parseDouble(split[1]);
                frequencyMap.put(name, frequency);
            } catch (RuntimeException e) {
                e.printStackTrace();
            }
        }
        return frequencyMap;
    }


}
