package com.teige.tim.randomnamenorsk.db.dao;

import android.arch.lifecycle.LiveData;
import android.arch.persistence.room.*;
import com.teige.tim.randomnamenorsk.db.entity.Spell;

import java.util.List;


@Dao
public interface SpellDao {
    @Query("SELECT * FROM SPELL_TABLE ORDER BY lvl DESC")
    LiveData<List<Spell>> getAll();

    @Insert(onConflict = OnConflictStrategy.IGNORE)
    void insertAll(Spell... spells);

    @Insert(onConflict = OnConflictStrategy.IGNORE)
    void insert(Spell spell);

    @Delete
    void delete(Spell spell);

    @Query("DELETE FROM SPELL_TABLE")
    void deleteAll();

    @Query("SELECT * FROM SPELL_TABLE WHERE name LIKE :query ORDER BY lvl DESC")
    LiveData<List<Spell>> searchByName(String query);

    @Query("select * from spell_table " +
            "JOIN character_class_spell " +
            "ON spell_table.name = character_class_spell.spell_name " +
            "WHERE character_class_spell.class like :className ORDER BY lvl desc;")
    LiveData<List<Spell>> searchByClass(String className);
}
