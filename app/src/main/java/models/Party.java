package models;

public class Party {
    private String name;
    private String location;
    private String date;

    // Empty constructor (required for Firebase)
    public Party() {}

    // Constructor
    public Party(String name, String location, String date) {
        this.name = name;
        this.location = location;
        this.date = date;
    }

    // Getters and Setters
    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getLocation() {
        return location;
    }

    public void setLocation(String location) {
        this.location = location;
    }

    public String getDate() {
        return date;
    }

    public void setDate(String date) {
        this.date = date;
    }
}
