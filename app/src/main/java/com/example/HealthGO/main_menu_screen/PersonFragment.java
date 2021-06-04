package com.example.HealthGO.main_menu_screen;

import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.widget.SwitchCompat;
import androidx.fragment.app.Fragment;

import com.example.HealthGO.MainActivity;
import com.example.HealthGO.R;
import com.google.android.gms.auth.api.signin.GoogleSignIn;
import com.google.android.gms.auth.api.signin.GoogleSignInClient;
import com.google.android.gms.auth.api.signin.GoogleSignInOptions;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.firestore.CollectionReference;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;

import java.util.Objects;


public class PersonFragment extends Fragment {

    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    private String mParam1;
    private String mParam2;

    private ImageView Logout;
    private FirebaseAuth mAuth;
    private GoogleSignInClient mGoogleSignInClient;
    private TextView About, EditProfile, uEmail, uName, Report;
    private SwitchCompat Notify;
    private FirebaseFirestore mStore;

    public PersonFragment() {
        // Required empty public constructor
    }

    public static PersonFragment newInstance(String param1, String param2) {
        PersonFragment fragment = new PersonFragment();
        Bundle args = new Bundle();
        args.putString(ARG_PARAM1, param1);
        args.putString(ARG_PARAM2, param2);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
            mParam1 = getArguments().getString(ARG_PARAM1);
            mParam2 = getArguments().getString(ARG_PARAM2);
        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_person, container, false);

        init(view);
        animation();
        SignOut(view);

        Report.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getActivity(), ReportActivity.class);
                getActivity().startActivity(intent);
            }
        });

        return view;
    }

    private void init(View view) {
        Logout = view.findViewById(R.id.Logout);
        About = view.findViewById(R.id.About);
        EditProfile = view.findViewById(R.id.EditProfile);
        Notify = view.findViewById(R.id.Notify);
        uEmail = view.findViewById(R.id.UserGmail);
        uName = view.findViewById(R.id.UserName);
        Report = view.findViewById(R.id.Report);

        mStore = FirebaseFirestore.getInstance();

        DocumentReference docRef = mStore.collection("user").document(Objects.requireNonNull(FirebaseAuth.getInstance().getCurrentUser()).getUid());
        docRef.get().addOnSuccessListener(documentSnapshot -> {
            uEmail.setText(documentSnapshot.getString("Email"));
            uName.setText(documentSnapshot.getString("Name"));
        }).addOnFailureListener(e -> Toast.makeText(view.getContext(), e.getMessage(), Toast.LENGTH_SHORT).show());
    }

    private void animation() {
        Logout.setTranslationX(300);
        EditProfile.setTranslationY(800);
        Notify.setTranslationY(800);
        Report.setTranslationY(800);
        About.setTranslationY(800);


        Logout.setAlpha(0);
        EditProfile.setAlpha(0);
        Notify.setAlpha(0);
        Report.setAlpha(0);
        About.setAlpha(0);

        Logout.animate().translationX(0).alpha(1).setDuration(600).setStartDelay(300).start();
        EditProfile.animate().translationY(0).alpha(1).setDuration(600).setStartDelay(300).start();
        Notify.animate().translationY(0).alpha(1).setDuration(600).setStartDelay(300).start();
        Report.animate().translationY(0).alpha(1).setDuration(600).setStartDelay(400).start();
        About.animate().translationY(0).alpha(1).setDuration(600).setStartDelay(500).start();
    }

    private void SignOut(View view) {
        GoogleCreateRequest();
        mAuth = FirebaseAuth.getInstance();
        Logout.setOnClickListener(v -> {
            mAuth.signOut();
            mGoogleSignInClient.revokeAccess();
            mGoogleSignInClient.signOut();
            startActivity(new Intent(v.getContext(), MainActivity.class));
        });
    }

    private void GoogleCreateRequest() {
        GoogleSignInOptions gso = new GoogleSignInOptions.Builder(GoogleSignInOptions.DEFAULT_SIGN_IN)
                .requestIdToken(getString(R.string.default_web_client_id))
                .requestEmail()
                .build();

        mGoogleSignInClient = GoogleSignIn.getClient(Objects.requireNonNull(getActivity()).getApplicationContext(), gso);
    }

}