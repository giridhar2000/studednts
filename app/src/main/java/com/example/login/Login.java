package com.example.login;

import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;

public class Login extends AppCompatActivity {
TextView new1;
EditText user,pass;
Button login;
ProgressBar progress;
FirebaseAuth fAuth;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        new1 = findViewById(R.id.newuser);
        user = findViewById(R.id.username);
        pass = findViewById(R.id.password);
        login = findViewById(R.id.login);
        progress = findViewById(R.id.loading);
        fAuth = FirebaseAuth.getInstance();

        new1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(getApplicationContext(),Register.class));
            }
        });
        login.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String name = user.getText().toString().trim();
                String password = pass.getText().toString().trim();

                if(TextUtils.isEmpty(name)) {
                    user.setError("Enter your email");
                    return;
                }

                if(TextUtils.isEmpty(password)) {
                    pass.setError("Enter a password");
                    return;
                }

                progress.setVisibility((View.VISIBLE));
                fAuth.signInWithEmailAndPassword(name,password).addOnCompleteListener(new OnCompleteListener<AuthResult>() {
                    @Override
                    public void onComplete(@NonNull Task<AuthResult> task) {
                        if(task.isSuccessful()){
                            startActivity(new Intent(getApplicationContext(),MainActivity.class));
                        }
                        else{
                            progress.setVisibility((View.INVISIBLE));
                            Toast.makeText(Login.this,"unsuccessful",Toast.LENGTH_LONG).show();
                        }
                    }
                });
            }
        });
    }
}