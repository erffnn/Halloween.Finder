package com.example.halloweenfinder.adapters;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.halloweenfinder.R;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import java.util.ArrayList;

import models.Party;

public class PartyAdapter extends RecyclerView.Adapter<PartyAdapter.PartyViewHolder> {

    private ArrayList<Party> partyList;

    public PartyAdapter(ArrayList<Party> partyList) {
        this.partyList = partyList;
    }

    @NonNull
    @Override
    public PartyViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_party, parent, false);
        return new PartyViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull PartyViewHolder holder, int position) {
        Party party = partyList.get(position);

        holder.partyName.setText(party.getName());
        holder.partyLocation.setText(party.getAddress());
        holder.partyDate.setText(party.getTime());
        holder.partyDescription.setText(party.getDescription());


        holder.attendButton.setOnClickListener(v -> {
            DatabaseReference guestRef = FirebaseDatabase.getInstance()
                    .getReference("Parties")
                    .child(party.getPartyId())
                    .child("guests");

            // Fetch current user details
            FirebaseUser currentUser = FirebaseAuth.getInstance().getCurrentUser();
            if (currentUser != null) {
                String guestId = currentUser.getUid();
                String guestEmail = currentUser.getEmail();

                if (guestEmail != null && !guestEmail.isEmpty()) {
                    guestRef.child(guestId).setValue(guestEmail) // Write guest email to Firebase
                            .addOnCompleteListener(task -> {
                                if (task.isSuccessful()) {
                                    Toast.makeText(v.getContext(), "You are now attending " + party.getName(), Toast.LENGTH_SHORT).show();
                                } else {
                                    Toast.makeText(v.getContext(), "Failed to join the party. Try again.", Toast.LENGTH_SHORT).show();
                                }
                            });
                } else {
                    Toast.makeText(v.getContext(), "Email not available for this user.", Toast.LENGTH_SHORT).show();
                }
            } else {
                Toast.makeText(v.getContext(), "Please log in to attend the party.", Toast.LENGTH_SHORT).show();
            }
        });
    }

    @Override
    public int getItemCount() {
        return partyList.size();
    }

    public static class PartyViewHolder extends RecyclerView.ViewHolder {
        TextView partyName, partyLocation, partyDate, partyDescription;
        Button attendButton;

        public PartyViewHolder(@NonNull View itemView) {
            super(itemView);
            partyName = itemView.findViewById(R.id.partyNameTextView);
            partyLocation = itemView.findViewById(R.id.partyLocationTextView);
            partyDate = itemView.findViewById(R.id.partyDateTextView);
            partyDescription = itemView.findViewById(R.id.partyDescriptionTextView);
            attendButton = itemView.findViewById(R.id.attendButton);
        }
    }
}
