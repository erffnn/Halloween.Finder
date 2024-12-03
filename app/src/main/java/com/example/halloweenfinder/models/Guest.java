package com.example.halloweenfinder.models;

public class Guest {
    private String guestId;
    private String guestEmail;

    public Guest(String guestId, String guestEmail) {
        this.guestId = guestId;
        this.guestEmail = guestEmail;
    }

    public String getGuestId() {
        return guestId;
    }

    public void setGuestId(String guestId) {
        this.guestId = guestId;
    }

    public String getGuestEmail() {
        return guestEmail;
    }

    public void setGuestEmail(String guestEmail) {
        this.guestEmail = guestEmail;
    }
}
