package com.teige.tim.randomnamenorsk.db;

import android.arch.persistence.db.SupportSQLiteDatabase;
import android.arch.persistence.room.Database;
import android.arch.persistence.room.Room;
import android.arch.persistence.room.RoomDatabase;
import android.content.Context;
import android.content.res.AssetManager;
import android.os.AsyncTask;
import android.support.annotation.NonNull;
import com.teige.tim.randomnamenorsk.db.dao.SpellDao;
import com.teige.tim.randomnamenorsk.db.entity.Spell;

import java.io.*;
import java.nio.charset.StandardCharsets;
import java.util.ArrayList;
import java.util.List;

@Database(entities = {Spell.class}, version = 1)
public abstract class SpellDatabase extends RoomDatabase {
    public abstract SpellDao spellDao();

    private static volatile SpellDatabase INSTANCE;

    static SpellDatabase getDatabase(final Context context) {
        if (INSTANCE == null) {
            synchronized (SpellDatabase.class) {
                if (INSTANCE == null) {
                    INSTANCE = Room.databaseBuilder(context.getApplicationContext(),
                            SpellDatabase.class, "spell_database").addCallback(
                            new RoomDatabase.Callback(){

                                @Override
                                public void onOpen (@NonNull SupportSQLiteDatabase db){
                                    super.onOpen(db);
                                    AssetManager manager = context.getAssets();

                                    try {
                                        new PopulateDbAsync(INSTANCE, manager.open("bard.csv")).execute();
                                    } catch (IOException e) {
                                        e.printStackTrace();
                                    }
                                    try {
                                        new PopulateDbAsync(INSTANCE, manager.open("cleric.csv")).execute();
                                    } catch (IOException e) {
                                        e.printStackTrace();
                                    }
                                    try {
                                        new PopulateDbAsync(INSTANCE, manager.open("druid.csv")).execute();
                                    } catch (IOException e) {
                                        e.printStackTrace();
                                    }
                                    try {
                                        new PopulateDbAsync(INSTANCE, manager.open("paladin.csv")).execute();
                                    } catch (IOException e) {
                                        e.printStackTrace();
                                    }
                                    try {
                                        new PopulateDbAsync(INSTANCE, manager.open("ranger.csv")).execute();
                                    } catch (IOException e) {
                                        e.printStackTrace();
                                    }
                                    try {
                                        new PopulateDbAsync(INSTANCE, manager.open("sorcerer.csv")).execute();
                                    } catch (IOException e) {
                                        e.printStackTrace();
                                    }
                                    try {
                                        new PopulateDbAsync(INSTANCE, manager.open("warlock.csv")).execute();
                                    } catch (IOException e) {
                                        e.printStackTrace();
                                    }
                                    try {
                                        new PopulateDbAsync(INSTANCE, manager.open("wizard.csv")).execute();
                                    } catch (IOException e) {
                                        e.printStackTrace();
                                    }
                                }
                            }
                    ).build();
                }
            }
        }
        return INSTANCE;
    }

    private static class PopulateDbAsync extends AsyncTask<Void, Void, Void> {

        private final SpellDao spellDao;
        private final InputStream stream;

        PopulateDbAsync(SpellDatabase db, InputStream stream) {
            spellDao = db.spellDao();
            this.stream = stream;
        }

        @Override
        protected Void doInBackground(final Void... params) {
            spellDao.deleteAll();
            DataInputStream data = new DataInputStream(stream);
            BufferedReader bData = new BufferedReader(new InputStreamReader(data, StandardCharsets.ISO_8859_1));
            String line;
            try {
                while ((line = bData.readLine()) != null) {
                    spellDao.insert(new Spell(line));
                }
            } catch (IOException e) {
                e.printStackTrace();
            }
            return null;
        }
    }
}


