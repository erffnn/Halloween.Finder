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
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;

import com.example.halloweenfinder.adapters.PartyAdapter;
import models.Party;

public class PartyListFragment extends Fragment {

    private RecyclerView partyRecyclerView;
    private PartyAdapter partyAdapter;
    private ArrayList<Party> partyList;
    private DatabaseReference partyDatabaseRef;

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

        // Initialize Firebase Database Reference
        partyDatabaseRef = FirebaseDatabase.getInstance().getReference("Parties");

        // Initialize party list and adapter
        partyList = new ArrayList<>();
        partyAdapter = new PartyAdapter(partyList);
        partyRecyclerView.setAdapter(partyAdapter);

        // Fetch data from Firebase
        fetchPartiesFromFirebase();

        return view;
    }

    private void fetchPartiesFromFirebase() {
        partyDatabaseRef.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                partyList.clear();
                for (DataSnapshot partySnapshot : snapshot.getChildren()) {
                    Party party = partySnapshot.getValue(Party.class);
                    if (party != null) {
                        partyList.add(party);
                    }
                }
                partyAdapter.notifyDataSetChanged();
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {
                Toast.makeText(getContext(), "Failed to load parties: " + error.getMessage(), Toast.LENGTH_SHORT).show();
            }
        });
    }
}
