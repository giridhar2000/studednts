package com.example.login;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.HashMap;

public class updatestudents extends AppCompatActivity {
Button up,del,reload;
TextView u;
insert a = new insert();

MainActivity ma = new MainActivity();
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_updatestudents);
        EditText naame = findViewById(R.id.name);
        EditText rollno = findViewById(R.id.roll);
        EditText cla = findViewById(R.id.clas);
        EditText date = findViewById(R.id.dob);
        EditText teacher = findViewById(R.id.teacher);
        Button btn = findViewById(R.id.update);
        Button del = findViewById(R.id.back);



        u = findViewById(R.id.na);
        Bundle extras = getIntent().getExtras();
        String Current_Value = null;
        if (extras != null) {
            Current_Value = getIntent().getStringExtra("updating");
        }
        u.setText("Updating" + " " + "Data for" + " " + Current_Value);
        DatabaseReference ref = FirebaseDatabase.getInstance().getReference("students").child(Current_Value);
        naame.setText(Current_Value + "" + "(Name Cannot be updated)");



        btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

//                String n = Name.getText().toString();
                String r = rollno.getText().toString();
                String c = cla.getText().toString();
                String d = date.getText().toString();
                String t = teacher.getText().toString();

                HashMap h = new HashMap();
//                h.put("name", n);
                h.put("roll_no", r);
                h.put("date_of_birth", d);
                h.put("class_section", c);
                h.put("class_teacher", t);
                ref.updateChildren(h).addOnCompleteListener(new OnCompleteListener<Void>() {
                    @Override
                    public void onComplete(@NonNull Task<Void> task) {
                        Toast.makeText(updatestudents.this, "data updated", Toast.LENGTH_LONG).show();
                        startActivity(new Intent(getApplicationContext(), MainActivity.class));

                    }
                });
            }
        });

        del.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                ref.removeValue();
                Toast.makeText(updatestudents.this, "data deleted", Toast.LENGTH_LONG).show();
                startActivity(new Intent(getApplicationContext(), MainActivity.class));

            }
        });
    }
}