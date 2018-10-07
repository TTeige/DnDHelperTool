package com.teige.tim.randomnamenorsk.views;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.text.Html;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import com.teige.tim.randomnamenorsk.R;
import com.teige.tim.randomnamenorsk.db.entity.Spell;

import java.util.ArrayList;
import java.util.List;

public class RecycleSpellListAdapter extends RecyclerView.Adapter<RecycleSpellListAdapter.SpellViewHolder> {

    private LayoutInflater inflater;
    private List<Spell> spellList;
    private Context context;
    public RecycleSpellListAdapter(Context context) {
        inflater = LayoutInflater.from(context);
        this.context = context;
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
                Spell spell = spellList.get(holder.getAdapterPosition());
                holder.spellName.setText(spell.getName());
                holder.castTime.setText(context.getString(R.string.spell_cast_time_placeholder, spell.getCastTime()));
                holder.type.setText(spell.getType());
                holder.components.setText(context.getString(R.string.spell_components_placeholder, spell.getComponents()));
                holder.castRange.setText(context.getString(R.string.spell_range_placeholder,spell.getRange()));
                holder.description.setText(Html.fromHtml(spell.getDescription(), Html.FROM_HTML_MODE_COMPACT));
                holder.duration.setText(context.getString(R.string.spell_duration_placeholder,spell.getDuration()));

                holder.itemView.findViewById(R.id.spell_sub_item).setVisibility(spell.getExpanded() ? View.VISIBLE : View.GONE);

                holder.itemView.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        spell.setExpanded(!spell.getExpanded());
                        notifyItemChanged(holder.getAdapterPosition());
                    }
                });
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
        this.spellList = spellList;
        notifyDataSetChanged();
    }

    public void add(Spell spell) {
        spellList.add(spell);
    }


    public static class SpellViewHolder extends RecyclerView.ViewHolder {
        public TextView spellName;
        public TextView castTime;
        public TextView type;
        public TextView components;
        public TextView castRange;
        public TextView description;
        public TextView duration;


        public SpellViewHolder(View itemView) {
            super(itemView);
            spellName = itemView.findViewById(R.id.spell_title);
            castTime = itemView.findViewById(R.id.spell_cast_time);
            type = itemView.findViewById(R.id.spell_type);
            duration = itemView.findViewById(R.id.spell_duration);
            components = itemView.findViewById(R.id.spell_components);
            castRange = itemView.findViewById(R.id.spell_cast_range);
            description = itemView.findViewById(R.id.spell_description);
        }
    }
}
