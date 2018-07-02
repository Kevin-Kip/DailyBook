package com.truekenyan.dailybook.adapters;

import android.content.Context;
import android.content.Intent;
import android.support.annotation.NonNull;
import android.support.v7.widget.CardView;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.truekenyan.dailybook.R;
import com.truekenyan.dailybook.activities.DetailsActivity;
import com.truekenyan.dailybook.interfaces.OnItemClick;
import com.truekenyan.dailybook.models.JournalEntry;
import com.truekenyan.dailybook.utilities.Constants;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by password
 * on 6/28/18.
 */

public class HomeAdapter extends RecyclerView.Adapter<HomeAdapter.MyViewHolder> {

    private   List<JournalEntry> entryList = new ArrayList<>();
    private final Context context;
    private OnItemClick onItemClick;

    public HomeAdapter (List<JournalEntry> entryList, Context context) {
        this.entryList = entryList;
        this.context = context;
    }

    @NonNull
    @Override
    public MyViewHolder onCreateViewHolder (@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(context).inflate(R.layout.card_list_item, parent, false);
        return new MyViewHolder(view);
    }

    @Override
    public void onBindViewHolder (@NonNull MyViewHolder holder, int position) {
        JournalEntry journalEntry = entryList.get(position);
        holder.dateView.setText(journalEntry.getWriteDate());
        holder.monthView.setText(journalEntry.getWriteMonth());
        holder.dayView.setText(journalEntry.getWriteDay());
        holder.yearView.setText(journalEntry.getWriteYear());
        holder.timeView.setText(journalEntry.getWriteTime());
        holder.contentView.setText(journalEntry.getContent());
    }

    @Override
    public int getItemCount () {
        return entryList.size();
    }

    public void setEntryList(List<JournalEntry> list){
        entryList = list;
        notifyDataSetChanged();
    }

    class MyViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {

        final TextView dateView;
        final TextView monthView;
        final TextView dayView;
        final TextView yearView;
        final TextView timeView;
        final TextView contentView;
        final CardView mainParent;


        private MyViewHolder (View itemView) {
            super(itemView);

            dateView = itemView.findViewById(R.id.date_display);
            monthView = itemView.findViewById(R.id.month_display);
            dayView = itemView.findViewById(R.id.day_display);
            yearView = itemView.findViewById(R.id.year_display);
            timeView = itemView.findViewById(R.id.time_display);
            contentView = itemView.findViewById(R.id.content_display);
            mainParent = itemView.findViewById(R.id.main_parent);

            dateView.setOnClickListener(this);
            monthView.setOnClickListener(this);
            dayView.setOnClickListener(this);
            yearView.setOnClickListener(this);
            timeView.setOnClickListener(this);
            contentView.setOnClickListener(this);
            mainParent.setOnClickListener(this);
        }

        @Override
        public void onClick (View view) {
            int position = getAdapterPosition();
            onItemClick.onItemClick(position);
        }
    }
}
