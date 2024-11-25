package com.example.halloweenfinder.main;

import android.os.Bundle;
import android.view.View;
import android.widget.LinearLayout;

import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentTransaction;

import com.example.halloweenfinder.R;
import com.example.halloweenfinder.guest_fragments.PartyListFragment;
import com.example.halloweenfinder.guest_fragments.ReviewFragment;
import com.example.halloweenfinder.host_fragments.GuestsListFragment;
import com.example.halloweenfinder.host_fragments.HostProfileFragment;
import com.example.halloweenfinder.host_fragments.PartyCreationFragment;
import com.example.halloweenfinder.shared_fragments.ConversationFragment;
import com.example.halloweenfinder.shared_fragments.MapFragment;

public class HostHomePage extends AppCompatActivity {

    private LinearLayout partyCreation , guestListButton , profileButton , mapButton, conversationRoomButton;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.main_host_home);

        partyCreation = findViewById(R.id.partyCreationButton);
        guestListButton = findViewById(R.id.guestsListButton);
        profileButton  = findViewById(R.id.hostProfileButton);
        mapButton = findViewById(R.id.mapButton);
        conversationRoomButton = findViewById(R.id.conversationRoomButton);

        loadFragment(new PartyCreationFragment());


        partyCreation.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                loadFragment(new PartyCreationFragment());
            }
        });

        guestListButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                loadFragment(new GuestsListFragment());
            }
        });

        profileButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                loadFragment(new HostProfileFragment());
            }
        });

        mapButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                loadFragment(new MapFragment());
            }
        });

        conversationRoomButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                loadFragment(new ConversationFragment());
            }
        });
    }

    public void loadFragment(Fragment fragment) {
        FragmentTransaction transaction = getSupportFragmentManager().beginTransaction();
        transaction.replace(R.id.fragmentContainer, fragment);
        transaction.commit();
    }
}