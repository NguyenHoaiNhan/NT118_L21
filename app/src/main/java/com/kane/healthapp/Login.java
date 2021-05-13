package com.kane.healthapp;


import androidx.appcompat.app.AppCompatActivity;

import android.annotation.SuppressLint;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.text.method.HideReturnsTransformationMethod;
import android.text.method.PasswordTransformationMethod;
import android.view.MotionEvent;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.Toast;

public class Login extends AppCompatActivity {
    private EditText et_Email;
    private EditText et_Password;
    private Button btn_Login;
    private ImageButton imgbtn_View;

    private String Email;
    private String Password;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        init();

        btn_LoginClicked();
        imgbtn_ViewClicked();
    }

    public static Context getAppContext() {
        return Login.context;
    }

    private void init() {
        Login.context = getAppContext();
        btn_Login = findViewById(R.id.btn_Login);
        imgbtn_View = findViewById(R.id.igmbtn_View);
        et_Email = findViewById(R.id.et_Email);
        et_Password = findViewById(R.id.et_Password);
    }

    private boolean isValid() {
        Email = et_Email.getText().toString();
        Password = et_Password.getText().toString();

        if (Email.equals("")) {
            et_Email.setError("Email is required");
            return false;
        }
        if (Password.equals("")) {
            et_Password.setError("Password is required");
            return false;
        }
        return true;
    }

    private void btn_LoginClicked() {
        btn_Login.setOnClickListener(v -> {
            if (isValid()) {
                UserHandler.fb_Auth.signInWithEmailAndPassword(Email, Password).addOnCompleteListener(Login.this, task -> {
                    if (task.isSuccessful()) {
                        startActivity(new Intent(Login.this, MainActivity.class));
                    } else {
                        Toast.makeText(Login.this, "Please check your Email and Password", Toast.LENGTH_SHORT).show();
                    }
                });
            }
        });
    }

    @SuppressLint("ClickableViewAccessibility")
    private void imgbtn_ViewClicked() {
        imgbtn_View.setOnTouchListener((v, event) -> {
            if (event.getAction() == MotionEvent.ACTION_UP) {
                imgbtn_View.setImageResource(R.drawable.invisible_eye);
                et_Password.setTransformationMethod(PasswordTransformationMethod.getInstance());
            }
            if (event.getAction() == MotionEvent.ACTION_DOWN) {
                imgbtn_View.setImageResource(R.drawable.visible_eye);
                et_Password.setTransformationMethod(HideReturnsTransformationMethod.getInstance());
            }
            return false;
        });
    }
}