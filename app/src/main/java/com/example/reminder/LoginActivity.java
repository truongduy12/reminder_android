package com.example.reminder;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.view.WindowCompat;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.example.reminder.helpers.TextValidator;
import com.example.reminder.models.User;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.android.material.textfield.TextInputLayout;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;

public class LoginActivity extends AppCompatActivity {
    private RelativeLayout progressCircular;
    private TextView email;
    private TextInputLayout emailLayout;
    private TextView password;
    private TextInputLayout passwordLayout;
    private Button loginBtn;
    private Button signupBtn;

    private DatabaseReference db;
    private FirebaseAuth auth;

    @Override
    protected void onStart() {
        super.onStart();
        FirebaseUser currentUser = auth.getCurrentUser();
        if (currentUser != null) {
            Intent intent = new Intent(LoginActivity.this, HomeActivity.class);
            startActivity(intent);
            finish();
        }
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        WindowCompat.setDecorFitsSystemWindows(getWindow(), false);
        setContentView(R.layout.activity_login);

        db = FirebaseDatabase.getInstance().getReference();
        auth = FirebaseAuth.getInstance();

        handleDataTransfer();
        getElements();
        setElementBehaviors();
    }

    private void handleDataTransfer() {

    }

    private void getElements() {
        progressCircular = findViewById(R.id.login_progressCircular);
        email = findViewById(R.id.login_txtEmail);
        password = findViewById(R.id.login_txtPassword);
        emailLayout = findViewById(R.id.login_emailLayout);
        passwordLayout = findViewById(R.id.login_passwordLayout);
        loginBtn = findViewById(R.id.login_btnLogin);
        signupBtn = findViewById(R.id.login_btnSignup);
    }

    private void setElementBehaviors() {
        email.addTextChangedListener(new TextValidator(email) {
            @Override
            public void validate(TextView textView, String text) {
                if (text.isEmpty()) {
                    emailLayout.setError("Email is required");
                } else if (!text.matches("^[a-zA-Z0-9_!#$%&'*+/=?`{|}~^.-]+@[a-zA-Z0-9.-]+$")) {
                    emailLayout.setError("Email is invalid");
                } else {
                    emailLayout.setError(null);
                }
            }
        });

        password.addTextChangedListener(new TextValidator(password) {
            @Override
            public void validate(TextView textView, String text) {
                if (text.isEmpty()) {
                    passwordLayout.setError("Password is required");
//                } else if (!text.matches("^(?=.*\\d)(?=.*[a-z])(?=.*[A-Z])(?=.*[a-zA-Z]).{8,}$")) {
//                    password_layout.setError("Weak password format");
                } else {
                    passwordLayout.setError(null);
                }
            }
        });

        signupBtn.setOnClickListener(view -> {
            Intent intent = new Intent(LoginActivity.this, SignupActivity.class);
            startActivity(intent);
            finish();
        });

        loginBtn.setOnClickListener(view -> {
            email.clearFocus();
            password.clearFocus();
            if (invokeValidation()) {
                progressCircular.setVisibility(View.VISIBLE);
                auth.signInWithEmailAndPassword(email.getText().toString(), password.getText().toString()).addOnCompleteListener(this, task -> {
                    if (task.isSuccessful()) {
                        Intent intent = new Intent(this, HomeActivity.class);
                        startActivity(intent);
                        finish();
                    } else {
                        progressCircular.setVisibility(View.GONE);
                        Toast.makeText(this, "Authentication failed", Toast.LENGTH_SHORT).show();
                    }
                });
            }
        });
    }

    private boolean invokeValidation() {
        String emailText = email.getText().toString();
        email.setText(emailText + " ");
        email.setText(emailText);
        String passwordText = password.getText().toString();
        password.setText(passwordText + " ");
        password.setText(passwordText);
        return emailLayout.getError() == null && passwordLayout.getError() == null;
    }

    //        btnGet.setOnClickListener(view -> {
    // Get one
//            db.child("users").child("-N9lbdLwJyqYLJlZ7cAr").get().addOnCompleteListener(new OnCompleteListener<DataSnapshot>() {
//                @Override
//                public void onComplete(@NonNull Task<DataSnapshot> task) {
//                    if (!task.isSuccessful()) {
//                        Toast.makeText(LoginActivity.this, task.getException().getMessage(), Toast.LENGTH_SHORT).show();
//                    } else {
//                        Toast.makeText(LoginActivity.this, String.valueOf(task.getResult().getValue()), Toast.LENGTH_SHORT).show();
//                    }
//                }
//            });
    // Get the created Id from signup
//            Bundle extras = getIntent().getExtras();
//            String id = extras.getString("id");
//            Toast.makeText(this, id, Toast.LENGTH_SHORT).show();

    // Get all users
//            ArrayList<User> userArrayList = new ArrayList<>();
//            db.child("users").addValueEventListener(new ValueEventListener() {
//                @Override
//                public void onDataChange(@NonNull DataSnapshot snapshot) {
//                    for (DataSnapshot singleUser : snapshot.getChildren()) {
//                        userArrayList.add(singleUser.getValue(User.class));
//                    }
//                    for (int i = 0; i < userArrayList.size(); i++) {
//                        Log.w("", userArrayList.get(i).getEmail());
//                    }
//                }
//
//                @Override
//                public void onCancelled(@NonNull DatabaseError error) {
//
//                }
//            });
//        });

}