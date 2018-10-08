package com.teige.tim.randomnamenorsk.db;

import android.app.Application;
import android.os.AsyncTask;
import com.teige.tim.randomnamenorsk.db.dao.CharacterClassSpellDao;
import com.teige.tim.randomnamenorsk.db.entity.CharacterClassSpell;

import java.util.List;

public class CharacterClassSpellRepository {
    private CharacterClassSpellDao dao;
    private List<CharacterClassSpell> classSpells;

    private static class insertAsyncTask extends AsyncTask<CharacterClassSpell, Void, Void> {

        private CharacterClassSpellDao asyncTaskDao;

        insertAsyncTask(CharacterClassSpellDao dao) {
            asyncTaskDao = dao;
        }

        @Override
        protected Void doInBackground(final CharacterClassSpell... params) {
            asyncTaskDao.insert(params[0]);
            return null;
        }
    }

    public CharacterClassSpellRepository(Application application) {
        AppDatabase db = AppDatabase.getDatabase(application);
        dao = db.characterClassSpellDao();
        classSpells = dao.getAll();
    }

    public List<CharacterClassSpell> getClassSpells() {
        return classSpells;
    }

    public void insert(CharacterClassSpell characterClassSpell) {
        new insertAsyncTask(dao).execute(characterClassSpell);
    }
}
