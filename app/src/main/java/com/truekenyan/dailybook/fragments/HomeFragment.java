package com.truekenyan.dailybook.fragments;

import android.arch.lifecycle.LiveData;
import android.arch.lifecycle.Observer;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.design.widget.FloatingActionButton;
import android.support.v4.app.Fragment;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.truekenyan.dailybook.R;
import com.truekenyan.dailybook.activities.WriteActivity;
import com.truekenyan.dailybook.adapters.HomeAdapter;
import com.truekenyan.dailybook.database.DailyBookDatabase;
import com.truekenyan.dailybook.models.JournalEntry;
import com.truekenyan.dailybook.utilities.AppExecutors;

import java.util.ArrayList;
import java.util.List;

import butterknife.ButterKnife;
import butterknife.OnClick;
import butterknife.Unbinder;

/**
 * Created by password
 * on 6/27/18.
 */

@SuppressWarnings ("WeakerAccess")
public class HomeFragment extends Fragment {
    private FloatingActionButton fab;
    Unbinder unbinder;

    private static List<JournalEntry> newList = new ArrayList<>();

    private HomeAdapter adapter;

    private DailyBookDatabase database;
    private AppExecutors appExecutors;
    private LiveData<List<JournalEntry>> liveData;

    @Nullable
    @Override
    public View onCreateView (@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_home, container, false);

        database = DailyBookDatabase.getDatabaseInstance(getContext());
        appExecutors = AppExecutors.getExecutors();

        RecyclerView recycler = view.findViewById(R.id.recycler);
        fab = view.findViewById(R.id.fab);

        List<JournalEntry> entryList = new ArrayList<>();
        adapter = new HomeAdapter(entryList,getContext());
        RecyclerView.LayoutManager layoutManager = new LinearLayoutManager(getContext());
        recycler.setHasFixedSize(true);
        recycler.setItemAnimator(new DefaultItemAnimator());
        recycler.setAdapter(adapter);
        recycler.setLayoutManager(layoutManager);
        unbinder = ButterKnife.bind(this, view);

        recycler.addOnScrollListener(new RecyclerView.OnScrollListener() {
            @Override
            public void onScrolled (RecyclerView recyclerView, int dx, int dy) {
                super.onScrolled(recyclerView, dx, dy);
                if (dy > 0){
                    fab.hide();
                } else if (dy < 0){
                    fab.show();
                }
            }
        });

        return view;
    }

    @Override
    public void onDestroyView () {
        super.onDestroyView();
        unbinder.unbind();
    }

    @OnClick (R.id.fab)
    public void onViewClicked () {
        Intent intent = new Intent(getContext(), WriteActivity.class);
        intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
        startActivity(intent);
    }

    @Override
    public void onStart () {
        super.onStart();
        appExecutors
                .getDiskIO()
                .execute(new Runnable() {
                    @Override
                    public void run () {
                        liveData = database.journalDao().getAllEntries();
                    }
                });

        assert getActivity() != null;
        liveData.observe(getActivity(), new Observer<List<JournalEntry>>() {
            @Override
            public void onChanged (@Nullable List<JournalEntry> journalEntries) {
                adapter.setEntryList(journalEntries);
                newList = journalEntries;
            }
        });
    }

    public static List<JournalEntry> getList(){
        return newList;
    }

}
