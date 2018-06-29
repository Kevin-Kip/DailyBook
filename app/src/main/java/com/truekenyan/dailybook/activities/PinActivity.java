package com.truekenyan.dailybook.activities;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.EditText;

import com.afollestad.materialdialogs.DialogAction;
import com.afollestad.materialdialogs.MaterialDialog;
import com.truekenyan.dailybook.R;
import com.truekenyan.dailybook.utilities.Constants;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

@SuppressWarnings ("WeakerAccess")
public class PinActivity extends AppCompatActivity {

    @BindView (R.id.pin_input)
    EditText pinInput;

    private int savedPin;

    @Override
    protected void onCreate (Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_pin);
        ButterKnife.bind(this);

        startActivity(new Intent(getApplicationContext(), MainActivity.class));

        SharedPreferences sharedPreferences = getSharedPreferences(Constants.PIN_COLUMN, MODE_PRIVATE);
        if (sharedPreferences.contains(Constants.PIN_COLUMN)){
            savedPin = sharedPreferences.getInt(Constants.PIN_COLUMN, 0);
        } else {
            startActivity(new Intent(getApplicationContext(), NewPinActivity.class));
            finish();
        }
    }

    @OnClick ({R.id.confirm_button, R.id.reset_button})
    public void onViewClicked (View view) {
        switch (view.getId()) {
            case R.id.confirm_button:
                String input = pinInput.getText().toString().trim();
                if (input.length() < 4){
                    pinInput.setError(getString(R.string.too_short_error));
                    return;
                }
                int pin = Integer.parseInt(input);
                if (pin == savedPin){
                    startActivity(new Intent(getApplicationContext(), MainActivity.class));
                    finish();
                } else {
                    new MaterialDialog.Builder(PinActivity.this)
                            .title("Error")
                            .content("Dou want to reset Pin?")
                            .positiveText("YES")
                            .positiveText("NO")
                            .onNegative(new MaterialDialog.SingleButtonCallback() {
                                @Override
                                public void onClick (@NonNull MaterialDialog dialog, @NonNull DialogAction which) {
                                    dialog.dismiss();
                                }
                            })
                            .onPositive(new MaterialDialog.SingleButtonCallback() {
                                @Override
                                public void onClick (@NonNull MaterialDialog dialog, @NonNull DialogAction which) {
                                    dialog.dismiss();
                                    startActivity(new Intent(getApplicationContext(), NewPinActivity.class));
                                    finish();
                                }
                            })
                            .show();
                }
                break;
            case R.id.reset_button:
                startActivity(new Intent(getApplicationContext(), NewPinActivity.class));
                finish();
                break;
        }
    }
}
