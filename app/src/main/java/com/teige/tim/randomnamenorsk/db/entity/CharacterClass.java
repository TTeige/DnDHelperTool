package com.teige.tim.randomnamenorsk.db.entity;

import android.arch.persistence.room.ColumnInfo;
import android.arch.persistence.room.Entity;
import android.arch.persistence.room.PrimaryKey;
import android.support.annotation.NonNull;

@Entity(tableName = "character_classes")
public class CharacterClass {

    @PrimaryKey
    @ColumnInfo(name = "class")
    @NonNull
    public String characterClass;

    public CharacterClass(String characterClass) {
        this.characterClass = characterClass;
    }

    public String getCharacterClass() {
        return characterClass;
    }

    public void setCharacterClass(String characterClass) {
        this.characterClass = characterClass;
    }
}
