package com.teige.tim.randomnamenorsk.db;

import android.app.Application;
import android.os.AsyncTask;
import com.teige.tim.randomnamenorsk.db.dao.CharacterClassDao;
import com.teige.tim.randomnamenorsk.db.entity.CharacterClass;

import java.util.List;

public class CharacterClassRepository {

    private CharacterClassDao dao;
    private List<CharacterClass> classes;

    private static class insertAsyncTask extends AsyncTask<CharacterClass, Void, Void> {

        private CharacterClassDao asyncTaskDao;

        insertAsyncTask(CharacterClassDao dao) {
            asyncTaskDao = dao;
        }

        @Override
        protected Void doInBackground(final CharacterClass... params) {
            asyncTaskDao.insert(params[0]);
            return null;
        }
    }

    public CharacterClassRepository(Application application) {
        AppDatabase db = AppDatabase.getDatabase(application);
        dao = db.classDao();
        classes = dao.getAll();
    }

    public List<CharacterClass> getClasses() {
        return classes;
    }

    public void insert(CharacterClass characterClass) {
        new insertAsyncTask(dao).execute(characterClass);
    }
}
