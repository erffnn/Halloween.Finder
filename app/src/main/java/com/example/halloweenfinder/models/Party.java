package com.example.halloweenfinder.models;

public class Party {
    private String partyId;
    private String partyName;
    private String partyDescription;
    private String partyDate;

    public Party(){

    }

    public Party(String partyId, String partyName, String partyDescription, String partyDate) {
        this.partyId = partyId;
        this.partyName = partyName;
        this.partyDescription = partyDescription;
        this.partyDate = partyDate;
    }

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
}
