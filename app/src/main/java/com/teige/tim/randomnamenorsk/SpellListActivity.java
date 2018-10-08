package com.teige.tim.randomnamenorsk;

import android.arch.lifecycle.Observer;
import android.arch.lifecycle.ViewModelProviders;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.design.widget.FloatingActionButton;
import android.support.v4.view.MenuItemCompat;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.SearchView;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.Toast;
import com.teige.tim.randomnamenorsk.db.entity.Spell;
import com.teige.tim.randomnamenorsk.views.RecycleSpellListAdapter;
import com.teige.tim.randomnamenorsk.views.SpellViewModel;

import java.util.List;
import java.util.Objects;

public class SpellListActivity extends AppCompatActivity {

    public static final int NEW_SPELL_ACTIVITY_REQUEST_CODE = 1;
    private RecycleSpellListAdapter adapter;
    private SpellViewModel spellViewModel;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_spell_list);
        Toolbar myToolbar = findViewById(R.id.main_toolbar);
        setSupportActionBar(myToolbar);

        RecyclerView spellListView = findViewById(R.id.spell_list_view);
        adapter = new RecycleSpellListAdapter(this);
        spellListView.setAdapter(adapter);
        spellListView.setLayoutManager(new LinearLayoutManager(this));

        Intent startIntent = getIntent();
        String classButtonString = startIntent.getStringExtra("CLASS");
        spellViewModel = ViewModelProviders.of(this).get(SpellViewModel.class);

        if (!classButtonString.equals(getString(R.string.spell_item_class_button_all))) {
            spellViewModel.searchByClass(classButtonString).observe(this, new Observer<List<Spell>>() {
                @Override
                public void onChanged(@Nullable List<Spell> spells) {
                    adapter.add(spells);
                }
            });
        } else {
            spellViewModel.getSpells().observe(this, new Observer<List<Spell>>() {
                @Override
                public void onChanged(@Nullable List<Spell> spells) {
                    adapter.add(spells);
                }
            });
        }


        FloatingActionButton fab = findViewById(R.id.new_spell_fab);
        fab.setOnClickListener(view -> {
            Intent intent = new Intent(getApplicationContext(), SpellSaveActivity.class);
            startActivityForResult(intent, NEW_SPELL_ACTIVITY_REQUEST_CODE);
        });
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu_spell_list_toolbar, menu);

        final MenuItem searchItem = menu.findItem(R.id.action_spell_search);
        final SearchView searchView = (SearchView) MenuItemCompat.getActionView(searchItem);
        searchView.setOnQueryTextListener(new SearchView.OnQueryTextListener() {
            @Override
            public boolean onQueryTextSubmit(String query) {
                return false;
            }

            @Override
            public boolean onQueryTextChange(String query) {
                spellViewModel.searchByName(query).observe(SpellListActivity.this, new Observer<List<Spell>>() {
                    @Override
                    public void onChanged(@Nullable List<Spell> spells) {
                        if (spells == null) {
                            return;
                        }
                        adapter.setSpellList(spells);
                    }
                });
                return true;
            }
        });

        return true;
    }

    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == NEW_SPELL_ACTIVITY_REQUEST_CODE && resultCode == RESULT_OK) {
            Bundle extras = data.getExtras();
            assert extras != null;
            @NonNull
            String title = Objects.requireNonNull(extras.getString("title"));
            Integer lvl = Integer.parseInt(extras.getString("lvl"));
            Spell spell = new Spell(
                    lvl,
                    title,
                    extras.getString("type"),
                    extras.getString("castTime"),
                    extras.getString("range"),
                    extras.getString("components"),
                    extras.getString("duration"),
                    extras.getString("description")
            );

            spellViewModel.insert(spell);
        } else {
            Toast.makeText(
                    getApplicationContext(),
                    R.string.spell_not_saved,
                    Toast.LENGTH_LONG).show();
        }
    }
}
