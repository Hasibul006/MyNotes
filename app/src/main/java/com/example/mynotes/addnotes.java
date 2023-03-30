package com.example.mynotes;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.android.material.timepicker.TimeFormat;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.FirebaseFirestore;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.HashMap;
import java.util.Locale;
import java.util.Map;

public class addnotes extends AppCompatActivity {



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_addnotes);

        EditText title=findViewById(R.id.title),notes=findViewById(R.id.content);
        FloatingActionButton button = findViewById(R.id.save);
        FirebaseUser user = FirebaseAuth.getInstance().getCurrentUser();
        DocumentReference dref = FirebaseFirestore.getInstance().collection("MyNotes").document(user.getUid()).collection("Notes").document();

        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String name = title.getText().toString();
                String write = notes.getText().toString();
                Calendar calendar=Calendar.getInstance();
                SimpleDateFormat datef = new SimpleDateFormat("dd mm, yy");
                SimpleDateFormat timef = new SimpleDateFormat("hh:mm");
                String date = DateFormat.getDateInstance(DateFormat.MEDIUM).format(calendar.getTime());
                String time = timef.format(calendar.getTime());





                Map<String,Object>note = new HashMap<>();
                note.put("title",name);
                note.put("contents",write);
                note.put("time",time);
                note.put("date",date);
                dref.set(note).addOnCompleteListener(new OnCompleteListener<Void>() {
                    @Override
                    public void onComplete(@NonNull Task<Void> task) {
                        if(task.isSuccessful())
                        {
                            Toast.makeText(button.getContext(),"New note is created",Toast.LENGTH_SHORT).show();
                            startActivity(new Intent(button.getContext(),mother.class));
                            finish();
                        }
                        else
                        {
                            Toast.makeText(button.getContext(),"New note connot be created",Toast.LENGTH_SHORT).show();
                            return;
                        }
                    }
                });
            }
        });

    }
}