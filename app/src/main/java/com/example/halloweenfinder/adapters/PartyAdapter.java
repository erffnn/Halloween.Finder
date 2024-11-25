package com.example.halloweenfinder.adapters;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.halloweenfinder.R;
import models.Party;

import java.util.ArrayList;

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
        holder.partyLocation.setText(party.getLocation());
        holder.partyDate.setText(party.getDate());
    }

    @Override
    public int getItemCount() {
        return partyList.size();
    }

    public static class PartyViewHolder extends RecyclerView.ViewHolder {
        TextView partyName, partyLocation, partyDate;

        public PartyViewHolder(@NonNull View itemView) {
            super(itemView);
            partyName = itemView.findViewById(R.id.partyNameTextView);
            partyLocation = itemView.findViewById(R.id.partyLocationTextView);
            partyDate = itemView.findViewById(R.id.partyDateTextView);
        }
    }
}
