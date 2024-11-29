package com.example.halloweenfinder.shared_fragments;

import android.os.Bundle;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;

import androidx.annotation.NonNull;
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

import adapters.ChatAdapter;

public class ConversationFragment extends Fragment {

    private RecyclerView recyclerMessages;
    private EditText editMessage;
    private Button btnSendMessage;
    private DatabaseReference chatDatabaseReference;

    private ArrayList<String> messageList;
    private ChatAdapter chatAdapter;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.shared_conversation_fr, container, false);

        // Initialize UI elements
        recyclerMessages = view.findViewById(R.id.recycler_messages);
        editMessage = view.findViewById(R.id.edit_message);
        btnSendMessage = view.findViewById(R.id.btn_send_message);

        // Initialize Firebase database reference for chats
        chatDatabaseReference = FirebaseDatabase.getInstance().getReference("chats");

        // Initialize RecyclerView
        messageList = new ArrayList<>();
        chatAdapter = new ChatAdapter(messageList);
        recyclerMessages.setLayoutManager(new LinearLayoutManager(getContext()));
        recyclerMessages.setAdapter(chatAdapter);

        // Fetch messages from Firebase
        fetchMessages();

        // Handle sending a new message
        btnSendMessage.setOnClickListener(v -> sendMessage());

        return view;
    }

    private void fetchMessages() {
        chatDatabaseReference.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                messageList.clear();
                for (DataSnapshot messageSnapshot : snapshot.getChildren()) {
                    String message = messageSnapshot.getValue(String.class);
                    if (message != null) {
                        messageList.add(message);
                    }
                }
                chatAdapter.notifyDataSetChanged();
                recyclerMessages.scrollToPosition(messageList.size() - 1); // Scroll to the latest message
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {
                // Handle database error
            }
        });
    }

    private void sendMessage() {
        String message = editMessage.getText().toString().trim();
        if (!TextUtils.isEmpty(message)) {
            chatDatabaseReference.push().setValue(message);
            editMessage.setText(""); // Clear the input field after sending
        }
    }
}
