package com;

public class User {
    private int id;
    private String username;
    private String password;
    // Add more user-related properties as needed

    // Constructor
    public User(int id, String username, String password) {
        this.id = id;
        this.username = username;
        this.password = password;
    }

    // Getters and Setters
    // Add getter and setter methods for each property

    // Override toString() method for debugging
    @Override
    public String toString() {
        return "User [id=" + id + ", username=" + username + "]";
    }
}



