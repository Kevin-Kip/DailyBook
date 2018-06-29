package com.truekenyan.dailybook.activities;

import android.os.Bundle;
import android.support.design.widget.TabLayout;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.SearchView;
import android.support.v7.widget.Toolbar;

import com.truekenyan.dailybook.R;
import com.truekenyan.dailybook.adapters.TabsAdapter;

import butterknife.BindView;
import butterknife.ButterKnife;

@SuppressWarnings ("WeakerAccess")
public class MainActivity extends AppCompatActivity {

    @BindView (R.id.search_view)
    SearchView searchView;
    @BindView (R.id.toolbar)
    Toolbar toolbar;
    @BindView (R.id.tab_layout)
    TabLayout tabLayout;
    @BindView (R.id.view_pager)
    ViewPager viewPager;

    @Override
    protected void onCreate (Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        ButterKnife.bind(this);

        setSupportActionBar(toolbar);

        TabsAdapter adapter = new TabsAdapter(getSupportFragmentManager());
        viewPager.setAdapter(adapter);
        tabLayout.setupWithViewPager(viewPager);
    }
}
