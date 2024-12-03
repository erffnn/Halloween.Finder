package com.example.halloweenfinder.models;

public class Guest {
    private String partyName;
    private String guestId;
    private String guestEmail;

    // Constructor
    public Guest(String partyName, String guestId) {
        this.partyName = partyName;
        this.guestId = guestId;
        this.guestEmail = guestEmail;
    }

    // Getter methods
    public String getPartyName() {
        return partyName;
    }

    public String getGuestId() {
        return guestId;
    }

    public String getGuestEmail() {
        return guestEmail;
    }
}
