package com.example.halloweenfinder.shared_fragments;

import android.annotation.SuppressLint;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import com.example.halloweenfinder.R;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

public class ProfileFragment extends Fragment {

    private static final String TAG = "ProfileFragment";

    private TextView textEmail;
    private EditText editTextName, editTextAge, editTextAddress;
    private Button buttonCancel, buttonSave;

    private DatabaseReference databaseReference;
    private FirebaseUser currentUser;

    @SuppressLint("MissingInflatedId")
    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.shared_profile_fr, container, false);

        // Initialize views
        textEmail = view.findViewById(R.id.textEmail); // Corrected ID
        editTextName = view.findViewById(R.id.editTextName);
        editTextAge = view.findViewById(R.id.editTextAge);
        editTextAddress = view.findViewById(R.id.editTextAddress);
        buttonCancel = view.findViewById(R.id.buttonCancel);
        buttonSave = view.findViewById(R.id.buttonSave);

        // Firebase setup
        currentUser = FirebaseAuth.getInstance().getCurrentUser();
        databaseReference = FirebaseDatabase.getInstance().getReference("users");

        // Fetch user data
        fetchUserData();

        // Set button listeners
        buttonCancel.setOnClickListener(v -> cancelChanges());
        buttonSave.setOnClickListener(v -> saveChanges());

        return view;
    }

    private void fetchUserData() {
        if (currentUser != null) {
            String userId = currentUser.getUid();

            databaseReference.child(userId).addListenerForSingleValueEvent(new ValueEventListener() {
                @Override
                public void onDataChange(@NonNull DataSnapshot snapshot) {
                    if (snapshot.exists()) {
                        Log.d(TAG, "Snapshot data: " + snapshot.getValue());

                        // Fetch values from the database
                        String name = snapshot.child("name").getValue(String.class);
                        String email = snapshot.child("email").getValue(String.class);
                        // Handle 'age' as a Long, but display as a String in the UI
                        Long ageLong = snapshot.child("age").getValue(Long.class);
                        String address = snapshot.child("address").getValue(String.class);

                        // Set default values if data is missing
                        name = (name != null && !name.isEmpty()) ? name : "To be filled";
                        email = (email != null && !email.isEmpty()) ? email : "To be filled";
                        address = (address != null && !address.isEmpty()) ? address : "To be filled";
                        String age = (ageLong != null) ? String.valueOf(ageLong) : "To be filled";

                        // Set the fetched data to UI elements
                        editTextName.setText(name);
                        editTextAge.setText(age); // Age as string
                        editTextAddress.setText(address);
                        textEmail.setText(email);
                    } else {
                        Log.e(TAG, "Snapshot does not exist.");
                        Toast.makeText(getActivity(), "User data not found", Toast.LENGTH_SHORT).show();
                    }
                }

                @Override
                public void onCancelled(@NonNull DatabaseError error) {
                    Log.e(TAG, "Database error: " + error.getMessage());
                    Toast.makeText(getActivity(), "Error fetching user data: " + error.getMessage(), Toast.LENGTH_SHORT).show();
                }
            });
        } else {
            Log.e(TAG, "Current user is null.");
            Toast.makeText(getActivity(), "User not authenticated", Toast.LENGTH_SHORT).show();
        }
    }

    private void cancelChanges() {
        fetchUserData(); // Reload user data to discard changes
        Toast.makeText(getActivity(), "Changes canceled", Toast.LENGTH_SHORT).show();
    }

    private void saveChanges() {
        String name = editTextName.getText().toString().trim();
        String ageStr = editTextAge.getText().toString().trim();
        String address = editTextAddress.getText().toString().trim();

        if (name.isEmpty() || ageStr.isEmpty() || address.isEmpty()) {
            Toast.makeText(getActivity(), "Please fill in all fields", Toast.LENGTH_SHORT).show();
            return;
        }

        try {
            Long age = Long.parseLong(ageStr); // Validate age as a Long

            if (currentUser != null) {
                String userId = currentUser.getUid();
                DatabaseReference userRef = databaseReference.child(userId);

                // Save the changes to the database
                userRef.child("name").setValue(name);
                userRef.child("age").setValue(age); // Store age as Long
                userRef.child("address").setValue(address);

                Toast.makeText(getActivity(), "Changes saved successfully", Toast.LENGTH_SHORT).show();
            } else {
                Toast.makeText(getActivity(), "User not authenticated", Toast.LENGTH_SHORT).show();
            }

        } catch (NumberFormatException e) {
            Toast.makeText(getActivity(), "Invalid age input", Toast.LENGTH_SHORT).show();
        }
    }
}
