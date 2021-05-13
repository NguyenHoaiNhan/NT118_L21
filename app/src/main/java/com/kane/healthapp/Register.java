package com.kane.healthapp;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.annotation.SuppressLint;
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
import com.google.firebase.auth.FirebaseAuthInvalidCredentialsException;
import com.google.firebase.auth.FirebaseAuthUserCollisionException;
import com.google.firebase.auth.FirebaseAuthWeakPasswordException;

import java.util.Objects;


public class Register extends AppCompatActivity {
    private FirebaseAuth mAuth;
    private EditText et_rgGmail;
    private EditText et_rgPassword;
    private EditText et_rgRetypePassword;
    private EditText et_rgPhoneNum;
    private ImageButton imgbtn_View0;
    private ImageButton imgbtn_View1;
    private Button btn_Register;

    private String Gmail;
    private String Password;
    private String RetypePassword;
    private String PhoneNum;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register);

        init();
        btn_RegisterClicked();
        imgbtn_View1Clicked();
        imgbtn_View0Clicked();

    }

    private void init() {
        et_rgGmail = findViewById(R.id.et_rgGmail);
        et_rgPassword = findViewById(R.id.et_rgPassword);
        et_rgRetypePassword = findViewById(R.id.et_rgRetypePassword);
        et_rgPhoneNum = findViewById(R.id.et_rgPhoneNum);
        imgbtn_View0 = findViewById(R.id.imgbtn_View0);
        imgbtn_View1 = findViewById(R.id.imgbtn_View1);
        btn_Register = findViewById(R.id.btn_Register);
        mAuth = FirebaseAuth.getInstance();
    }

    private boolean isValid() {
        boolean check = true;
        Gmail = et_rgGmail.getText().toString();
        Password = et_rgPassword.getText().toString();
        RetypePassword = et_rgRetypePassword.getText().toString();
        PhoneNum = et_rgPhoneNum.getText().toString();

        if (Gmail.equals("")) {
            et_rgGmail.setError("Email is required");
            check = false;
        }
        if (Password.equals("") || Password.length() <= 8) {
            et_rgPassword.setError("Password needs to be at least 8 characters");
            check = false;
        }
        if (RetypePassword.equals("") || !RetypePassword.equals(Password)) {
            et_rgRetypePassword.setError("Retype your password");
            check = false;
        }
        if (PhoneNum.length() != 10) {
            et_rgPhoneNum.setError("Phone number needs to be 10 characters");
            check = false;
        }

        return check;
    }

    private void btn_RegisterClicked() {
        btn_Register.setOnClickListener(v -> {
            if (isValid()) {
                mAuth.createUserWithEmailAndPassword(Gmail, Password).addOnCompleteListener(new OnCompleteListener<AuthResult>() {
                    @Override
                    public void onComplete(@NonNull Task<AuthResult> task) {
                        if (task.isSuccessful()) {
                            Toast.makeText(Register.this, "User created", Toast.LENGTH_SHORT).show();
                        }
                        else {
                            try {
                                throw task.getException();
                            } catch(FirebaseAuthWeakPasswordException e) {
                                et_rgPassword.setError("Weak password");
                            } catch(FirebaseAuthUserCollisionException e) {
                                et_rgGmail.setError("User already exists");
                            } catch(Exception e) {
                                Log.e("FIREBASE", e.getMessage());
                            }
                        }
                    }
                });
            }
        });
    }

    @SuppressLint("ClickableViewAccessibility")
    private void imgbtn_View0Clicked() {
        imgbtn_View0.setOnTouchListener((v, event) -> {
            if (event.getAction() == MotionEvent.ACTION_UP) {
                imgbtn_View0.setImageResource(R.drawable.invisible_eye);
                et_rgPassword.setTransformationMethod(PasswordTransformationMethod.getInstance());
            }
            if (event.getAction() == MotionEvent.ACTION_DOWN) {
                imgbtn_View0.setImageResource(R.drawable.visible_eye);
                et_rgPassword.setTransformationMethod(HideReturnsTransformationMethod.getInstance());
            }
            return false;
        });
    }

    @SuppressLint("ClickableViewAccessibility")
    private void imgbtn_View1Clicked() {
        imgbtn_View1.setOnTouchListener((v, event) -> {
            if (event.getAction() == MotionEvent.ACTION_UP) {
                imgbtn_View1.setImageResource(R.drawable.invisible_eye);
                et_rgRetypePassword.setTransformationMethod(PasswordTransformationMethod.getInstance());
            }
            if (event.getAction() == MotionEvent.ACTION_DOWN) {
                imgbtn_View1.setImageResource(R.drawable.visible_eye);
                et_rgRetypePassword.setTransformationMethod(HideReturnsTransformationMethod.getInstance());
            }
            return false;
        });
    }
}