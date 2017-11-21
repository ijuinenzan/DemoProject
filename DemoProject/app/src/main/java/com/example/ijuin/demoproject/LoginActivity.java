package com.example.ijuin.demoproject;

import android.content.Intent;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;

public class LoginActivity extends AppCompatActivity {
    private EditText _userNameText;
    private EditText _passwordText;

    FirebaseAuth _auth;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        _auth = FirebaseAuth.getInstance();
        _userNameText = findViewById(R.id.username_text);
        _passwordText = findViewById(R.id.password_text);
    }

    public void signIn(View view)
    {
        _auth.signInWithEmailAndPassword(_userNameText.getText().toString(), _passwordText.getText().toString()).addOnCompleteListener(new OnCompleteListener<AuthResult>() {
            @Override
            public void onComplete(@NonNull Task<AuthResult> task) {
                if(task.isSuccessful())
                {
                    Intent chatIntent = new Intent(LoginActivity.this, ChatActivity.class);
                    startActivity(chatIntent);
                }
            }
        });
    }

    public void signUp(View view)
    {
        _auth.createUserWithEmailAndPassword(_userNameText.getText().toString(),
                _passwordText.getText().toString()).addOnCompleteListener(new OnCompleteListener<AuthResult>() {
            @Override
            public void onComplete(@NonNull Task<AuthResult> task) {
                if(task.isSuccessful())
                {
                    FirebaseUser user = _auth.getCurrentUser();
                    user.sendEmailVerification();
                }

            }
        });
    }
}
