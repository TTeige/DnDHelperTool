package com.teige.tim.randomnamenorsk.db.dao;

import android.arch.persistence.room.*;
import com.teige.tim.randomnamenorsk.db.entity.CharacterClassSpell;

import java.util.List;

@Dao
public interface CharacterClassSpellDao {
    @Insert(onConflict = OnConflictStrategy.IGNORE)
    void insert(CharacterClassSpell characterClassSpell);

    @Insert(onConflict = OnConflictStrategy.IGNORE)
    void insertAll(CharacterClassSpell... characterClassSpells);

    @Delete
    void delete(CharacterClassSpell characterClassSpell);

    @Query("DELETE FROM character_class_spell")
    void deleteAll();

    @Query("SELECT * FROM character_class_spell")
    List<CharacterClassSpell> getAll();
}
