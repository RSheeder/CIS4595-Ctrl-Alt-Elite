package com.example.lochat;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

public class MessengerActivity extends AppCompatActivity {
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_messenger);
        final String chatId = "32566";
        final String userID = "U1-Bob";

        TextView mTextView = findViewById(R.id.textView);
        final EditText edit = findViewById(R.id.EditText1);
        final Chat chat = new Chat(mTextView, chatId);

        chat.chatGeneration();

        chat.readNewMessage();

        final Button button = findViewById(R.id.Send);
        button.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                String message = edit.getText().toString();
                chat.writeNewMessage(userID, message);
                edit.setText("");
            }
        });
    }
}
