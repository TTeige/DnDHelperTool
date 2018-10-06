package com.teige.tim.randomnamenorsk.views;

import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import com.teige.tim.randomnamenorsk.R;

import java.util.ArrayList;

public class RecycleNameListAdapter extends RecyclerView.Adapter<RecycleNameListAdapter.MyViewHolder> {
    private ArrayList<String> dataset;

    // Provide a reference to the views for each data item
    // Complex data items may need more than one view per item, and
    // you provide access to all the views for a data item in a view holder
    public static class MyViewHolder extends RecyclerView.ViewHolder {
        // each data item is just a string in this case
        public TextView firstName;
        public TextView lastName;
        public MyViewHolder(View v) {
            super(v);
            firstName = v.findViewById(R.id.firstName);
            lastName = v.findViewById(R.id.lastName);

        }
    }

    // Provide a suitable constructor (depends on the kind of dataset)
    public RecycleNameListAdapter(ArrayList<String> myDataset) {
        dataset = myDataset;
    }

    public void add(String item) {
        dataset.add(item);
        this.notifyDataSetChanged();
    }

    public void remove(int position) {
        dataset.remove(position);
        this.notifyItemRemoved(position);
    }

    // Create new views (invoked by the layout manager)
    @Override
    public RecycleNameListAdapter.MyViewHolder onCreateViewHolder(ViewGroup parent,
                                                                  int viewType) {
        // create a new view
        View v = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.name_list_item_recycleview, parent, false);
        return new MyViewHolder(v);
    }

    // Replace the contents of a view (invoked by the layout manager)
    @Override
    public void onBindViewHolder(MyViewHolder holder, int position) {
        // - get element from your dataset at this position
        // - replace the contents of the view with that element
        String[] split = dataset.get(position).split(" ");
        String firstName = split[0];
        String lastName = "";
        if (split.length == 2) {
            lastName = split[1];
        }
        holder.firstName.setText(firstName);
        holder.lastName.setText(lastName);

    }

    // Return the size of your dataset (invoked by the layout manager)
    @Override
    public int getItemCount() {
        return dataset.size();
    }
}
