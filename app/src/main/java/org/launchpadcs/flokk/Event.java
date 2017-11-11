package org.launchpadcs.flokk;

/**
 * Created by ccirr on 10/11/2017.
 */

public class Event {
    private String title;
    private String description;
    private String date;
    private String location;
    private String authToken;
    public String _id;

    public Event(String title, String description, String date, String location, String authToken) {
        this.title = title;
        this.description = description;
        this.date = date;
        this.location = location;
        this.authToken = authToken;
    }

    public Event(String title, String description, String date, String location) {
        this(title, description, date, location, null);
    }

    public Event() {

    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getDate() {
        return date;
    }

    public void setDate(String date) {
        this.date = date;
    }

    public String getLocation() {
        return location;
    }

    public void setLocation(String location) {
        this.location = location;
    }

    public String getAuthToken() {
        return authToken;
    }

    public void setAuthToken(String authToken) {
        this.authToken = authToken;
    }
}
