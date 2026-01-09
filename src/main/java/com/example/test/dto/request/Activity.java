package com.example.test.dto.request;

public class Activity {
    private String type;
    private String description;
    private String time;

    public Activity(String type, String description, String time) {
        this.type = type;
        this.description = description;
        this.time = time;
    }

    // Getters and setters
    public String getType() { return type; }
    public void setType(String type) { this.type = type; }
    public String getDescription() { return description; }
    public void setDescription(String description) { this.description = description; }
    public String getTime() { return time; }
    public void setTime(String time) { this.time = time; }
}
