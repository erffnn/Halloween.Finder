package com.example.halloweenfinder.adapters;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.halloweenfinder.R;

import java.util.List;

import models.Party;

public class PartyHistoryAdapter extends RecyclerView.Adapter<PartyHistoryAdapter.ViewHolder> {

    private List<Party> partyList;

    public PartyHistoryAdapter(List<Party> partyList) {
        this.partyList = partyList;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.party_history_item, parent, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        Party party = partyList.get(position);

        holder.txtPartyName.setText(party.getName());
        holder.txtPartyDate.setText(party.getTime());

        holder.btnReviewParty.setOnClickListener(v -> {
            // Handle review button click (e.g., open a dialog or navigate to a review fragment)
        });
    }

    @Override
    public int getItemCount() {
        return partyList.size();
    }

    public static class ViewHolder extends RecyclerView.ViewHolder {
        TextView txtPartyName, txtPartyDate;
        Button btnReviewParty;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            txtPartyName = itemView.findViewById(R.id.txt_party_name);
            txtPartyDate = itemView.findViewById(R.id.txt_party_date);
            btnReviewParty = itemView.findViewById(R.id.btn_review_party);
        }
    }
}
