package com.teige.tim.randomnamenorsk.db;

import android.app.Application;
import android.arch.lifecycle.LiveData;
import android.os.AsyncTask;
import com.teige.tim.randomnamenorsk.R;
import com.teige.tim.randomnamenorsk.db.dao.SpellDao;
import com.teige.tim.randomnamenorsk.db.entity.Spell;

import java.util.LinkedList;
import java.util.List;
import java.util.Objects;

public class SpellRepository {

    private SpellDao spellDao;
    private LiveData<List<Spell>> allSpells;

    private static class insertAsyncTask extends AsyncTask<Spell, Void, Void> {

        private SpellDao asyncTaskDao;

        insertAsyncTask(SpellDao dao) {
            asyncTaskDao = dao;
        }

        @Override
        protected Void doInBackground(final Spell... params) {
            asyncTaskDao.insert(params[0]);
            return null;
        }
    }

    public SpellRepository(Application application) {
        AppDatabase db = AppDatabase.getDatabase(application);
        spellDao = db.spellDao();
        allSpells = spellDao.getAll();
    }

    public LiveData<List<Spell>> getAllSpells() {
        return allSpells;
    }

    public void insert(Spell spell) {
        new insertAsyncTask(spellDao).execute(spell);
    }

    public LiveData<List<Spell>> searchByName(String query, String characterClass) {
        query = query + "%";
        if (characterClass.equals("All classes")) {
            return spellDao.searchByName(query);
        } else {
            characterClass = characterClass + "%";
            return spellDao.searchByNameAndClass(query, characterClass);
        }
    }

    public LiveData<List<Spell>> searchByClass(String className) {
        className = "%" + className + "%";
        return spellDao.searchByClass(className);
    }

}
