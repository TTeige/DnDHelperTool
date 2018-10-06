package com.teige.tim.randomnamenorsk.views;

import android.content.Context;
import android.support.v7.util.SortedList;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import com.teige.tim.randomnamenorsk.R;
import com.teige.tim.randomnamenorsk.db.entity.Spell;

import java.util.Comparator;
import java.util.List;

public class RecycleSpellListAdapter extends RecyclerView.Adapter<RecycleSpellListAdapter.SpellViewHolder> {

    private SortedList<Spell> spellList = new SortedList<>(Spell.class, new SortedList.Callback<Spell>() {

        @Override
        public void onInserted(int position, int count) {
            notifyItemRangeInserted(position, count);
        }

        @Override
        public void onRemoved(int position, int count) {
            notifyItemRangeRemoved(position, count);
        }

        @Override
        public void onMoved(int fromPosition, int toPosition) {
            notifyItemMoved(fromPosition, toPosition);
        }

        @Override
        public int compare(Spell o1, Spell o2) {
            return comparator.compare(o1, o2);
        }

        @Override
        public void onChanged(int position, int count) {
            notifyItemRangeChanged(position, count);
        }

        @Override
        public boolean areContentsTheSame(Spell oldItem, Spell newItem) {
            return oldItem.equals(newItem);
        }

        @Override
        public boolean areItemsTheSame(Spell item1, Spell item2) {
            return item1.getName().equals(item2.getName());
        }
    });
    private LayoutInflater inflater;
    private final Comparator<Spell> comparator;

    public RecycleSpellListAdapter(Context context) {
        inflater = LayoutInflater.from(context);
        comparator = Comparator.comparing(Spell::getName);
    }

    @Override
    public SpellViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View v = inflater.inflate(R.layout.spell_item_recycleview, parent, false);
        return new SpellViewHolder(v);
    }

    @Override
    public void onBindViewHolder(SpellViewHolder holder, int position) {
        try {
            if (spellList != null) {
                Spell spell = spellList.get(position);
                holder.lvl.setText(spell.getLevel().toString());
                holder.spellName.setText(spell.getName());
                holder.castTime.setText(spell.getCastTime());
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @Override
    public int getItemCount() {
        return spellList != null ? spellList.size() : 0;
    }

    public void setSpellList(List<Spell> spellList) {
        this.spellList.addAll(spellList);
    }

    public void remove(List<Spell> spells) {
        spellList.beginBatchedUpdates();
        for (Spell spell : spells) {
            spellList.remove(spell);
        }
        spellList.endBatchedUpdates();
    }

    public void add(Spell spell) {
        spellList.add(spell);
    }

    public void replaceAll(List<Spell> spells) {
        spellList.beginBatchedUpdates();
        for (int i = spellList.size() - 1; i >= 0; i--) {
            final Spell spell = spellList.get(i);
            if (!spells.contains(spell)) {
                spellList.remove(spell);
            }
        }
        spellList.addAll(spells);
        spellList.endBatchedUpdates();
    }

    public static class SpellViewHolder extends RecyclerView.ViewHolder {
        public TextView lvl;
        public TextView spellName;
        public TextView castTime;

        public SpellViewHolder(View itemView) {
            super(itemView);
            lvl = itemView.findViewById(R.id.spell_lvl);
            spellName = itemView.findViewById(R.id.spell_title);
            castTime = itemView.findViewById(R.id.spell_cast_time);
        }
    }
}
