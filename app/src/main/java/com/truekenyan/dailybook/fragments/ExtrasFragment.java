package com.truekenyan.dailybook.fragments;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.afollestad.materialdialogs.DialogAction;
import com.afollestad.materialdialogs.MaterialDialog;
import com.truekenyan.dailybook.R;
import com.truekenyan.dailybook.activities.NewPinActivity;

import butterknife.ButterKnife;
import butterknife.OnClick;
import butterknife.Unbinder;

/**
 * Created by password
 * on 6/27/18.
 */

@SuppressWarnings ({"WeakerAccess", "unused"})
public class ExtrasFragment extends Fragment {
    Unbinder unbinder;

    private Context context;
    @Nullable
    @Override
    public View onCreateView (@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_extras, container, false);
        context = getContext();
        unbinder = ButterKnife.bind(this, view);
        return view;
    }

    @Override
    public void onDestroyView () {
        super.onDestroyView();
        unbinder.unbind();
    }

    @OnClick ({R.id.change_pin, R.id.about_me, R.id.close_app})
    public void onViewClicked (View view) {
        switch (view.getId()) {
            case R.id.change_pin:
                startActivity(new Intent(context, NewPinActivity.class));
                break;
            case R.id.about_me:
                new MaterialDialog.Builder(context)
                        .title("About me")
                        .content(getResources().getString(R.string.long_text))
                        .neutralText("CLOSE")
                        .onNeutral(new MaterialDialog.SingleButtonCallback() {
                            @Override
                            public void onClick (@NonNull MaterialDialog dialog, @NonNull DialogAction which) {
                                dialog.dismiss();
                            }
                        })
                        .show();
                break;
            case R.id.close_app:
                assert getContext() != null;
                new MaterialDialog.Builder(context)
                        .title("Confirmation")
                        .content("Are you sure you want to exit?")
                        .positiveText("SURE")
                        .negativeText("No")
                        .onPositive(new MaterialDialog.SingleButtonCallback() {
                            @Override
                            public void onClick (@NonNull MaterialDialog dialog, @NonNull DialogAction which) {
                                dialog.dismiss();
                                System.exit(0);
                                getActivity().finishAffinity();
                            }
                        })
                        .onNegative(new MaterialDialog.SingleButtonCallback() {
                            @Override
                            public void onClick (@NonNull MaterialDialog dialog, @NonNull DialogAction which) {
                                dialog.dismiss();
                            }
                        })
                        .show();
                break;
        }
    }
}
