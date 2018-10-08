package com.teige.tim.randomnamenorsk.db.dao;

import android.arch.persistence.room.*;
import com.teige.tim.randomnamenorsk.db.entity.CharacterClass;

import java.util.List;

@Dao
public interface CharacterClassDao {
    @Query("SELECT * FROM character_classes")
    List<CharacterClass> getAll();

    @Insert(onConflict = OnConflictStrategy.IGNORE)
    void insert(CharacterClass characterClass);

    @Insert(onConflict = OnConflictStrategy.IGNORE)
    void insertAll(CharacterClass... characterClass);

    @Delete
    void delete(CharacterClass characterClass);

    @Query("DELETE FROM CHARACTER_CLASSES")
    void deleteAll();

    @Query("SELECT * FROM CHARACTER_CLASSES WHERE class = (:name)")
    CharacterClass findClass(String name);
}
