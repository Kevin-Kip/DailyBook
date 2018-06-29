package com.truekenyan.dailybook.activities;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.EditText;

import com.truekenyan.dailybook.R;
import com.truekenyan.dailybook.utilities.Constants;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

@SuppressWarnings ("WeakerAccess")
public class NewPinActivity extends AppCompatActivity {

    @BindView (R.id.pin_input)
    EditText pinInput;
    @BindView (R.id.confirm_pin_input)
    EditText confirmPinInput;

    private SharedPreferences sharedPreferences;

    @Override
    protected void onCreate (Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_new_pin);
        ButterKnife.bind(this);

        sharedPreferences = getSharedPreferences(Constants.PIN_COLUMN, MODE_PRIVATE);
    }

    @OnClick ({R.id.confirm_button, R.id.back_to_sign_in})
    public void onViewClicked (View view) {
        switch (view.getId()) {
            case R.id.confirm_button:
                String pin1 = pinInput.getText().toString().trim();
                String pin2 = confirmPinInput.getText().toString().trim();
                if (pinInput.getText().length() < 4){
                    pinInput.setError("Minimum length is 4.");
                    return;
                } else if (confirmPinInput.getText().length() < 4){
                    confirmPinInput.setError("Minimum length is 4");
                    return;
                } else if (pinInput.getText().length() >= 4 && pinInput.getText().length() >= 4){
                    int pin = Integer.parseInt(pin1);
                    int confirmPin = Integer.parseInt(pin2);
                    if (pin == confirmPin){
                        SharedPreferences.Editor editor = sharedPreferences.edit();
                        editor.putInt(Constants.PIN_COLUMN, confirmPin);
                        editor.apply();
                        startActivity(new Intent(getApplicationContext(), MainActivity.class));
                        finish();
                    } else {
                        confirmPinInput.setError("Please input matching Pin");
                        return;
                    }
                }
                break;
            case R.id.back_to_sign_in:
                startActivity(new Intent(getApplicationContext(), PinActivity.class));
                finish();
                break;
        }
    }
}
