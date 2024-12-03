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
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;

import models.Party;

public class PartyListFragment extends Fragment {

    private RecyclerView partyRecyclerView;
    private PartyAdapter partyAdapter;
    private ArrayList<Party> partyList;
    private DatabaseReference partiesDatabaseRef;

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

        // Initialize Firebase Database Reference for Parties
        partiesDatabaseRef = FirebaseDatabase.getInstance().getReference("Parties");

        // Initialize party list and adapter
        partyList = new ArrayList<>();
        partyAdapter = new PartyAdapter(partyList);
        partyRecyclerView.setAdapter(partyAdapter);

        // Fetch parties from Firebase
        fetchParties();

        return view;
    }

    private void fetchParties() {
        partiesDatabaseRef.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                partyList.clear(); // Clear the list before adding new data
                for (DataSnapshot partySnapshot : snapshot.getChildren()) {
                    Party party = new Party();

                    // Fetch party details from snapshot
                    party.setPartyId(partySnapshot.child("partyId").getValue(String.class));
                    party.setName(partySnapshot.child("partyName").getValue(String.class));
                    party.setDescription(partySnapshot.child("partyDescription").getValue(String.class));
                    party.setAddress(partySnapshot.child("partyLocation").getValue(String.class));
                    party.setTime(partySnapshot.child("partyDate").getValue(String.class));

                    partyList.add(party);
                }

                // Notify adapter of data change
                partyAdapter.notifyDataSetChanged();
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {
                Toast.makeText(getContext(), "Failed to load parties: " + error.getMessage(), Toast.LENGTH_SHORT).show();
            }
        });
    }
}
