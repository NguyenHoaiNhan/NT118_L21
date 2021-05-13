package com.kane.healthapp;

import android.content.Context;
import android.widget.EditText;
import android.widget.Toast;

import androidx.annotation.NonNull;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseAuthInvalidCredentialsException;
import com.google.firebase.auth.FirebaseAuthUserCollisionException;
import com.google.firebase.auth.FirebaseAuthWeakPasswordException;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.FirebaseFirestore;

import java.util.HashMap;
import java.util.Map;
import java.util.Objects;

public class UserHandler {
    private Map<String, String> user = new HashMap<>();
    private final String[] UserAttribute = {"Phone Number", "Email"};
    private Context context;

    public static FirebaseAuth fb_Auth = FirebaseAuth.getInstance();
    public static FirebaseFirestore fb_fireStore = FirebaseFirestore.getInstance();

    public UserHandler(){}
    public UserHandler(Context context) {
        this.context = context;
    }

    void createUser(String Email, String Password) {
        fb_Auth.createUserWithEmailAndPassword(Email, Password);
    }

    void addUserAttribute(String[] Values) {
        for (int i = 0; i < UserAttribute.length; i++) {
            user.put(UserAttribute[i], Values[i]);
        }
        fb_fireStore.collection("users").add(user)
            .addOnCompleteListener(task -> {

            })
            .addOnFailureListener(e -> {

            });
    }

}
