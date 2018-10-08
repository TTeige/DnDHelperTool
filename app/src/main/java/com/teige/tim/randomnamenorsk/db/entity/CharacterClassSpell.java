package com.teige.tim.randomnamenorsk.db.entity;

import android.arch.persistence.room.ColumnInfo;
import android.arch.persistence.room.Entity;
import android.arch.persistence.room.PrimaryKey;
import android.support.annotation.NonNull;

@Entity(tableName = "character_class_spell", primaryKeys = {"spell_name", "class"})
public class CharacterClassSpell {
    @NonNull
    @ColumnInfo(name = "spell_name")
    public String spellName;
    @NonNull
    @ColumnInfo(name = "class")
    public String characterClass;

    public CharacterClassSpell(@NonNull String spellName, @NonNull String characterClass) {
        this.spellName = spellName;
        this.characterClass = characterClass;
    }

    @NonNull
    public String getSpellName() {
        return spellName;
    }

    public void setSpellName(@NonNull String spellName) {
        this.spellName = spellName;
    }

    @NonNull
    public String getCharacterClass() {
        return characterClass;
    }

    public void setCharacterClass(@NonNull String characterClass) {
        this.characterClass = characterClass;
    }
}
