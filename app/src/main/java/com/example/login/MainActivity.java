package com.example.login;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.RecyclerView;

import android.app.Dialog;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.jar.Attributes;

public class MainActivity extends AppCompatActivity {

Button lot,ins;
ListView mylistview;
List<Student> studentsList;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);


        lot = findViewById(R.id.logout);
        lot.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                FirebaseAuth.getInstance().signOut();
                startActivity(new Intent(getApplicationContext(),Login.class));
            }
        });

        ins = findViewById(R.id.insert);
        ins.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(getApplicationContext(),insert.class));
            }
        });
        studentsList = new ArrayList<>();
        mylistview = findViewById(R.id.list);



        DatabaseReference reference = FirebaseDatabase.getInstance().getReference().child("students");
        reference.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                studentsList.clear();
                for(DataSnapshot snap : snapshot.getChildren()){
                      Student student = snap.getValue(Student.class);
                      studentsList.add(student);

                }
                ListAdapter adapter = new ListAdapter(MainActivity.this,studentsList);
                mylistview.setAdapter(adapter);
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });
        mylistview.setOnItemLongClickListener(new AdapterView.OnItemLongClickListener() {
            @Override
            public boolean onItemLongClick(AdapterView<?> parent, View view,int position, long id) {
                    Student students = studentsList.get(position);
                    showupdatedialogue(students.getName(),students.getName(), students.getroll_no(),students.getclass_section(),students.getdate_of_birth(),students.getclass_teacher());
                return false;
            }
        });

    }

    public void showupdatedialogue(String id , String name, String r,String c,String d,String t){

        AlertDialog.Builder builder = new AlertDialog.Builder(MainActivity.this);
        LayoutInflater inflater = getLayoutInflater();
        View dialog = inflater.inflate(R.layout.update,null);
        AlertDialog alert = builder.create();
        alert.setView(dialog);

        EditText naame = dialog.findViewById(R.id.name);
        EditText rollno = dialog.findViewById(R.id.roll);
        EditText cla = dialog.findViewById(R.id.clas);
        EditText date = dialog.findViewById(R.id.dob);
        EditText teacher = dialog.findViewById(R.id.teacher);
        Button up = dialog.findViewById(R.id.update);
        Button del = dialog.findViewById(R.id.de);

        alert.setTitle("Updating" + " " + name + " " + "record");
        alert.show();
        DatabaseReference reference = FirebaseDatabase.getInstance().getReference("students").child(name);
        reference.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                for(DataSnapshot snap : snapshot.getChildren()){
                    naame.setText(name);
                    rollno.setText(r);
                    cla.setText(c);
                    date.setText(d);
                    teacher.setText(t);
                }

            }
            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });
        del.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                deletestudents(name);
                alert.dismiss();
            }
        });
        up.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String n = naame.getText().toString();
                String r = rollno.getText().toString();
                String c = cla.getText().toString();
                String d = date.getText().toString();
                String t = teacher.getText().toString();
                updatestudents(id,n,r,d,c,t);
                alert.dismiss();
            }
        });

    }
    private void updatestudents(String id, String name, String roll_no, String dob, String clas, String tea){
        DatabaseReference ref = FirebaseDatabase.getInstance().getReference("students").child(id);

        HashMap h = new HashMap();
        h.put("name", name);
        h.put("roll_no", roll_no);
        h.put("date_of_birth", dob);
        h.put("class_section", clas);
        h.put("class_teacher", tea);
        ref.updateChildren(h).addOnCompleteListener(new OnCompleteListener<Void>() {
            @Override
            public void onComplete(@NonNull Task<Void> task) {
                showmsg("record updated");
            }
        });

    }
    private void deletestudents(String id){
        DatabaseReference ref = FirebaseDatabase.getInstance().getReference("students").child(id);
        ref.removeValue().addOnCompleteListener(new OnCompleteListener<Void>() {
            @Override
            public void onComplete(@NonNull Task<Void> task) {
                showmsg("record deleted");
            }
        });

    }
    private void showmsg(String msg){
        Toast.makeText(this,msg,Toast.LENGTH_LONG).show();
    }


}