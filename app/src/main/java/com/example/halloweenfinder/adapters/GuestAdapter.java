package com.example.halloweenfinder.adapters;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.halloweenfinder.R;
import com.example.halloweenfinder.models.Guest;

import java.util.ArrayList;

public class GuestAdapter extends RecyclerView.Adapter<GuestAdapter.GuestViewHolder> {

    private ArrayList<Guest> guestList;

    public GuestAdapter(ArrayList<Guest> guestList) {
        this.guestList = guestList;
    }

    // ViewHolder class to hold references to the TextViews
    static class GuestViewHolder extends RecyclerView.ViewHolder {
        TextView partyName;
        TextView guestUserId;
        TextView guestEmail;

        public GuestViewHolder(@NonNull View itemView) {
            super(itemView);
            partyName = itemView.findViewById(R.id.partyname);
            guestUserId = itemView.findViewById(R.id.guest_user_id);
        }
    }

    @NonNull
    @Override
    public GuestViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        // Inflate the layout for each item and return a new ViewHolder
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.host_guest_list_item, parent, false);
        return new GuestViewHolder(view);
    }

    public void onBindViewHolder(@NonNull GuestViewHolder holder, int position) {
        Guest guest = guestList.get(position);

        // Set the correct data for each guest
        holder.partyName.setText("user id: " + guest.getPartyName());
        holder.guestUserId.setText("email: " + guest.getGuestId());

    }


    @Override
    public int getItemCount() {
        return guestList == null ? 0 : guestList.size();
    }
}
