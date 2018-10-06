package com.teige.tim.randomnamenorsk;

import android.content.Context;
import android.content.res.AssetManager;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.DividerItemDecoration;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.support.v7.widget.helper.ItemTouchHelper;
import android.view.View;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.RadioButton;
import android.widget.Toast;
import com.teige.tim.randomnamenorsk.utils.NameGenerator;
import com.teige.tim.randomnamenorsk.views.RecycleNameListAdapter;

import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Objects;

import static android.support.v7.widget.RecyclerView.HORIZONTAL;

public class NameGenerationActivity extends AppCompatActivity {
    private NameGenerator nameGenerator = new NameGenerator();
    private ArrayList<String> nameList = new ArrayList<>();
    private RecycleNameListAdapter nameListAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_name_generation);
        Toolbar toolbar = findViewById(R.id.main_toolbar);
        setSupportActionBar(toolbar);
        Objects.requireNonNull(getSupportActionBar()).setDisplayShowTitleEnabled(false);

        initializeNameFrequencies();
        RecyclerView listView = findViewById(R.id.nameList);
        RecyclerView.LayoutManager layoutManager = new LinearLayoutManager(this);
        listView.setLayoutManager(layoutManager);
        nameListAdapter = new RecycleNameListAdapter(nameList);
        listView.setAdapter(nameListAdapter);
        DividerItemDecoration itemDecor = new DividerItemDecoration(this, HORIZONTAL);
        listView.addItemDecoration(itemDecor);
        ItemTouchHelper itemTouchHelper = new ItemTouchHelper(new ItemTouchHelper.SimpleCallback(0, ItemTouchHelper.LEFT | ItemTouchHelper.RIGHT) {
            @Override
            public boolean onMove(RecyclerView recyclerView, RecyclerView.ViewHolder viewHolder, RecyclerView.ViewHolder target) {
                return false;
            }

            @Override
            public void onSwiped(RecyclerView.ViewHolder viewHolder, int swipeDir) {
                int position = viewHolder.getAdapterPosition();
                nameListAdapter.remove(position);
            }
        });

        itemTouchHelper.attachToRecyclerView(listView);
    }

    public void generateNames(View view) {
        RadioButton male = findViewById(R.id.maleRadioButton);
        boolean maleChecked = male.isChecked();
        boolean rare = ((CheckBox) findViewById(R.id.rarityCheckBox)).isChecked();
        boolean first = ((CheckBox) findViewById(R.id.firstNameCheckBox)).isChecked();
        boolean last = ((CheckBox) findViewById(R.id.lastNameCheckBox)).isChecked();
        String firstName = null;
        String lastName = null;
        EditText editText = findViewById(R.id.nameCount);
        int iterations = Integer.parseInt(editText.getText().toString());

        if (!first && !last) {
            Context context = getApplicationContext();
            CharSequence text = "Fornavn eller etternavn må være valgt";
            int duration = Toast.LENGTH_SHORT;
            Toast toast = Toast.makeText(context, text, duration);
            toast.show();
            return;
        }

        for (int i = 0; i < iterations; i++) {
            if (first) {
                if (maleChecked) {
                    firstName = nameGenerator.generateFirstName(R.integer.male);
                } else {
                    firstName = nameGenerator.generateFirstName(R.integer.female);
                }
            }

            if (last) {
                lastName = nameGenerator.generateLastName();
            }
            String fullName;
            if (firstName == null) {
                fullName = lastName;
            } else if (lastName == null) {
                fullName = firstName;
            } else {
                fullName = firstName + " " + lastName;
            }

            nameListAdapter.add(fullName);
        }
    }

    private void initializeNameFrequencies() {
        AssetManager assetManager = getAssets();
        try {
            nameGenerator.insertFrequencyListFromStream(assetManager.open(getString(R.string.female_names)), R.integer.female);
        } catch (IOException e) {
            try {
                nameGenerator.insertFrequencyListFromStream(openFileInput(getString(R.string.female_names)), R.integer.female);
            } catch (FileNotFoundException e1) {
                e1.printStackTrace();
            }
        }
        try {
            nameGenerator.insertFrequencyListFromStream(assetManager.open(getString(R.string.male_names)), R.integer.male);
        } catch (IOException e) {
            try {
                nameGenerator.insertFrequencyListFromStream(openFileInput(getString(R.string.male_names)), R.integer.male);
            } catch (FileNotFoundException e1) {
                e1.printStackTrace();
            }
        }
        try {
            nameGenerator.insertFrequencyListFromStream(assetManager.open(getString(R.string.last_names)), R.integer.lastName);
        } catch (IOException e) {
            try {
                nameGenerator.insertFrequencyListFromStream(openFileInput(getString(R.string.last_names)), R.integer.lastName);
            } catch (FileNotFoundException e1) {
                e1.printStackTrace();

            }
        }
    }

}
