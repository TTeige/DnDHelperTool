package com.teige.tim.randomnamenorsk.db.entity;

import android.arch.persistence.room.*;
import android.support.annotation.NonNull;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

@Entity(tableName = "spell_table")
public class Spell {

    @ColumnInfo(name = "lvl")
    Integer level;
    @PrimaryKey
    @NonNull
    @ColumnInfo(name = "name")
    String name;
    @ColumnInfo(name = "type")
    String type;
    @ColumnInfo(name = "cast_time")
    String castTime;
    @ColumnInfo(name = "range")
    String range;
    @ColumnInfo(name = "components")
    String components;
    @ColumnInfo(name = "duration")
    String duration;
    @ColumnInfo(name = "description")
    String description;
    @ColumnInfo(name = "classes")
    @Ignore
    private boolean expanded;

    public boolean getExpanded() {
        return expanded;
    }

    public void setExpanded(boolean expanded) {
        this.expanded = expanded;
    }

    public Spell(Integer level, @NonNull String name, String type,
                 String castTime, String range, String components,
                 String duration, String description) {
        this.level = level;
        this.name = name;
        this.type = type;
        this.castTime = castTime;
        this.range = range;
        this.components = components;
        this.duration = duration;
        this.description = description;
        expanded = false;
    }


    public Spell(String spellString) {
        String[] split = spellString.split(";");
        level = Integer.parseInt(split[0]);
        name = split[1];
        type = split[2];
        castTime = split[3];
        range = split[4];
        components = split[5];
        duration = split[6];
        description = split[7];
        expanded = false;
    }

    public Integer getLevel() {
        return level;
    }

    public void setLevel(Integer level) {
        this.level = level;
    }

    @NonNull
    public String getName() {
        return name;
    }

    public void setName(@NonNull String name) {
        this.name = name;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public String getCastTime() {
        return castTime;
    }

    public void setCastTime(String castTime) {
        this.castTime = castTime;
    }

    public String getRange() {
        return range;
    }

    public void setRange(String range) {
        this.range = range;
    }

    public String getComponents() {
        return components;
    }

    public void setComponents(String components) {
        this.components = components;
    }

    public String getDuration() {
        return duration;
    }

    public void setDuration(String duration) {
        this.duration = duration;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        Spell spell = (Spell) o;
        return this.name.equals(spell.name);
    }

    @Override
    public int hashCode() {
        return Objects.hash(name);
    }
}
