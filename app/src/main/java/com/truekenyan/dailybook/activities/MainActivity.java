package com.truekenyan.dailybook.activities;

import android.arch.lifecycle.LiveData;
import android.arch.lifecycle.Observer;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.TabLayout;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.SearchView;
import android.support.v7.widget.Toolbar;

import com.truekenyan.dailybook.R;
import com.truekenyan.dailybook.adapters.HomeAdapter;
import com.truekenyan.dailybook.adapters.TabsAdapter;
import com.truekenyan.dailybook.database.DailyBookDatabase;
import com.truekenyan.dailybook.interfaces.OnItemClick;
import com.truekenyan.dailybook.models.JournalEntry;
import com.truekenyan.dailybook.utilities.AppExecutors;
import com.truekenyan.dailybook.utilities.Constants;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;

public class MainActivity extends AppCompatActivity implements OnItemClick {

    @BindView (R.id.search_view)
    SearchView searchView;
    @BindView (R.id.toolbar)
    Toolbar toolbar;
    @BindView (R.id.tab_layout)
    TabLayout tabLayout;
    @BindView (R.id.view_pager)
    ViewPager viewPager;

    DailyBookDatabase bookDatabase;
    AppExecutors appExecutors;
    LiveData<List<JournalEntry>> listLiveData;
    List<JournalEntry> entryList;

    RecyclerView recyclerView;
    HomeAdapter adapter;
    RecyclerView.LayoutManager layoutManager;

    FloatingActionButton floatingActionButton;

    @Override
    protected void onCreate (Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        ButterKnife.bind(this);

        setSupportActionBar(toolbar);

        recyclerView = findViewById(R.id.recycler);
        layoutManager = new LinearLayoutManager(getApplicationContext());
        adapter = new HomeAdapter(entryList, getApplicationContext(), this);
        recyclerView.setLayoutManager(layoutManager);
        recyclerView.setHasFixedSize(true);
        recyclerView.setItemAnimator(new DefaultItemAnimator());
        recyclerView.setAdapter(adapter);

        entryList = new ArrayList<>();
        bookDatabase = DailyBookDatabase.getDatabaseInstance(getApplicationContext());
        appExecutors = AppExecutors.getExecutors();

        TabsAdapter adapter = new TabsAdapter(getSupportFragmentManager());
        viewPager.setAdapter(adapter);
        tabLayout.setupWithViewPager(viewPager);

        recyclerView.addOnScrollListener(new RecyclerView.OnScrollListener() {
            @Override
            public void onScrolled (RecyclerView recyclerView, int dx, int dy) {
                super.onScrolled(recyclerView, dx, dy);
                if (dy > 0){
                    floatingActionButton.hide();
                } else if (dy < 0){
                    floatingActionButton.show();
                }
            }
        });
    }

    @Override
    public void onListItemClicked (int position) {
        Intent intent = new Intent(getApplicationContext(), DetailsActivity.class);
        intent.putExtra(Constants.POSITION, position);
        startActivity(intent);
    }

    @Override
    protected void onStart () {
        super.onStart();
        listLiveData = bookDatabase.journalDao().getAllEntries();
        listLiveData.observe(this, new Observer<List<JournalEntry>>() {
            @Override
            public void onChanged (@Nullable List<JournalEntry> journalEntries) {
                entryList = journalEntries;
                adapter.setEntryList(entryList);
            }
        });
    }
}
