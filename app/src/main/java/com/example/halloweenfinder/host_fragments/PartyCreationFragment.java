package com.example.halloweenfinder.host_fragments;

import android.os.Bundle;

import androidx.fragment.app.Fragment;

import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.example.halloweenfinder.R;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import java.util.HashMap;

public class PartyCreationFragment extends Fragment {

    private EditText editPartyName, editPartyDate, editPartyDescription;
    private Button btnCreateParty;

    private DatabaseReference databaseReference;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment

        View view = inflater.inflate(R.layout.host_party_creation_fr, container, false);

        databaseReference = FirebaseDatabase.getInstance().getReference("parties");

        editPartyName = view.findViewById((R.id.edt_party_name));
        editPartyDate = view.findViewById((R.id.edt_party_date));
        editPartyDescription = view.findViewById((R.id.edt_party_description));
        btnCreateParty = view.findViewById((R.id.btn_create_party));


        btnCreateParty.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                CreateParty();
            }
        });

        return view;

    }

    private void CreateParty() {
        String partyName = editPartyName.getText().toString().trim();
        String partyDate = editPartyDate.getText().toString().trim();
        String partyDescription = editPartyDescription.getText().toString().trim();

        if(TextUtils.isEmpty(partyName)){
            Toast.makeText(getContext(), "Party name is required", Toast.LENGTH_SHORT).show();
            return;
        }


        if(TextUtils.isEmpty(partyDate)){
            Toast.makeText(getContext(), "Party date is required", Toast.LENGTH_SHORT).show();
            return;
        }

        if(TextUtils.isEmpty(partyDescription)){
            Toast.makeText(getContext(), "Party description is required", Toast.LENGTH_SHORT).show();
            return;
        }

        String partyId = databaseReference.push().getKey();

        HashMap<String, String> partyData = new HashMap<>();
        partyData.put("partyId", partyId);
        partyData.put("partyName", partyName);
        partyData.put("partyDescription", partyDescription);
        partyData.put("partyDate", partyDate);

        databaseReference.child(partyId).setValue(partyData).addOnCompleteListener( task -> {
            if(task.isSuccessful()){
                Toast.makeText(getContext(), "party created successfully!", Toast.LENGTH_SHORT).show();

                editPartyName.setText("");
                editPartyDate.setText("");
                editPartyDescription.setText("");
            } else {
                Toast.makeText(getContext(), "Failed to create party. Please try again.", Toast.LENGTH_SHORT).show();
            }
        });
    }

    public PartyCreationFragment() {
        // Required empty public constructor
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

    }

}