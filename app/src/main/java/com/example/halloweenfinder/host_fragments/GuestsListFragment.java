package com.example.halloweenfinder.host_fragments;

import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.example.halloweenfinder.R;
import com.example.halloweenfinder.models.Guest;
import com.example.halloweenfinder.adapters.GuestAdapter;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;

public class GuestsListFragment extends Fragment {

    private RecyclerView recyclerGuests;
    private GuestAdapter guestAdapter;
    private ArrayList<Guest> guestList;
    private DatabaseReference databaseReference;
    private String partyId;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.host_guests_list_fr, container, false);

        // Initialize RecyclerView
        recyclerGuests = view.findViewById(R.id.recycler_guests);
        recyclerGuests.setLayoutManager(new LinearLayoutManager(getContext()));

        // Initialize guest list and adapter
        guestList = new ArrayList<>();
        guestAdapter = new GuestAdapter(guestList);
        recyclerGuests.setAdapter(guestAdapter);

        // Get the party ID from arguments
        if (getArguments() != null) {
            partyId = getArguments().getString("partyId");
            Log.d("GuestsListFragment", "Party ID: " + partyId);
        } else {
            Log.e("GuestsListFragment", "Party ID is missing!");
            return view; // Exit early if no partyId is provided
        }

        // Firebase reference for the selected party's guests
        databaseReference = FirebaseDatabase.getInstance().getReference("Parties").child(partyId);

        // Fetch guests from Firebase
        fetchGuests();

        return view;
    }

    private void fetchGuests() {
        guestList.clear();

        databaseReference.child("guests").addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                Log.d("GuestsListFragment", "Snapshot received: " + snapshot.toString());

                if (!snapshot.exists()) {
                    Log.e("GuestsListFragment", "No guests found for party ID: " + partyId);
                    guestAdapter.notifyDataSetChanged();
                    return;
                }

                guestList.clear();

                for (DataSnapshot guestSnapshot : snapshot.getChildren()) {
                    String guestId = guestSnapshot.getKey();
                    String guestEmail = guestSnapshot.getValue(String.class);

                    Log.d("GuestsListFragment", "Guest ID: " + guestId + ", Email: " + guestEmail);

                    if (guestId != null && guestEmail != null) {
                        guestList.add(new Guest(guestId, guestEmail));
                    }
                }

                guestAdapter.notifyDataSetChanged();
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {
                Log.e("GuestsListFragment", "Failed to fetch guests: " + error.getMessage());
            }
        });
    }

}
