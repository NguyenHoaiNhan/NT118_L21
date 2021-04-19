package com.kane.healthapp;
import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.text.method.HideReturnsTransformationMethod;
import android.text.method.PasswordTransformationMethod;
import android.view.MotionEvent;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;

public class Login extends AppCompatActivity {
    private Button btnLogin = null;
    private TextView lgStatus = null;
    private TextView rgtForm = null;
    private TextView getPasswordForm = null;
    private ImageButton lgPassword = null;
    private FirebaseAuth mAuth = null;

    /* Main */
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        init();
        lgClicked();
        rgtClicked();
        get_pwClicked();
        showPassword();
    }
    /* End Main */

    private void init() {
        btnLogin = (Button) findViewById(R.id.loginButton);
        lgStatus = (TextView) findViewById(R.id.loginStatus);
        getPasswordForm = (TextView) findViewById(R.id.forgotPasswordForm);
        rgtForm = (TextView) findViewById(R.id.registerForm);
        lgPassword = (ImageButton) findViewById(R.id.passwordVisibility);
        mAuth = FirebaseAuth.getInstance();
    }

    private void showPassword() {
        EditText Password = (EditText) findViewById(R.id.loginPassword);

        lgPassword.setOnTouchListener(new View.OnTouchListener() {
            @Override
            public boolean onTouch(View v, MotionEvent event) {
                if (event.getAction() == MotionEvent.ACTION_DOWN) {
                    lgPassword.setImageResource(R.drawable.visible_eye);
                    Password.setTransformationMethod(HideReturnsTransformationMethod.getInstance());
                }
                else if (event.getAction() == MotionEvent.ACTION_UP) {
                    lgPassword.setImageResource(R.drawable.invisible_eye);
                    Password.setTransformationMethod(PasswordTransformationMethod.getInstance());
                }
                return false;
            }
        });
    }

    private void lgClicked() {
        btnLogin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (isValid()) {
                    login();
                }
                else {
                    lgStatus.setText("Failed");
                }
            }
        });
    }

    private void login() {
        String email = ((EditText) findViewById(R.id.loginUserName)).getText().toString();
        String password = ((EditText) findViewById(R.id.loginPassword)).getText().toString();

        mAuth.signInWithEmailAndPassword(email, password).addOnCompleteListener(this, new OnCompleteListener<AuthResult>() {
            @Override
            public void onComplete(@NonNull Task<AuthResult> task) {
                if (task.isSuccessful()) {
                    Toast.makeText(Login.this, "Logged in successful.", Toast.LENGTH_SHORT).show();
                    startActivity(new Intent(getApplicationContext(), MainActivity.class));
                }
                else {
                    Toast.makeText(Login.this, "Error: " + task.getException().toString(), Toast.LENGTH_SHORT).show();
                }
            }
        });
    }

    private boolean isValid() {
        EditText Email = (EditText) findViewById(R.id.loginUserName);
        EditText Password = (EditText) findViewById(R.id.loginPassword);

        if (TextUtils.isEmpty(Email.getText().toString())) {
            Email.setError("Email is required");
            return false;
        }

        if (TextUtils.isEmpty(Password.getText().toString())) {
            Password.setError("Password is required");
            return false;
        }
        return true;
    }

    private void rgtClicked() {
        rgtForm.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //startActivity(new Intent(getApplicationContext(), Register.class));
            }
        });
    }

    private void get_pwClicked() {
        getPasswordForm.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //startActivity(new Intent(getApplicationContext(), ForgotPassword.class));
            }
        });
    }
}