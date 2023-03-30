package com.example.mynotes;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.MenuItem;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.FirebaseFirestore;

import java.util.HashMap;
import java.util.Map;

public class EditNotes extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_edit_notes);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        Intent intent = getIntent();
        Bundle bundle = intent.getExtras();
        String t = bundle.getString("title");
        String n = bundle.getString("notes");
        String ti = bundle.getString("time");
        String d = bundle.getString("date");
        String i = bundle.getString("noteid");

        EditText title = findViewById(R.id.title),
                note = findViewById(R.id.content);
        title.setText(t);
        note.setText(n);

        FloatingActionButton save = findViewById(R.id.save);
        FirebaseUser user = FirebaseAuth.getInstance().getCurrentUser();

        save.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String newtitle = title.getText().toString();
                String newnote = note.getText().toString();
                if(newtitle.isEmpty())
                {
                    Toast.makeText(save.getContext(),"Enter title first",Toast.LENGTH_SHORT).show();
                    return;
                }
                DocumentReference ref = FirebaseFirestore.getInstance().collection("MyNotes").document(user.getUid()).collection("Notes").document(i);
                Map<String,Object>note = new HashMap<>();
                note.put("title",newtitle);
                note.put("time",ti);
                note.put("date",d);
                note.put("contents",newnote);
                ref.set(note).addOnCompleteListener(new OnCompleteListener<Void>() {
                    @Override
                    public void onComplete(@NonNull Task<Void> task) {
                        if(task.isSuccessful())
                        {
                            Toast.makeText(save.getContext(),"Note is updated!",Toast.LENGTH_SHORT).show();
                            startActivity(new Intent(save.getContext(),mother.class));
                            finish();
                        }
                        else
                        {
                            Toast.makeText(save.getContext(),"Noted is failed to update!",Toast.LENGTH_SHORT).show();
                            return;
                        }
                    }
                });
            }
        });

    }

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        if(item.getItemId() ==android.R.id.home)
        {
            onBackPressed();
        }
        return super.onOptionsItemSelected(item);
    }
}