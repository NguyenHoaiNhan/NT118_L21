package com.example.HealthGO.startup_screen;

import android.annotation.SuppressLint;
import android.os.Bundle;
import android.text.method.PasswordTransformationMethod;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import com.example.HealthGO.R;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.FirebaseFirestore;

import java.util.HashMap;
import java.util.Map;

public class SignUpTabFragment extends Fragment {
    private EditText Email;
    private EditText Password;
    private EditText RetypePassword;
    private EditText PhoneNo;
    private EditText FullName;
    private Button SignUp;
    private FirebaseAuth mAuth;
    private FirebaseFirestore mStore;
    Map<String, Object> user = new HashMap<>();

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        ViewGroup root = (ViewGroup) inflater.inflate(R.layout.signup_tab_fragment, container, false);

        Email = root.findViewById(R.id.Email);
        Password = root.findViewById(R.id.Password);
        RetypePassword = root.findViewById(R.id.RetypePassword);
        PhoneNo = root.findViewById(R.id.PhoneNum);
        SignUp = root.findViewById(R.id.SignUp);
        FullName = root.findViewById(R.id.FullName);
        mAuth = FirebaseAuth.getInstance();
        mStore = FirebaseFirestore.getInstance();

        RegisterClicked();
        PasswordClicked();
        RetypePasswordClicked();
        return root;
    }

    private void PasswordClicked() {
        Password.setOnTouchListener(new View.OnTouchListener() {
            @Override
            @SuppressLint("ClickableViewAccessibility")
            public boolean onTouch(View v, MotionEvent event) {
                final int DRAWABLE_LEFT = 0;
                final int DRAWABLE_TOP = 1;
                final int DRAWABLE_RIGHT = 2;
                final int DRAWABLE_BOTTOM = 3;

                if (event.getAction() == MotionEvent.ACTION_DOWN) {
                    if (event.getRawX() >= (Password.getRight() - Password.getCompoundDrawables()[DRAWABLE_RIGHT].getBounds().width())) {
                        Password.setCompoundDrawablesWithIntrinsicBounds(R.drawable.lock, 0, R.drawable.visible, 0);
                        Password.setTransformationMethod(null);
                    }
                }
                if (event.getAction() == MotionEvent.ACTION_UP) {
                    if (event.getRawX() >= (Password.getRight() - Password.getCompoundDrawables()[DRAWABLE_RIGHT].getBounds().width())) {
                        Password.setCompoundDrawablesWithIntrinsicBounds(R.drawable.lock, 0, R.drawable.invisible, 0);
                        Password.setTransformationMethod(new PasswordTransformationMethod());
                    }
                }
                return false;
            }
        });
    }
    private  void RetypePasswordClicked() {
        RetypePassword.setOnTouchListener(new View.OnTouchListener() {
            @Override
            @SuppressLint("ClickableViewAccessibility")
            public boolean onTouch(View v, MotionEvent event) {
                final int DRAWABLE_LEFT = 0;
                final int DRAWABLE_TOP = 1;
                final int DRAWABLE_RIGHT = 2;
                final int DRAWABLE_BOTTOM = 3;

                if (event.getAction() == MotionEvent.ACTION_DOWN) {
                    if (event.getRawX() >= (RetypePassword.getRight() - RetypePassword.getCompoundDrawables()[DRAWABLE_RIGHT].getBounds().width())) {
                        RetypePassword.setCompoundDrawablesWithIntrinsicBounds(R.drawable.lock, 0, R.drawable.visible, 0);
                        RetypePassword.setTransformationMethod(null);
                    }
                }
                if (event.getAction() == MotionEvent.ACTION_UP) {
                    if (event.getRawX() >= (RetypePassword.getRight() - RetypePassword.getCompoundDrawables()[DRAWABLE_RIGHT].getBounds().width())) {
                        RetypePassword.setCompoundDrawablesWithIntrinsicBounds(R.drawable.lock, 0, R.drawable.invisible, 0);
                        RetypePassword.setTransformationMethod(new PasswordTransformationMethod());
                    }
                }
                return false;
            }
        });
    }

    private void RegisterClicked() {
        SignUp.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (isValid()) {
                    mAuth.createUserWithEmailAndPassword(Email.getText().toString(), Password.getText().toString()).addOnCompleteListener(new OnCompleteListener<AuthResult>() {
                        @Override
                        public void onComplete(@NonNull Task<AuthResult> task) {
                            if (task.isSuccessful()) {
                                AddUserToStore();
                                Toast.makeText(v.getContext(), "User created", Toast.LENGTH_SHORT).show();
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

    private boolean isValid() {
        boolean res = true;
        String email = Email.getText().toString();
        String pass = Password.getText().toString();
        String retypePass = RetypePassword.getText().toString();
        String phone = PhoneNo.getText().toString();

        if (email.isEmpty()) {
            Email.setError("Email is required");
            res = false;
        }
        if (pass.isEmpty()) {
            Password.setError("Password is required");
            res = false;
        }
        if (retypePass.isEmpty()) {
            RetypePassword.setError("Password is required");
            res = false;
        }
        if (phone.isEmpty()) {
            PhoneNo.setError("Password is required");
            res = false;
        }
        if (pass.length() < 8) {
            Password.setError("Password not strong enough");
            res = false;
        }
        if (!pass.equals(retypePass)) {
            Password.setError("Passwords didn't match");
            RetypePassword.setError("Passwords didn't match");
            res = false;
        }
        if (phone.length() != 10) {
            PhoneNo.setError("Phone need to be 10 characters");
            res = false;
        }
        return res;
    }

    private void AddUserToStore() {
        user.put("Email", Email.getText().toString());
        user.put("ID", mAuth.getCurrentUser().getUid());
        user.put("Name", FullName.getText().toString());
        user.put("PhoneNo", PhoneNo.getText().toString());

        mStore.collection("user").document(mAuth.getCurrentUser().getUid()).set(user);
    }
}
