package com.example.halloweenfinder.shared_fragments;

import android.os.Bundle;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

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
import com.google.firebase.auth.FirebaseAuth;

import java.util.ArrayList;

import adapters.ChatAdapter;

public class ConversationFragment extends Fragment {

    private RecyclerView recyclerMessages;
    private EditText editMessage;
    private Button btnSendMessage;
    private DatabaseReference chatDatabaseReference;
    private DatabaseReference usersDatabaseReference;

    private ArrayList<String> messageList;
    private ChatAdapter chatAdapter;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.shared_conversation_fr, container, false);

        // Initialize UI elements
        recyclerMessages = view.findViewById(R.id.recycler_messages);
        editMessage = view.findViewById(R.id.edit_message);
        btnSendMessage = view.findViewById(R.id.btn_send_message);

        // Initialize Firebase database references
        chatDatabaseReference = FirebaseDatabase.getInstance().getReference("chats");
        usersDatabaseReference = FirebaseDatabase.getInstance().getReference("users");

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
                    String senderName = messageSnapshot.child("senderName").getValue(String.class);
                    String message = messageSnapshot.child("message").getValue(String.class);

                    if (senderName != null && message != null) {
                        messageList.add(senderName + ": " + message);
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
        if (TextUtils.isEmpty(message)) {
            Toast.makeText(getContext(), "Message cannot be empty", Toast.LENGTH_SHORT).show();
            return;
        }

        FirebaseAuth auth = FirebaseAuth.getInstance();
        if (auth.getCurrentUser() == null) {
            Toast.makeText(getContext(), "User is not logged in", Toast.LENGTH_SHORT).show();
            return;
        }

        String currentUserId = auth.getCurrentUser().getUid();
        Toast.makeText(getContext(), "Current User ID: " + currentUserId, Toast.LENGTH_SHORT).show();

        // Explicitly access the `users` node to fetch the current user's name
        usersDatabaseReference.child(currentUserId).addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                if (snapshot.exists()) {
                    String userName = snapshot.child("name").getValue(String.class);
                    if (userName != null) {
                        Toast.makeText(getContext(), "User Name: " + userName, Toast.LENGTH_SHORT).show();

                        // Create a new message entry with the user's name and message
                        DatabaseReference newMessageRef = chatDatabaseReference.push();
                        newMessageRef.child("senderName").setValue(userName);
                        newMessageRef.child("message").setValue(message);

                        editMessage.setText(""); // Clear the input field after sending
                        Toast.makeText(getContext(), "Message sent successfully", Toast.LENGTH_SHORT).show();
                    } else {
                        Toast.makeText(getContext(), "Name field is missing for this user", Toast.LENGTH_SHORT).show();
                    }
                } else {
                    Toast.makeText(getContext(), "User ID not found in database", Toast.LENGTH_SHORT).show();
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {
                Toast.makeText(getContext(), "Error fetching user name: " + error.getMessage(), Toast.LENGTH_SHORT).show();
            }
        });
    }




}
