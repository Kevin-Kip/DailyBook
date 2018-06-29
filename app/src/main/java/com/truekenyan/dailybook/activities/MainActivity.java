package com.truekenyan.dailybook.activities;

import android.arch.lifecycle.LiveData;
import android.arch.lifecycle.Observer;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.design.widget.FloatingActionButton;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.SearchView;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;

import com.truekenyan.dailybook.R;
import com.truekenyan.dailybook.adapters.HomeAdapter;
import com.truekenyan.dailybook.database.DailyBookDatabase;
import com.truekenyan.dailybook.models.JournalEntry;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

@SuppressWarnings ("WeakerAccess")
public class MainActivity extends AppCompatActivity {

    @BindView (R.id.search_view)
    SearchView searchView;
    @BindView (R.id.toolbar)
    Toolbar toolbar;
    @BindView (R.id.recycler)
    RecyclerView recycler;
    @BindView (R.id.fab)
    FloatingActionButton fab;

    private DailyBookDatabase database;
    private HomeAdapter adapter;
    private static List<JournalEntry> newList;

    @Override
    protected void onCreate (Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        ButterKnife.bind(this);

        setSupportActionBar(toolbar);

        List<JournalEntry> entryList = new ArrayList<>();
        database = DailyBookDatabase.getDatabaseInstance(getApplicationContext());

        adapter = new HomeAdapter(entryList, getApplicationContext());
        RecyclerView.LayoutManager layoutManager = new LinearLayoutManager(getApplicationContext());
        recycler.setAdapter(adapter);
        recycler.setLayoutManager(layoutManager);
        recycler.setItemAnimator(new DefaultItemAnimator());
        recycler.setHasFixedSize(true);

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
    }

    @Override
    public boolean onCreateOptionsMenu (Menu menu) {
        getMenuInflater().inflate(R.menu.mein_menu, menu);
        return super.onCreateOptionsMenu(menu);
    }

    @Override
    public boolean onOptionsItemSelected (MenuItem item) {
        if (item.getItemId() == R.id.settings){
            startActivity(new Intent(getApplicationContext(), ExtrasActivity.class));
        }
        return super.onOptionsItemSelected(item);
    }

    @OnClick (R.id.fab)
    public void onViewClicked () {
        Intent intent = new Intent(getApplicationContext(), WriteActivity.class);
        intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
        startActivity(intent);
    }

    @Override
    protected void onResume () {
        super.onResume();
        LiveData<List<JournalEntry>> listLiveData = database.journalDao().getAllEntries();
        listLiveData.observe(this, new Observer<List<JournalEntry>>() {
            @Override
            public void onChanged (@Nullable List<JournalEntry> list) {
                adapter.setEntryList(list);
                newList = list;
            }
        });
    }

    public static List<JournalEntry> getList(){
        return newList;
    }
}
