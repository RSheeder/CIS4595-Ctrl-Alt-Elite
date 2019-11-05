package com.example.lochat;
import android.widget.TextView;

public class Chat {
    private DBAccess dbAccess;
    private final TextView mTextView;
    private String chatID;


    public Chat(TextView textView, String chatID) {
        this.chatID = chatID;
        dbAccess = new DBAccess();
        mTextView = textView;
    }

    public void writeNewMessage(String userName, String message) {
        Message newMessage = new Message(userName, message);
        dbAccess.write(chatID, newMessage);
    }

    public void readNewMessage() {
        dbAccess.read(mTextView, chatID);
    }

    public void chatGeneration() {
        dbAccess.createChild(chatID);
    }
}
