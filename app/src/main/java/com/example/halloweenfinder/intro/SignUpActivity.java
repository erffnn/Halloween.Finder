package com.example.halloweenfinder.intro;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.example.halloweenfinder.main.GuestHomeActivity;
import com.example.halloweenfinder.R;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import java.util.HashMap;
import java.util.Map;

public class SignUpActivity extends AppCompatActivity {

    private FirebaseAuth mAuth;
    private EditText emailEditText, passwordEditText, confirmPasswordEditText;
    private Button signUpButton, loginRedirectButton;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.intro_sign_up);

        // Initialize Firebase Auth
        mAuth = FirebaseAuth.getInstance();
        FirebaseAuth.getInstance().setLanguageCode("en");

        // Initialize UI elements
        emailEditText = findViewById(R.id.emailEditText);
        passwordEditText = findViewById(R.id.passwordEditText);
        confirmPasswordEditText = findViewById(R.id.confirmPasswordEditText);
        signUpButton = findViewById(R.id.signUpButton);
        loginRedirectButton = findViewById(R.id.loginRedirectButton);

        // Set sign-up button click listener
        signUpButton.setOnClickListener(v -> registerUser());

        // Redirect to login activity if the user already has an account
        loginRedirectButton.setOnClickListener(v -> {
            Intent intent = new Intent(SignUpActivity.this, LoginActivity.class);
            startActivity(intent);
            finish();
        });
    }

    private void saveUserToDatabase(FirebaseUser user) {
        String userId = user.getUid(); // Get the user's unique ID
        String email = user.getEmail(); // Get the user's email address

        // Extract the username from the email address (before the '@')
        String userName = "";
        if (email != null && email.contains("@")) {
            userName = email.split("@")[0]; // Split the email at '@' and take the first part
        }

        // Create default fields for the new user
        Map<String, Object> userData = new HashMap<>();
        userData.put("userId", userId);
        userData.put("email", email); // Add email to the user data
        userData.put("fileURL", ""); // Empty string for file URL
        userData.put("address", ""); // Empty address
        userData.put("name", userName); // Set the extracted name
        userData.put("age", ""); // Empty age
        userData.put("partiesAttending", new HashMap<>()); // Empty map
        userData.put("partiesHosting", new HashMap<>()); // Empty map
        userData.put("partyHistory", new HashMap<>()); // Empty map

        // Reference to the Realtime Database
        DatabaseReference databaseRef = FirebaseDatabase.getInstance().getReference("users");

        // Save user data
        databaseRef.child(userId).setValue(userData)
                .addOnCompleteListener(task -> {
                    if (task.isSuccessful()) {
                        Toast.makeText(this, "User data saved successfully!", Toast.LENGTH_SHORT).show();
                    } else {
                        Exception e = task.getException();
                        Toast.makeText(this, "Failed to save user data: " + (e != null ? e.getMessage() : "Unknown error"),
                                Toast.LENGTH_SHORT).show();
                        if (e != null) e.printStackTrace();
                    }
                });
    }



    private void registerUser() {
        String email = emailEditText.getText().toString().trim();
        String password = passwordEditText.getText().toString().trim();
        String confirmPassword = confirmPasswordEditText.getText().toString().trim();

        if (email.isEmpty() || password.isEmpty() || confirmPassword.isEmpty()) {
            Toast.makeText(this, "Please fill out all fields", Toast.LENGTH_SHORT).show();
            return;
        }

        if (!password.equals(confirmPassword)) {
            Toast.makeText(this, "Passwords do not match", Toast.LENGTH_SHORT).show();
            return;
        }

        mAuth.createUserWithEmailAndPassword(email, password)
                .addOnCompleteListener(this, task -> {
                    if (task.isSuccessful()) {
                        FirebaseUser user = mAuth.getCurrentUser();
                        if (user != null) {
                            saveUserToDatabase(user);
                            Intent intent = new Intent(SignUpActivity.this, GuestHomeActivity.class);
                            startActivity(intent);
                            finish();
                        }
                    } else {
                        Toast.makeText(this, "Registration failed: " + task.getException().getMessage(),
                                Toast.LENGTH_SHORT).show();
                    }
                });
    }
}
