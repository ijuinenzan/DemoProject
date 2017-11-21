package com.example.ijuin.demoproject;

import android.graphics.Color;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;

import com.github.bassaer.chatmessageview.model.User;
import com.github.bassaer.chatmessageview.models.Message;
import com.github.bassaer.chatmessageview.views.ChatView;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.ChildEventListener;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Locale;

public class ChatActivity extends AppCompatActivity {
    private FirebaseDatabase _database;
    private DatabaseReference _messageRef;
    private ChatView _chatView;
    private EditText _usernameText;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_chat);

        _chatView = findViewById(R.id.chatview);
        _chatView.setAutoScroll(true);
        _chatView.setEnableSendButton(true);


        _usernameText = findViewById(R.id.username);

        _database = FirebaseDatabase.getInstance();


        _messageRef = _database.getReference("messages");

        _chatView.setOnClickSendButtonListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                String key = _messageRef.push().getKey();

                MessageModel myMessage = new MessageModel();
                myMessage.set_senderId(FirebaseAuth.getInstance().getCurrentUser().getUid());
                myMessage.set_content(_chatView.getInputText());
                myMessage.set_senderName(_usernameText.getText().toString());
                myMessage.set_time(new SimpleDateFormat("yyyy/MM/dd HH:mm:ss").format(new Date()));
                _messageRef.child(key).setValue(myMessage);
            }
        });

        _messageRef.addChildEventListener(new ChildEventListener() {
            @Override
            public void onChildAdded(DataSnapshot dataSnapshot, String s) {
                MessageModel myMessage = dataSnapshot.getValue(MessageModel.class);

                if(myMessage.get_senderId().equals(FirebaseAuth.getInstance().getCurrentUser().getUid()))
                {
                    messageSent(myMessage.get_content(), myMessage.get_senderId(), myMessage.get_senderName());
                }
                else
                {
                    messageReceived(myMessage.get_content(), myMessage.get_senderId(), myMessage.get_senderName());
                }
            }

            @Override
            public void onChildChanged(DataSnapshot dataSnapshot, String s) {

            }

            @Override
            public void onChildRemoved(DataSnapshot dataSnapshot) {

            }

            @Override
            public void onChildMoved(DataSnapshot dataSnapshot, String s) {

            }

            @Override
            public void onCancelled(DatabaseError databaseError) {

            }
        });
    }

    private void messageSent(String content, String senderId, String senderName)
    {
        Message chatMessage = new Message.Builder()
                .setUser(new User(0, senderName, null))
                .setRightMessage(true)
                .setMessageText(content)
                .build();
        _chatView.send(chatMessage);
    }

    private void messageReceived(String content, String senderId, String senderName)
    {
        Message chatMessage = new Message.Builder()
                .setUser(new User(1, senderName, null))
                .setRightMessage(false)
                .setMessageText(content)
                .build();
        _chatView.receive(chatMessage);
    }
}
