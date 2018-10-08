package com.teige.tim.randomnamenorsk;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.View;
import android.widget.Button;

import java.util.Objects;

public class PlayerActivity extends AppCompatActivity {

    private boolean spellListItemsVisible = false;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_player);
        Toolbar myToolbar = findViewById(R.id.main_toolbar);
        setSupportActionBar(myToolbar);
        Objects.requireNonNull(getSupportActionBar()).setDisplayShowTitleEnabled(false);
        findViewById(R.id.spell_character_class_button_layout).setVisibility(spellListItemsVisible ? View.VISIBLE : View.GONE);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_player_tools_toolbar, menu);
        return true;
    }

    public void spellListSelected(View view) {
        spellListItemsVisible = !spellListItemsVisible;
        findViewById(R.id.spell_character_class_button_layout).setVisibility(spellListItemsVisible ? View.VISIBLE : View.GONE);
    }

    public void spellListClassItemSelected(View view) {
        Intent intent = new Intent(this, SpellListActivity.class);
        Button b = (Button)view;
        intent.putExtra("CLASS", b.getText());
        startActivity(intent);
    }
}
