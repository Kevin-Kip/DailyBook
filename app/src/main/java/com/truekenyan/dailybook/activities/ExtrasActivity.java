package com.truekenyan.dailybook.activities;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.View;

import com.afollestad.materialdialogs.DialogAction;
import com.afollestad.materialdialogs.MaterialDialog;
import com.truekenyan.dailybook.R;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import butterknife.Unbinder;

/**
 * Created by password
 * on 6/27/18.
 */

@SuppressWarnings ({"WeakerAccess", "unused"})
public class ExtrasActivity extends AppCompatActivity {
    Unbinder unbinder;
    @BindView (R.id.toolbar)
    Toolbar toolbar;

    @Override
    protected void onCreate (@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_extras);
        ButterKnife.bind(this);

        setSupportActionBar(toolbar);
        assert getSupportActionBar() != null;
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
    }

    @OnClick ({R.id.change_pin, R.id.about_me, R.id.close_app})
    public void onViewClicked (View view) {
        switch (view.getId()) {
            case R.id.change_pin:
                startActivity(new Intent(getApplicationContext(), NewPinActivity.class));
                break;
            case R.id.about_me:
                new MaterialDialog.Builder(ExtrasActivity.this)
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
                new MaterialDialog.Builder(ExtrasActivity.this)
                        .title("Confirmation")
                        .content("Are you sure you want to exit?")
                        .positiveText("SURE")
                        .negativeText("No")
                        .onPositive(new MaterialDialog.SingleButtonCallback() {
                            @Override
                            public void onClick (@NonNull MaterialDialog dialog, @NonNull DialogAction which) {
                                dialog.dismiss();
                                System.exit(0);
                                finishAffinity();
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
