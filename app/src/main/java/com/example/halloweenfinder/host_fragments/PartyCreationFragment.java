package com.example.halloweenfinder.host_fragments;

import android.os.Bundle;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;

import com.example.halloweenfinder.R;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.android.libraries.places.api.Places;
import com.google.android.libraries.places.api.model.Place;
import com.google.android.libraries.places.widget.AutocompleteSupportFragment;
import com.google.android.libraries.places.widget.listener.PlaceSelectionListener;

import java.util.Arrays;
import java.util.HashMap;

public class PartyCreationFragment extends Fragment {

    private EditText editPartyName, editPartyDate, editPartyDescription;
    private Button btnCreateParty;
    private String selectedLocation = ""; // Store selected location
    private DatabaseReference databaseReference;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.host_party_creation_fr, container, false);

        databaseReference = FirebaseDatabase.getInstance().getReference("Parties");

        editPartyName = view.findViewById(R.id.edt_party_name);
        editPartyDate = view.findViewById(R.id.edt_party_date);
        editPartyDescription = view.findViewById(R.id.edt_party_description);
        btnCreateParty = view.findViewById(R.id.btn_create_party);

        if (!Places.isInitialized()) {
            Places.initialize(requireContext(), getString(R.string.my_map_api_key));
        }

        AutocompleteSupportFragment autocompleteFragment = (AutocompleteSupportFragment)
                getChildFragmentManager().findFragmentById(R.id.autocomplete_fragment);

        if (autocompleteFragment != null) {
            autocompleteFragment.setPlaceFields(Arrays.asList(Place.Field.ID, Place.Field.NAME, Place.Field.LAT_LNG));
            autocompleteFragment.setOnPlaceSelectedListener(new PlaceSelectionListener() {
                @Override
                public void onPlaceSelected(@NonNull Place place) {
                    selectedLocation = place.getName();
                }

                @Override
                public void onError(@NonNull com.google.android.gms.common.api.Status status) {
                    Toast.makeText(getContext(), "Error selecting location: " + status.getStatusMessage(), Toast.LENGTH_SHORT).show();
                }
            });
        }

        btnCreateParty.setOnClickListener(v -> createParty());

        return view;
    }

    private void createParty() {
        String partyName = editPartyName.getText().toString().trim();
        String partyDate = editPartyDate.getText().toString().trim();
        String partyDescription = editPartyDescription.getText().toString().trim();

        if (TextUtils.isEmpty(partyName)) {
            Toast.makeText(getContext(), "Party name is required", Toast.LENGTH_SHORT).show();
            return;
        }

        if (TextUtils.isEmpty(partyDate)) {
            Toast.makeText(getContext(), "Party date is required", Toast.LENGTH_SHORT).show();
            return;
        }

        if (TextUtils.isEmpty(partyDescription)) {
            Toast.makeText(getContext(), "Party description is required", Toast.LENGTH_SHORT).show();
            return;
        }

        if (TextUtils.isEmpty(selectedLocation)) {
            Toast.makeText(getContext(), "Party location is required", Toast.LENGTH_SHORT).show();
            return;
        }

        String partyId = databaseReference.push().getKey();

        HashMap<String, String> partyData = new HashMap<>();
        partyData.put("partyId", partyId);
        partyData.put("partyName", partyName);
        partyData.put("partyDate", partyDate);
        partyData.put("partyDescription", partyDescription);
        partyData.put("partyLocation", selectedLocation);

        assert partyId != null;
        databaseReference.child(partyId).setValue(partyData).addOnCompleteListener(task -> {
            if (task.isSuccessful()) {
                Toast.makeText(getContext(), "Party created successfully!", Toast.LENGTH_SHORT).show();

                editPartyName.setText("");
                editPartyDate.setText("");
                editPartyDescription.setText("");
                selectedLocation = "";
            } else {
                Toast.makeText(getContext(), "Failed to create party. Try again.", Toast.LENGTH_SHORT).show();
            }
        });
    }
}
