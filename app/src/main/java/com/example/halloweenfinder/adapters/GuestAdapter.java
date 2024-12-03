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

    @NonNull
    @Override
    public GuestViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.host_guest_list_item, parent, false);
        return new GuestViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull GuestViewHolder holder, int position) {
        Guest guest = guestList.get(position);
        holder.guestEmail.setText(guest.getGuestEmail());
    }

    @Override
    public int getItemCount() {
        return guestList == null ? 0 : guestList.size();
    }

    static class GuestViewHolder extends RecyclerView.ViewHolder {
        TextView guestEmail;

        public GuestViewHolder(@NonNull View itemView) {
            super(itemView);
            guestEmail = itemView.findViewById(R.id.guestNameTextView); // Update to use correct ID
        }
    }
}
