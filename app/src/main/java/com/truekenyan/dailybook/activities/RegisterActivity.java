package com.truekenyan.dailybook.activities;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.truekenyan.dailybook.R;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

public class RegisterActivity extends AppCompatActivity {

    @BindView (R.id.email_input)
    EditText emailInput;
    @BindView (R.id.password_input)
    EditText passwordInput;

    private FirebaseAuth firebaseAuth;
    private FirebaseUser firebaseUser;

    @Override
    protected void onCreate (Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register);
        ButterKnife.bind(this);

        firebaseAuth = FirebaseAuth.getInstance();
    }

    @OnClick ({R.id.register_button, R.id.got_to_login})
    public void onViewClicked (View view) {
        switch (view.getId()) {
            case R.id.register_button:
                String email = emailInput.getText().toString().trim();
                String password = passwordInput.getText().toString().trim();
                if (password.isEmpty()){
                    passwordInput.setError("Please input password");
                    return;
                }
                if (email.isEmpty()){
                    emailInput.setError("Please input email");
                    return;
                }
                attemptRegister(email, password);
                break;
            case R.id.got_to_login:
                break;
        }
    }

    private void attemptRegister (String email, String password) {
        firebaseAuth
                .createUserWithEmailAndPassword(email, password)
                .addOnCompleteListener(this ,new OnCompleteListener<AuthResult>() {
                    @Override
                    public void onComplete (@NonNull Task<AuthResult> task) {
                        if (task.isSuccessful()) {
                            firebaseUser = firebaseAuth.getCurrentUser();
                            startActivity(new Intent(getApplicationContext(), MainActivity.class));
                        } else {
                            Toast.makeText(RegisterActivity.this, "Error! Unable to register", Toast.LENGTH_SHORT).show();
                            Toast.makeText(RegisterActivity.this, task.getException().getMessage(), Toast.LENGTH_SHORT).show();
                        }
                    }
                });
    }
}
