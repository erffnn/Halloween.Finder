package com.example.halloweenfinder.shared_fragments;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
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

public class HostProfileFragment extends Fragment {

    private ImageView imgProfile;
    private TextView textEmail;
    private EditText editTextName, editTextAge;
    private Button buttonCancel, buttonSave;

    private DatabaseReference databaseReference;
    private FirebaseUser currentUser;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.host_host_profile_fr, container, false);

        // Initialize views
        imgProfile = view.findViewById(R.id.img_profile);
        textEmail = view.findViewById(R.id.textEmail);
        editTextName = view.findViewById(R.id.editTextName);
        editTextAge = view.findViewById(R.id.editTextAge);
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
                        String name = snapshot.child("name").getValue(String.class);
                        String email = currentUser.getEmail();
                        long age = snapshot.child("age").getValue(Long.class); // Assuming age is stored as a number

                        editTextName.setText(name != null ? name : "");
                        editTextAge.setText(String.valueOf(age));
                        textEmail.setText(email != null ? email : "No email available");
                    } else {
                        Toast.makeText(getActivity(), "User data not found", Toast.LENGTH_SHORT).show();
                    }
                }

                @Override
                public void onCancelled(@NonNull DatabaseError error) {
                    Toast.makeText(getActivity(), "Error fetching user data: " + error.getMessage(), Toast.LENGTH_SHORT).show();
                }
            });
        } else {
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

        if (name.isEmpty() || ageStr.isEmpty()) {
            Toast.makeText(getActivity(), "Please fill in all fields", Toast.LENGTH_SHORT).show();
            return;
        }

        try {
            int age = Integer.parseInt(ageStr);

            if (currentUser != null) {
                String userId = currentUser.getUid();
                DatabaseReference userRef = databaseReference.child(userId);

                // Save the changes
                userRef.child("name").setValue(name);
                userRef.child("age").setValue(age);

                Toast.makeText(getActivity(), "Changes saved successfully", Toast.LENGTH_SHORT).show();
            } else {
                Toast.makeText(getActivity(), "User not authenticated", Toast.LENGTH_SHORT).show();
            }

        } catch (NumberFormatException e) {
            Toast.makeText(getActivity(), "Invalid age input", Toast.LENGTH_SHORT).show();
        }
    }
}
