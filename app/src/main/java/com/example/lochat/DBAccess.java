package com.example.lochat;
import android.widget.TextView;

import com.google.firebase.database.ChildEventListener;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

public class DBAccess {

    private DatabaseReference ref;

    public DBAccess() { ref = FirebaseDatabase.getInstance().getReference().child("chats");}

    public void write(String chatID, Message newMessage) {
        String msgId = ref.child(chatID).push().getKey();
        ref.child(chatID).child(msgId).setValue(newMessage);
    }

    public void read(TextView textView, String chatID){
        final TextView mTextView = textView;
        ChildEventListener childEventListener = new ChildEventListener() {
            @Override
            public void onChildAdded(DataSnapshot dataSnapshot, String previousChildName) {
                Message message = dataSnapshot.getValue(Message.class);
                mTextView.append(message.getUsername() + ": " + message.getMessage() + "\n");
            }

            @Override
            public void onChildChanged(DataSnapshot dataSnapshot, String previousChildName) { }
            @Override
            public void onChildRemoved(DataSnapshot dataSnapshot) { }
            @Override
            public void onChildMoved(DataSnapshot dataSnapshot, String previousChildName) { }
            @Override
            public void onCancelled(DatabaseError databaseError) { }

        };
        ref.child(chatID).limitToLast(1).addChildEventListener(childEventListener);
    }

    public void createChild(String name) {
        final String childName = name;
        final DatabaseReference ref1 = ref;
        ref.child(childName).addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot snapshot) {
                if (snapshot.getValue() == null) { ref1.setValue(childName); }
            }
            @Override
            public void onCancelled(DatabaseError databaseError) {
            }
        });
    }
}
