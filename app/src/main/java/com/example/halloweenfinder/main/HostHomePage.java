package com.example.halloweenfinder.main;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.LinearLayout;

import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentTransaction;

import com.example.halloweenfinder.R;
import com.example.halloweenfinder.host_fragments.GuestsListFragment;
import com.example.halloweenfinder.shared_fragments.ProfileFragment;
import com.example.halloweenfinder.host_fragments.PartyCreationFragment;
import com.example.halloweenfinder.shared_fragments.ConversationFragment;
import com.example.halloweenfinder.shared_fragments.MapFragment;

public class HostHomePage extends AppCompatActivity {

    private LinearLayout partyCreation , guestListButton , profileButton , mapButton, conversationRoomButton, guestButton;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.main_host_home);

        partyCreation = findViewById(R.id.partyCreationButton);
        guestListButton = findViewById(R.id.guestsListButton);
        profileButton  = findViewById(R.id.hostProfileButton);
        mapButton = findViewById(R.id.mapButton);
        conversationRoomButton = findViewById(R.id.conversationRoomButton);
        guestButton = findViewById(R.id.guestBtn);


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
                loadFragment(new ProfileFragment());
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


        guestButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(HostHomePage.this, GuestHomeActivity.class);
                startActivity(intent);
            }
        });
    }

    public void loadFragment(Fragment fragment) {
        FragmentTransaction transaction = getSupportFragmentManager().beginTransaction();
        transaction.replace(R.id.fragmentContainer, fragment);
        transaction.commit();
    }
}