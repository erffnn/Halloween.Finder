package com.example.halloweenfinder.models;

public class Guest {
    private String guestId;
    private String guestName;

    public Guest(){

    }

    public Guest(String guestId, String guestName) {
        this.guestId = guestId;
        this.guestName = guestName;
    }

    public String getGuestId() {
        return guestId;
    }

    public void setGuestId(String guestId) {
        this.guestId = guestId;
    }

    public String getGuestName() {
        return guestName;
    }

    public void setGuestName(String guestName) {
        this.guestName = guestName;
    }
}
