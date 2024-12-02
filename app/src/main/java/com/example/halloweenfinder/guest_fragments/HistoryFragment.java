package com.example.halloweenfinder.guest_fragments;

import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import com.example.halloweenfinder.R;
import com.example.halloweenfinder.adapters.PartyHistoryAdapter;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.firestore.FirebaseFirestore;

import java.util.ArrayList;
import java.util.List;

import models.Party;

public class HistoryFragment extends Fragment {

    private RecyclerView recyclerPartyHistory;
    private PartyHistoryAdapter adapter;
    private List<Party> partyList;

    public HistoryFragment() {
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        return inflater.inflate(R.layout.guest_history_fr, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        recyclerPartyHistory = view.findViewById(R.id.recycler_party_history);
        recyclerPartyHistory.setLayoutManager(new LinearLayoutManager(getContext()));

        partyList = new ArrayList<>();
        adapter = new PartyHistoryAdapter(partyList);
        recyclerPartyHistory.setAdapter(adapter);

        loadPartyHistory();
    }

    private void loadPartyHistory() {
        FirebaseFirestore db = FirebaseFirestore.getInstance();
        FirebaseAuth auth = FirebaseAuth.getInstance();

        db.collection("Parties")
                .whereEqualTo("guestId", auth.getCurrentUser().getUid())
                .get()
                .addOnCompleteListener(task -> {
                    if (task.isSuccessful() && task.getResult() != null && !task.getResult().isEmpty()) {
                        partyList.clear();
                        task.getResult().forEach(document -> {
                            Party party = document.toObject(Party.class);
                            partyList.add(party);
                        });
                        adapter.notifyDataSetChanged();
                    } else {
                        // Show a message if no history is found
                        Toast.makeText(getContext(), "No history found.", Toast.LENGTH_SHORT).show();
                    }
                })
                .addOnFailureListener(e -> {
                    Toast.makeText(getContext(), "Failed to load party history: " + e.getMessage(), Toast.LENGTH_SHORT).show();
                });
    }
}
