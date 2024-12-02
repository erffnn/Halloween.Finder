package com.example.halloweenfinder.host_fragments;

import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.example.halloweenfinder.R;
import com.example.halloweenfinder.models.Guest;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;

import com.example.halloweenfinder.adapters.GuestAdapter;

public class GuestsListFragment extends Fragment {

    private RecyclerView recyclerGuests;
    private GuestAdapter guestAdapter;
    private ArrayList<Guest> guestList;
    private DatabaseReference databaseReference;
    private String partId;


//    @Override
//    public View onCreateView(LayoutInflater inflater, ViewGroup container,
//                             Bundle savedInstanceState) {
//        View view = inflater.inflate(R.layout.host_guests_list_fr, container, false);
//
//        if(getArguments() != null){
//            partId = getArguments().getString("partyId");
//        }
//
//        recyclerGuests = view.findViewById(R.id.recycler_guests);
//        recyclerGuests.setLayoutManager(new LinearLayoutManager(getContext()));
//        guestList = new ArrayList<>();
//        guestAdapter = new GuestAdapter(guestList);
//        recyclerGuests.setAdapter(guestAdapter);
//
//        databaseReference = FirebaseDatabase.getInstance().getReference("parties").child(partId).child("guests");
//
//        //feychgustes();
//
//        return view;
//
//    }

//    private void feychgustes() {
//        databaseReference.addValueEventListener(new ValueEventListener() {
//            @Override
//            public void onDataChange(@NonNull DataSnapshot snapshot) {
//                guestList.clear();
//                for(DataSnapshot guestSnapshot : snapshot.getChildren()){
//                    String guestId = guestSnapshot.getKey();
//                    String guestName = guestSnapshot.getValue(String.class);
//                    guestList.add(new Guest(guestId, guestName));
//                }
//                guestAdapter.notifyDataSetChanged();
//            }
//
//            @Override
//            public void onCancelled(@NonNull DatabaseError error) {
//                // error logic
//            }
//        });
//    }


    public GuestsListFragment() {
        // Required empty public constructor
    }
}