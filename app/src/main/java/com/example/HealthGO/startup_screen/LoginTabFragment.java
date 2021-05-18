package com.example.HealthGO.startup_screen;

import android.annotation.SuppressLint;
import android.app.Dialog;
import android.content.Intent;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.text.method.PasswordTransformationMethod;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.constraintlayout.widget.ConstraintLayout;
import androidx.fragment.app.Fragment;

import com.example.HealthGO.R;
import com.example.HealthGO.Test;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;

public class LoginTabFragment extends Fragment {
    private EditText email;
    private EditText password;
    private TextView forgotPassword;
    EditText rsEmail;
    Button Reset;
    private Button Login;
    private float v = 0;
    private FirebaseAuth mAuth;
    Dialog mDialog;

    @Nullable
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        ViewGroup root = (ViewGroup) inflater.inflate(R.layout.login_tab_fragment, container, false);

        email = root.findViewById(R.id.Email);
        password = root.findViewById(R.id.Password);
        forgotPassword = root.findViewById(R.id.forgot_password);
        Login = root.findViewById(R.id.Login);
        mAuth = FirebaseAuth.getInstance();

        animation();
        LoginClicked();
        PasswordClicked();

        forgotPassword.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mDialog = new Dialog(v.getContext());
                ShowPopUp(v);
            }
        });

        return root;
    }

    private void ShowPopUp(View v) {
        mDialog.setContentView(R.layout.forgotpassword_tab_fragment);
        ConstraintLayout constraintLayout = mDialog.findViewById(R.id.fg_constraintLayout);
        constraintLayout.setTranslationX(800);
        constraintLayout.setAlpha(0);
        constraintLayout.animate().translationX(0).alpha(1).setDuration(600).setStartDelay(200).start();

        mDialog.getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
        mDialog.show();
        Reset = mDialog.findViewById(R.id.Reset);
        rsEmail = mDialog.findViewById(R.id.ResetEmail);

        Reset.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (rsEmail.getText().toString().isEmpty()) {
                    rsEmail.setError("Email is required");
                }
                else {
                    mAuth.sendPasswordResetEmail(rsEmail.getText().toString()).addOnCompleteListener(new OnCompleteListener<Void>() {
                        @Override
                        public void onComplete(@NonNull Task<Void> task) {
                            if (task.isSuccessful()) {
                                Toast.makeText(v.getContext(), "Email Send", Toast.LENGTH_SHORT).show();
                            }
                            else {
                                Toast.makeText(v.getContext(), task.getException().getMessage(), Toast.LENGTH_SHORT).show();
                            }
                        }
                    });
                }
            }
        });
    }

    private void LoginClicked() {
        Login.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (isValid()) {
                    mAuth.signInWithEmailAndPassword(email.getText().toString(), password.getText().toString()).addOnCompleteListener(new OnCompleteListener<AuthResult>() {
                        @Override
                        public void onComplete(@NonNull Task<AuthResult> task) {
                            if (task.isSuccessful()) {
                                Toast.makeText(v.getContext(), "User login", Toast.LENGTH_SHORT).show();
                                startActivity(new Intent(v.getContext(), Test.class));
                            }
                            else {
                                Toast.makeText(v.getContext(), task.getException().getMessage(), Toast.LENGTH_SHORT).show();
                            }
                        }
                    });
                }
            }
        });
    }

    private void PasswordClicked() {
        password.setOnTouchListener(new View.OnTouchListener() {
            @Override
            @SuppressLint("ClickableViewAccessibility")
            public boolean onTouch(View v, MotionEvent event) {
                final int DRAWABLE_LEFT = 0;
                final int DRAWABLE_TOP = 1;
                final int DRAWABLE_RIGHT = 2;
                final int DRAWABLE_BOTTOM = 3;

                if (event.getAction() == MotionEvent.ACTION_DOWN) {
                    if (event.getRawX() >= (password.getRight() - password.getCompoundDrawables()[DRAWABLE_RIGHT].getBounds().width())) {
                        password.setCompoundDrawablesWithIntrinsicBounds(R.drawable.ic_lock, 0, R.drawable.ic_visible, 0);
                        password.setTransformationMethod(null);
                    }
                }
                if (event.getAction() == MotionEvent.ACTION_UP) {
                    if (event.getRawX() >= (password.getRight() - password.getCompoundDrawables()[DRAWABLE_RIGHT].getBounds().width())) {
                        password.setCompoundDrawablesWithIntrinsicBounds(R.drawable.ic_lock, 0, R.drawable.ic_invisible, 0);
                        password.setTransformationMethod(new PasswordTransformationMethod());
                    }
                }
                return false;
            }
        });
    }

    private boolean isValid() {
        boolean res = true;
        String Email = email.getText().toString();
        String Pass = password.getText().toString();

        if (Email.isEmpty()) {
            email.setError("Email is required");
            res = false;
        }
        if (Pass.isEmpty()) {
            password.setError("Password is required");
            res = false;
        }

        return res;
    }

    private void animation() {
        email.setTranslationX(800);
        password.setTranslationX(800);
        forgotPassword.setTranslationX(800);
        Login.setTranslationX(800);

        email.setAlpha(v);
        password.setAlpha(v);
        forgotPassword.setAlpha(v);
        Login.setAlpha(v);

        email.animate().translationX(0).alpha(1).setDuration(800).setStartDelay(300).start();
        password.animate().translationX(0).alpha(1).setDuration(800).setStartDelay(500).start();
        forgotPassword.animate().translationX(0).alpha(1).setDuration(800).setStartDelay(500).start();
        Login.animate().translationX(0).alpha(1).setDuration(800).setStartDelay(700).start();
    }
}
