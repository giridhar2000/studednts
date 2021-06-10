package com.example.login;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

public class insert extends AppCompatActivity {
Button sub,back;
EditText Name,rollno,clas,teacher,dob;
DatabaseReference fRef;
long maxid = 0;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_insert);

        sub = findViewById(R.id.submit);
        back = findViewById(R.id.back);
        Name = findViewById(R.id.name);
        rollno = findViewById(R.id.roll);
        clas = findViewById(R.id.clas);
        dob = findViewById(R.id.dob);
        teacher = findViewById(R.id.teacher);
        fRef = FirebaseDatabase.getInstance().getReference().child("students");
        fRef.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                if(snapshot.exists()){
                    maxid = snapshot.getChildrenCount();
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });

        back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(getApplicationContext(),MainActivity.class));
            }
        });

        sub.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                insertData();
            }

            private void insertData() {
                String stname =  Name.getText().toString().trim();
                String roll = rollno.getText().toString().trim();
                String classec =  clas.getText().toString().trim();
                String daob =  dob.getText().toString().trim();
                String stea =  teacher.getText().toString().trim();

                if(TextUtils.isEmpty(stname) || TextUtils.isEmpty(roll)|| TextUtils.isEmpty(classec)|| TextUtils.isEmpty(daob)|| TextUtils.isEmpty(stea)){
                    Toast.makeText(insert.this,"provide input properly",Toast.LENGTH_LONG).show();
                }
                else{
                Student students = new Student(stname,roll,daob,classec,stea);
                fRef.child(stname).setValue(students);
                startActivity(new Intent(getApplicationContext(),MainActivity.class));
                Toast.makeText(insert.this,"data inserted",Toast.LENGTH_LONG).show();
                }
            }
        });
    }
}