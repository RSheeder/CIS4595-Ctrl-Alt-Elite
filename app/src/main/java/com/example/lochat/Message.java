package com.example.lochat;

public class Message {

    private String message;
    private String username;

    public Message() {}

    public Message(String username, String message) {this.message = message; this.username = username;}

    public String getMessage() {
        return message;
    }

    public String getUsername() {
        return username;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public void setUsername(String username) {
        this.username = username;
    }
}
