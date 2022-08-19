package com.example.reminder;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.coordinatorlayout.widget.CoordinatorLayout;
import androidx.core.view.WindowCompat;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.LinearLayout;
import android.widget.RadioGroup;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.example.reminder.helpers.TextValidator;
import com.example.reminder.models.User;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.android.material.appbar.MaterialToolbar;
import com.google.android.material.datepicker.CalendarConstraints;
import com.google.android.material.datepicker.DateValidatorPointBackward;
import com.google.android.material.datepicker.MaterialDatePicker;
import com.google.android.material.textfield.TextInputLayout;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import java.time.Instant;
import java.time.LocalDate;
import java.time.ZoneId;
import java.time.format.DateTimeFormatter;

public class SignupActivity extends AppCompatActivity {
    private RelativeLayout progressCircular;
    private CoordinatorLayout coordinatorLayout;
    private LinearLayout contentLayout;

    private MaterialToolbar appBar;
    private TextInputLayout emailLayout;
    private TextInputLayout passwordLayout;
    private TextInputLayout confirmPasswordLayout;
    private TextInputLayout nameLayout;
    private TextInputLayout dobLayout;
    private TextView email;
    private TextView password;
    private TextView confirmPassword;
    private TextView name;
    private TextView dob;
    private RadioGroup genderGroup;
    private CheckBox policyBtn;
    private CheckBox promoBtn;
    private Button signupBtn;

    private long dobToLong;
    private DatabaseReference db;
    private FirebaseAuth auth;

    private User.Gender getCheckedGender() {
        int id = genderGroup.getCheckedRadioButtonId();
        if (id == R.id.signup_btnSelectFemale) {
            return User.Gender.Female;
        }
        return User.Gender.Male;
    }

    private boolean getPromoEnableState() {
        return promoBtn.isChecked();
    }

    @Override
    protected void onStart() {
        super.onStart();
        FirebaseUser currentUser = auth.getCurrentUser();
        if (currentUser != null) {
            Intent intent = new Intent(this, HomeActivity.class);
            startActivity(intent);
        }
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        WindowCompat.setDecorFitsSystemWindows(getWindow(), false);
        setContentView(R.layout.activity_signup);

        db = FirebaseDatabase.getInstance().getReference();
        auth = FirebaseAuth.getInstance();

        handleDataTransfer();
        getElements();
        setElementBehaviors();
    }

    private void handleDataTransfer() {
    }

    private void getElements() {
        progressCircular = findViewById(R.id.signup_progressCircular);
        coordinatorLayout = findViewById(R.id.singup_coordinatorLayout);
        contentLayout = findViewById(R.id.signup_contentLayout);

        appBar = findViewById(R.id.signup_txtAppbar);
        emailLayout = findViewById(R.id.signup_emailLayout);
        email = findViewById(R.id.signup_txtEmail);
        passwordLayout = findViewById(R.id.signup_passwordLayout);
        password = findViewById(R.id.signup_txtPassword);
        confirmPasswordLayout = findViewById(R.id.signup_pwdCfmLayout);
        confirmPassword = findViewById(R.id.signup_txtPwdCfm);
        nameLayout = findViewById(R.id.signup_nameLayout);
        name = findViewById(R.id.signup_txtName);
        dobLayout = findViewById(R.id.signup_dobLayout);
        dob = findViewById(R.id.signup_txtDob);
        genderGroup = findViewById(R.id.signup_genderGroup);
        policyBtn = findViewById(R.id.signup_btnPolicyAgree);
        promoBtn = findViewById(R.id.signup_btnPromoAgree);
        signupBtn = findViewById(R.id.signup_btnSignup);
    }

    private void setElementBehaviors() {
        appBar.setNavigationOnClickListener(view -> {
            Intent intent = new Intent(SignupActivity.this, LoginActivity.class);
            intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
            startActivity(intent);
            finish();
        });

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
                } else if (text.length() < 8) {
                    passwordLayout.setError("Password must be at least 8 characters");
                } else {
                    passwordLayout.setError(null);
                }
            }
        });

        confirmPassword.addTextChangedListener(new TextValidator(confirmPassword) {
            @Override
            public void validate(TextView textView, String text) {
                if (text.isEmpty()) {
                    confirmPasswordLayout.setError("Confirm password is required");
                } else if (!text.contentEquals(password.getText())) {
                    confirmPasswordLayout.setError("Password does not match");
                } else {
                    confirmPasswordLayout.setError(null);
                }
            }
        });

        name.addTextChangedListener(new TextValidator(name) {
            @Override
            public void validate(TextView textView, String text) {
                if (text.isEmpty()) {
                    nameLayout.setError("Name is required");
                } else if (!text.matches("^[a-zA-Z\\s]+")) {
                    nameLayout.setError("Invalid name format");
                } else {
                    nameLayout.setError(null);
                }
            }
        });

        dob.setFocusable(false);
        dob.setOnClickListener(view -> {
            MaterialDatePicker<Long> datePicker = MaterialDatePicker.Builder.datePicker()
                    .setInputMode(MaterialDatePicker.INPUT_MODE_CALENDAR)
                    .setTitleText("Select date")
                    .setSelection(MaterialDatePicker.todayInUtcMilliseconds())
                    .setCalendarConstraints(
                            new CalendarConstraints.Builder()
                                    .setValidator(DateValidatorPointBackward.now())
                                    .build()
                    )
                    .build();
            datePicker.show(getSupportFragmentManager(), "datePicker");
            datePicker.addOnPositiveButtonClickListener(selection -> {
                if (selection != null) {
                    LocalDate date = Instant.ofEpochMilli(selection)
                            .atZone(ZoneId.systemDefault())
                            .toLocalDate();
                    dob.setText(date.format(DateTimeFormatter.ofPattern("MM/dd/yy")));
                    dobToLong = selection;
                }
            });
        });


        dob.addTextChangedListener(new TextValidator(dob) {
            @Override
            public void validate(TextView textView, String text) {
                if (text.isEmpty()) {
                    dobLayout.setError("Birthday is required");
                } else {
                    dobLayout.setError(null);
                }
            }
        });

        signupBtn.setOnClickListener(view -> {
            if (invokeValidation()) {
                progressCircular.setVisibility(View.VISIBLE);

                User newUser = new User(
                        email.getText().toString(),
                        name.getText().toString(),
                        dobToLong,
                        getCheckedGender(),
                        getPromoEnableState()
                );

                db.child("users").push().setValue(newUser, (error, ref) -> {
                    if (error != null) {
                        progressCircular.setVisibility(View.GONE);
                        Toast.makeText(SignupActivity.this, "An error occurs when signing up, please try again later!", Toast.LENGTH_SHORT).show();
                    } else {
                        auth.createUserWithEmailAndPassword(newUser.getEmail(), password.getText().toString())
                                .addOnCompleteListener(this, new OnCompleteListener<AuthResult>() {
                                    @Override
                                    public void onComplete(@NonNull Task<AuthResult> task) {
                                        if (task.isSuccessful()) {
                                            Intent intent = new Intent(SignupActivity.this, HomeActivity.class);
                                            startActivity(intent);
                                            finish();
                                        } else {
                                            coordinatorLayout.setVisibility(View.VISIBLE);
                                            contentLayout.setVisibility(View.VISIBLE);
                                            progressCircular.setVisibility(View.GONE);
                                            db.child("users").child(ref.getKey()).setValue(null);
                                            Toast.makeText(SignupActivity.this, "An error occurs when signing up, please try again later!", Toast.LENGTH_SHORT).show();
                                        }
                                    }
                                });
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
        String confirmText = confirmPassword.getText().toString();
        confirmPassword.setText(confirmText + " ");
        confirmPassword.setText(confirmText);
        String nameText = name.getText().toString();
        name.setText(nameText + " ");
        name.setText(nameText);
        String dobText = dob.getText().toString();
        dob.setText(dobText + " ");
        dob.setText(dobText);
        return emailLayout.getError() == null
                && passwordLayout.getError() == null
                && confirmPasswordLayout.getError() == null
                && dobLayout.getError() == null
                && nameLayout.getError() == null;
    }

}