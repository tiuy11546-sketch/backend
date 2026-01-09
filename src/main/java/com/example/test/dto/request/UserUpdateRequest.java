package com.example.test.dto.request;

import java.util.UUID;

public class UserUpdateRequest {
    private UUID id;
    private String email;
    private String firstName;
    private String lastName;
    private String panNumber;
    private String state;
    private String lastDigit;
    private String commodity;
    private String password;
    private String username;
    private boolean enabled;

    public UserUpdateRequest(UUID id, String email, String firstName, String lastName, String panNumber, String state, String lastDigit, String commodity, String password, String username, boolean enabled) {
        this.id = id;
        this.email = email;
        this.firstName = firstName;
        this.lastName = lastName;
        this.panNumber = panNumber;
        this.state = state;
        this.lastDigit = lastDigit;
        this.commodity = commodity;
        this.password = password;
        this.username = username;
        this.enabled = enabled;
    }

    public UserUpdateRequest() {
    }

    public UUID getId() {
        return id;
    }

    public void setId(UUID id) {
        this.id = id;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getFirstName() {
        return firstName;
    }

    public void setFirstName(String firstName) {
        this.firstName = firstName;
    }

    public String getLastName() {
        return lastName;
    }

    public void setLastName(String lastName) {
        this.lastName = lastName;
    }

    public String getPanNumber() {
        return panNumber;
    }

    public void setPanNumber(String panNumber) {
        this.panNumber = panNumber;
    }

    public String getState() {
        return state;
    }

    public void setState(String state) {
        this.state = state;
    }

    public String getLastDigit() {
        return lastDigit;
    }

    public void setLastDigit(String lastDigit) {
        this.lastDigit = lastDigit;
    }

    public String getCommodity() {
        return commodity;
    }

    public void setCommodity(String commodity) {
        this.commodity = commodity;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public boolean isEnabled() {
        return enabled;
    }

    public void setEnabled(boolean enabled) {
        this.enabled = enabled;
    }
}
