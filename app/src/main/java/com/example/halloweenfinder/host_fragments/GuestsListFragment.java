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

        // Firebase reference for all parties
        databaseReference = FirebaseDatabase.getInstance().getReference("Parties");

        // Fetch guests from Firebase for all parties
        fetchGuests();

        return view;
    }

    private void fetchGuests() {
        guestList.clear();

        // Fetch all parties
        databaseReference.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                Log.d("GuestsListFragment", "Snapshot received: " + snapshot.toString());

                if (!snapshot.exists()) {
                    Log.e("GuestsListFragment", "No parties found.");
                    guestAdapter.notifyDataSetChanged();
                    return;
                }

                guestList.clear();

                // Iterate through each party
                for (DataSnapshot partySnapshot : snapshot.getChildren()) {
                    String partyId = partySnapshot.child("partyId").getValue(String.class);
                    String partyName = partySnapshot.child("partyName").getValue(String.class);
                    Log.d("GuestsListFragment", "Party ID: " + partyId + ", Party Name: " + partyName);

                    // Check if the guests node exists
                    DataSnapshot guestsSnapshot = partySnapshot.child("guests");
                    if (guestsSnapshot.exists()) {
                        for (DataSnapshot guestSnapshot : guestsSnapshot.getChildren()) {
                            String guestId = guestSnapshot.getKey(); // This is the guest's userId
                            String guestEmail = guestSnapshot.getValue(String.class); // This is the guest's email

                            Log.d("GuestsListFragment", "Guest ID: " + guestId + ", Email: " + guestEmail);

                            // Add guest to list
                            if (guestId != null && guestEmail != null) {
                                guestList.add(new Guest(guestId, guestEmail));
                            }
                        }
                    }
                }

                // Notify the adapter that the data has changed
                guestAdapter.notifyDataSetChanged();
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {
                Log.e("GuestsListFragment", "Failed to fetch guests: " + error.getMessage());
            }
        });
    }
}
