package com.truekenyan.dailybook.activities;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.EditText;

import com.truekenyan.dailybook.R;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

public class PinActivity extends AppCompatActivity {

    @BindView (R.id.pin_input)
    EditText pinInput;

    @Override
    protected void onCreate (Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_pin);
        ButterKnife.bind(this);
    }

    @OnClick ({R.id.confirm_button, R.id.reset_button})
    public void onViewClicked (View view) {
        switch (view.getId()) {
            case R.id.confirm_button:
                break;
            case R.id.reset_button:
                startActivity(new Intent(getApplicationContext(), MainActivity.class));
                break;
        }
    }
}
