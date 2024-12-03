package com.example.halloweenfinder.models;

public class Party {
    private String partyId;
    private String partyName;
    private String partyDescription;
    private String partyDate;
    private String address; // New field
    private String hostName; // New field
    private String time; // New field

    public Party() {
    }

    // Parameterized constructor
    public Party(String partyId, String partyName, String partyDescription, String partyDate, String address, String hostName, String time) {
        this.partyId = partyId;
        this.partyName = partyName;
        this.partyDescription = partyDescription;
        this.partyDate = partyDate;
        this.address = address;
        this.hostName = hostName;
        this.time = time;
    }

    // Getters and Setters
    public String getPartyId() {
        return partyId;
    }

    public void setPartyId(String partyId) {
        this.partyId = partyId;
    }

    public String getPartyName() {
        return partyName;
    }

    public void setPartyName(String partyName) {
        this.partyName = partyName;
    }

    public String getPartyDescription() {
        return partyDescription;
    }

    public void setPartyDescription(String partyDescription) {
        this.partyDescription = partyDescription;
    }

    public String getPartyDate() {
        return partyDate;
    }

    public void setPartyDate(String partyDate) {
        this.partyDate = partyDate;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public String getHostName() {
        return hostName;
    }

    public void setHostName(String hostName) {
        this.hostName = hostName;
    }

    public String getTime() {
        return time;
    }

    public void setTime(String time) {
        this.time = time;
    }
}
