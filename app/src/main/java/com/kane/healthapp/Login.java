package com.kane.healthapp;


import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.os.Bundle;
import android.text.method.HideReturnsTransformationMethod;
import android.text.method.PasswordTransformationMethod;
import android.util.Log;
import android.view.MotionEvent;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;

public class Login extends AppCompatActivity {
    private EditText et_Email;
    private EditText et_Password;
    private Button btn_Login;
    private ImageButton imgbtn_View;
    private FirebaseAuth mAuth;
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

    private void init() {
        btn_Login = findViewById(R.id.btn_Login);
        imgbtn_View = findViewById(R.id.igmbtn_View);
        mAuth = FirebaseAuth.getInstance();
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
                mAuth.signInWithEmailAndPassword(Email, Password).addOnCompleteListener(Login.this, task -> {
                    if (task.isSuccessful()) {
                        startActivity(new Intent(Login.this, MainActivity.class));
                    } else {
                        Toast.makeText(Login.this, "Please check your Email and Password again", Toast.LENGTH_SHORT).show();
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