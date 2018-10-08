package com.teige.tim.randomnamenorsk.db;

import android.arch.persistence.db.SupportSQLiteDatabase;
import android.arch.persistence.room.Database;
import android.arch.persistence.room.Room;
import android.arch.persistence.room.RoomDatabase;
import android.content.Context;
import android.content.res.AssetManager;
import android.os.AsyncTask;
import android.support.annotation.NonNull;
import com.teige.tim.randomnamenorsk.db.dao.CharacterClassSpellDao;
import com.teige.tim.randomnamenorsk.db.dao.CharacterClassDao;
import com.teige.tim.randomnamenorsk.db.dao.SpellDao;
import com.teige.tim.randomnamenorsk.db.entity.CharacterClass;
import com.teige.tim.randomnamenorsk.db.entity.CharacterClassSpell;
import com.teige.tim.randomnamenorsk.db.entity.Spell;

import java.io.*;
import java.nio.charset.StandardCharsets;

@Database(entities = {Spell.class, CharacterClass.class, CharacterClassSpell.class}, version = 1)
public abstract class AppDatabase extends RoomDatabase {
    public abstract SpellDao spellDao();
    public abstract CharacterClassDao classDao();
    public abstract CharacterClassSpellDao characterClassSpellDao();

    private static volatile AppDatabase INSTANCE;

    static AppDatabase getDatabase(final Context context) {
        if (INSTANCE == null) {
            synchronized (AppDatabase.class) {
                if (INSTANCE == null) {
                    INSTANCE = Room.databaseBuilder(context.getApplicationContext(),
                            AppDatabase.class, "app_database").addCallback(
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
        private final CharacterClassDao characterClassDao;
        private final CharacterClassSpellDao classSpellDao;
        private final InputStream stream;

        PopulateDbAsync(AppDatabase db, InputStream stream) {
            spellDao = db.spellDao();
            characterClassDao = db.classDao();
            classSpellDao = db.characterClassSpellDao();
            this.stream = stream;
        }

        @Override
        protected Void doInBackground(final Void... params) {
            DataInputStream data = new DataInputStream(stream);
            BufferedReader bData = new BufferedReader(new InputStreamReader(data, StandardCharsets.ISO_8859_1));
            String line;
            try {
                while ((line = bData.readLine()) != null) {
                    Spell spell = new Spell(line);
                    spellDao.insert(spell);
                    String[] split = line.split(";");
                    CharacterClass characterClass = new CharacterClass(split[8]);
                    characterClassDao.insert(characterClass);
                    classSpellDao.insert(new CharacterClassSpell(spell.getName(),
                            characterClass.getCharacterClass()));

                }
            } catch (IOException e) {
                e.printStackTrace();
            }
            return null;
        }
    }
}


