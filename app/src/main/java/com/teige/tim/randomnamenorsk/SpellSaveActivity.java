package com.teige.tim.randomnamenorsk;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.text.TextUtils;
import android.view.Menu;
import android.view.View;
import android.widget.EditText;
import com.teige.tim.randomnamenorsk.db.entity.Spell;

import java.util.Objects;

public class SpellSaveActivity extends AppCompatActivity {

    public static final String EXTRA_REPLY = "com.teige.tim.randomnamenorsk.REPLY";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_spell_save);
        Toolbar myToolbar = findViewById(R.id.main_toolbar);
        setSupportActionBar(myToolbar);
        Objects.requireNonNull(getSupportActionBar()).setDisplayShowTitleEnabled(false);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_spell_save_toolbar, menu);
        return true;
    }

    public void onSave(View view) {
        Intent replyIntent = new Intent();
        EditText editTitle = findViewById(R.id.spell_title_input);
        EditText editLvl = findViewById(R.id.spell_lvl_input);
        EditText editType = findViewById(R.id.spell_type_input);
        EditText editCastTime = findViewById(R.id.spell_cast_time_input);
        EditText editRange = findViewById(R.id.spell_range_input);
        EditText editComponents = findViewById(R.id.spell_components_input);
        EditText editDuration = findViewById(R.id.spell_duration_input);
        EditText editDescription = findViewById(R.id.spell_description_input);
        EditText editCharacterClass = findViewById(R.id.spell_class_input);
        if (TextUtils.isEmpty(editTitle.getText())) {
            setResult(RESULT_CANCELED, replyIntent);
            return;
        }
        if (TextUtils.isEmpty(editLvl.getText())) {
            setResult(RESULT_CANCELED, replyIntent);
            return;
        }
        if (TextUtils.isEmpty(editType.getText())) {
            setResult(RESULT_CANCELED, replyIntent);
            return;
        }
        if (TextUtils.isEmpty(editCastTime.getText())) {
            setResult(RESULT_CANCELED, replyIntent);
            return;
        }
        if (TextUtils.isEmpty(editRange.getText())) {
            setResult(RESULT_CANCELED, replyIntent);
            return;
        }
        if (TextUtils.isEmpty(editComponents.getText())) {
            setResult(RESULT_CANCELED, replyIntent);
            return;
        }
        if (TextUtils.isEmpty(editDuration.getText())) {
            setResult(RESULT_CANCELED, replyIntent);
            return;
        }
        if (TextUtils.isEmpty(editDescription.getText())) {
            setResult(RESULT_CANCELED, replyIntent);
            return;
        }
        if (TextUtils.isEmpty(editCharacterClass.getText())) {
            setResult(RESULT_CANCELED, replyIntent);
            return;
        }

        String title = editTitle.getText().toString();
        String lvl = editLvl.getText().toString();
        String type = editType.getText().toString();
        String castTime = editCastTime.getText().toString();
        String range = editRange.getText().toString();
        String components = editComponents.getText().toString();
        String duration = editDuration.getText().toString();
        String description = editDescription.getText().toString();
        String characterClass = editCharacterClass.getText().toString();

        Spell spell = new Spell(Integer.parseInt(lvl), title, type, castTime, range, components, duration, description);
        replyIntent.putExtra("lvl", lvl);
        replyIntent.putExtra("title", title);
        replyIntent.putExtra("type", type);
        replyIntent.putExtra("castTime", castTime);
        replyIntent.putExtra("range", range);
        replyIntent.putExtra("components", components);
        replyIntent.putExtra("duration", duration);
        replyIntent.putExtra("description", description);
        replyIntent.putExtra("characterClass", characterClass);

        setResult(RESULT_OK, replyIntent);

        finish();
    }
}
