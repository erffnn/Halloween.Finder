package com.example.halloweenfinder.guest_fragments;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.halloweenfinder.R;
import com.example.halloweenfinder.adapters.PartyAdapter;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.Map;

import models.Party;

public class PartyListFragment extends Fragment {

    private RecyclerView partyRecyclerView;
    private PartyAdapter partyAdapter;
    private ArrayList<Party> partyList;
    private DatabaseReference userDatabaseRef;
    private String currentUserId;

    public PartyListFragment() {
        // Required empty public constructor
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.guest_party_list_fr, container, false);

        // Initialize RecyclerView
        partyRecyclerView = view.findViewById(R.id.partyRecyclerView);
        partyRecyclerView.setLayoutManager(new LinearLayoutManager(getContext()));

        // Get current user ID
        currentUserId = FirebaseAuth.getInstance().getCurrentUser().getUid();

        // Initialize Firebase Database Reference for current user

        userDatabaseRef = FirebaseDatabase.getInstance().getReference("Parties");

        // Initialize party list and adapter
        partyList = new ArrayList<>();
        partyAdapter = new PartyAdapter(partyList);
        partyRecyclerView.setAdapter(partyAdapter);

        // Fetch attending parties
        fetchAttendingParties();

        return view;
    }

    private void fetchAttendingParties() {

        userDatabaseRef.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                if (!snapshot.exists()) {
                    // Show a message if attendingParties does not exist
                    Toast.makeText(getContext(), "You don't have any attending parties.", Toast.LENGTH_SHORT).show();
                } else {
                    // Fetch attending parties
                    partyList.clear();
                    for (DataSnapshot partySnapshot : snapshot.getChildren()) {
                        String partyId = partySnapshot.getKey();
                        Map<String, String> partyData = (Map<String, String>) partySnapshot.getValue();

                        if (partyData != null) {
                            Party party = new Party();
                            party.setPartyId(partyId);
                            party.setAddress(partyData.get("address"));
                            party.setHostName(partyData.get("hostName"));
                            party.setTime(partyData.get("time"));
                            party.setDescription(partyData.get("description"));
                            partyList.add(party);
                        }
                    }
                    partyAdapter.notifyDataSetChanged();
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {
                Toast.makeText(getContext(), "Failed to load attending parties: " + error.getMessage(), Toast.LENGTH_SHORT).show();
            }
        });
    }
}
