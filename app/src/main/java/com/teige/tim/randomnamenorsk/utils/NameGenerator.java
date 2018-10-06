package com.teige.tim.randomnamenorsk.utils;

import android.util.Log;
import com.teige.tim.randomnamenorsk.R;
import org.apache.commons.math3.distribution.EnumeratedDistribution;
import org.apache.commons.math3.util.Pair;

import java.io.*;
import java.nio.charset.StandardCharsets;
import java.util.ArrayList;
import java.util.List;

public class NameGenerator {
    private List<Pair<String, Double>> maleFirstNameList;
    private List<Pair<String, Double>> femaleFirstNameList;
    private List<Pair<String, Double>> lastNameList;

    public NameGenerator() {

    }

    public NameGenerator(InputStream maleStream, InputStream femaleStream, InputStream lastStream) {
        try {
            maleFirstNameList = _insertFrequencyListFromStream(maleStream);
            femaleFirstNameList = _insertFrequencyListFromStream(femaleStream);
            lastNameList = _insertFrequencyListFromStream(lastStream);
        } catch (IOException e) {
            e.printStackTrace();
        }

    }

    public NameGenerator(List<Pair<String, Double>> maleFirstNameList,
                         List<Pair<String, Double>> femaleFirstNameList,
                         List<Pair<String, Double>> lastNameList) {
        this.maleFirstNameList = maleFirstNameList;
        this.femaleFirstNameList = femaleFirstNameList;
        this.lastNameList = lastNameList;
    }

    public String generateFirstName(Integer gender) {
        switch (gender) {
            case R.integer.male:
                return new EnumeratedDistribution<>(maleFirstNameList).sample();
            case R.integer.female:
                return new EnumeratedDistribution<>(femaleFirstNameList).sample();
            default:
                return "";
        }
    }

    public String generateLastName() {
        return new EnumeratedDistribution<>(lastNameList).sample();
    }

    public void insertFrequencyListFromStream(InputStream inputStream, int type) {
        try {
            switch (type) {
                case R.integer.female:
                    femaleFirstNameList = _insertFrequencyListFromStream(inputStream);
                    break;
                case R.integer.male:
                    maleFirstNameList = _insertFrequencyListFromStream(inputStream);
                    break;
                case R.integer.lastName:
                    lastNameList = _insertFrequencyListFromStream(inputStream);
                    break;
                default:
                    Log.e("FREQUENCY LIST", Integer.toString(type));
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private List<Pair<String, Double>> _insertFrequencyListFromStream(InputStream inputStream) throws IOException {
        DataInputStream data = new DataInputStream(inputStream);
        BufferedReader bData = new BufferedReader(new InputStreamReader(data, StandardCharsets.ISO_8859_1));
        String bufferedLine;
        List<Pair<String, Double>> frequencyList = new ArrayList<>();
        while ((bufferedLine = bData.readLine()) != null) {
            String[] split = bufferedLine.split(",");
            if (split[0].equals("")) {
                continue;
            }
            String name = split[0];
            try {

                Double frequency = Double.parseDouble(split[1]);
                frequencyList.add(new Pair<>(name, frequency));
            } catch (RuntimeException e) {
                e.printStackTrace();
            }
        }
        return frequencyList;
    }

}
