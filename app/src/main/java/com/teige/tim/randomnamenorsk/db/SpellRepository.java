package com.teige.tim.randomnamenorsk.db;

import android.app.Application;
import android.arch.lifecycle.LiveData;
import android.os.AsyncTask;
import com.teige.tim.randomnamenorsk.db.dao.SpellDao;
import com.teige.tim.randomnamenorsk.db.entity.Spell;

import java.util.List;

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
        SpellDatabase db = SpellDatabase.getDatabase(application);
        spellDao = db.spellDao();
        allSpells = spellDao.getAll();
    }

    public LiveData<List<Spell>> getAllSpells() {
        return allSpells;
    }

    public void insert(Spell spell) {
        new insertAsyncTask(spellDao).execute(spell);
    }

}
