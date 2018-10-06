package com.teige.tim.randomnamenorsk.views;

import android.app.Application;
import android.arch.lifecycle.AndroidViewModel;
import android.arch.lifecycle.LiveData;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import com.teige.tim.randomnamenorsk.db.SpellRepository;
import com.teige.tim.randomnamenorsk.db.entity.Spell;

import java.util.List;

public class SpellViewModel extends AndroidViewModel {

    private SpellRepository spellRepository;
    private LiveData<List<Spell>> spells;

    public SpellViewModel(@NonNull Application application) {
        super(application);
        spellRepository = new SpellRepository(application);
        spells = spellRepository.getAllSpells();
    }

    public LiveData<List<Spell>> getSpells() {
        return spells;
    }

    public void insert(Spell spell) {
        spellRepository.insert(spell);
    }
}
