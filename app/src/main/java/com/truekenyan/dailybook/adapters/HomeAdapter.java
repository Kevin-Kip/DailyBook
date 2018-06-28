package com.truekenyan.dailybook.adapters;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.truekenyan.dailybook.R;
import com.truekenyan.dailybook.interfaces.OnItemClick;
import com.truekenyan.dailybook.models.JournalEntry;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by password
 * on 6/28/18.
 */

public class HomeAdapter extends RecyclerView.Adapter<HomeAdapter.MyViewHolder> {

    private List<JournalEntry> entryList = new ArrayList<>();
    private Context context;
    private OnItemClick itemClick;

    public HomeAdapter (List<JournalEntry> entryList, Context context, OnItemClick itemClick) {
        this.entryList = entryList;
        this.context = context;
        this.itemClick = itemClick;
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

    public List<JournalEntry> getEntryList(){
        return entryList;
    }

    public void setEntryList(List<JournalEntry> list){
        entryList = list;
        notifyDataSetChanged();
    }

    class MyViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {

        TextView dateView;
        TextView monthView;
        TextView dayView;
        TextView yearView;
        TextView timeView;
        TextView contentView;


        public MyViewHolder (View itemView) {
            super(itemView);

            dateView = itemView.findViewById(R.id.date_display);
            monthView = itemView.findViewById(R.id.month_display);
            dayView = itemView.findViewById(R.id.day_display);
            yearView = itemView.findViewById(R.id.year_display);
            timeView = itemView.findViewById(R.id.time_display);
            contentView = itemView.findViewById(R.id.content_display);

            dateView.setOnClickListener(this);
            dayView.setOnClickListener(this);
            monthView.setOnClickListener(this);
            yearView.setOnClickListener(this);
            timeView.setOnClickListener(this);
            contentView.setOnClickListener(this);
        }

        @Override
        public void onClick (View view) {
            itemClick.onListItemClicked(getAdapterPosition());
        }
    }
}
