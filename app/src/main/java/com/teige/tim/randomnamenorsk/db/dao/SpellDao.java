package com.teige.tim.randomnamenorsk.db.dao;

import android.arch.lifecycle.LiveData;
import android.arch.persistence.room.Dao;
import android.arch.persistence.room.Delete;
import android.arch.persistence.room.Insert;
import android.arch.persistence.room.Query;
import com.teige.tim.randomnamenorsk.db.entity.Spell;

import java.util.List;

@Dao
public interface SpellDao {
    @Query("SELECT * FROM SPELL_TABLE ORDER BY lvl DESC")
    LiveData<List<Spell>> getAll();

    @Query("SELECT * FROM SPELL_TABLE WHERE class IN (:classes)")
    LiveData<List<Spell>> getByClass(String[] classes);

    @Insert
    void insertAll(Spell... spells);

    @Insert
    void insert(Spell spell);

    @Delete
    void delete(Spell spell);

    @Query("DELETE FROM spell_table")
    void deleteAll();

    @Query("SELECT * FROM spell_table WHERE name LIKE (:query)")
    LiveData<List<Spell>> searchByName(String query);
}
