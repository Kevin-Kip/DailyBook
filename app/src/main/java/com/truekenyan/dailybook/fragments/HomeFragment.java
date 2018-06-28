package com.truekenyan.dailybook.fragments;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.design.widget.FloatingActionButton;
import android.support.v4.app.Fragment;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.truekenyan.dailybook.R;
import com.truekenyan.dailybook.activities.WriteActivity;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import butterknife.Unbinder;

/**
 * Created by password
 * on 6/27/18.
 */

public class HomeFragment extends Fragment {
    @BindView (R.id.recycler)
    RecyclerView recycler;
    @BindView (R.id.fab)
    FloatingActionButton fab;
    Unbinder unbinder;

    @Nullable
    @Override
    public View onCreateView (@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_home, container, false);
        unbinder = ButterKnife.bind(this, view);
        return view;
    }

    @Override
    public void onDestroyView () {
        super.onDestroyView();
        unbinder.unbind();
    }

    @OnClick (R.id.fab)
    public void onViewClicked () {
        startActivity(new Intent(getContext(), WriteActivity.class));
    }
}
