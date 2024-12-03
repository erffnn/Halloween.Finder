package models;

public class Party {
    private String partyId;
    private String name;
    private String description;
    private String address; // Field for address
    private String time;    // Field for party date
    private String location; // New field for location

    public Party() {
        // Default constructor required for Firebase
    }

    // Getter and Setter for partyId
    public String getPartyId() {
        return partyId;
    }

    public void setPartyId(String partyId) {
        this.partyId = partyId;
    }

    // Getter and Setter for name
    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    // Getter and Setter for description
    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    // Getter and Setter for address
    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    // Getter and Setter for time (party date)
    public String getTime() {
        return time;
    }

    public void setTime(String time) {
        this.time = time;
    }

    // Getter and Setter for location
    public String getLocation() {
        return location;
    }

    public void setLocation(String location) {
        this.location = location;
    }
}
