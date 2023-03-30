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
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.FirebaseFirestore;

import java.util.HashMap;
import java.util.Map;

public class EditToDo extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_edit_to_do);

        Intent intent = getIntent();
        Bundle bundle = intent.getExtras();
        String todo = bundle.getString("title");
        String value = bundle.getString("value");
        String noteid = bundle.getString("noteid");

        EditText title = findViewById(R.id.title);
        title.setText(todo);

        FloatingActionButton save = findViewById(R.id.save);
        FirebaseUser user = FirebaseAuth.getInstance().getCurrentUser();

        save.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String newtitle = title.getText().toString();
                if(newtitle.isEmpty())
                {
                    Toast.makeText(save.getContext(),"Enter title first",Toast.LENGTH_SHORT).show();
                    return;
                }
                DatabaseReference ref = FirebaseDatabase.getInstance().getReference().child("MyTask").child(user.getUid()).child(noteid).child("todo");

                ref.setValue(newtitle).addOnCompleteListener(new OnCompleteListener<Void>() {
                    @Override
                    public void onComplete(@NonNull Task<Void> task) {
                        if(task.isSuccessful())
                        {
                            Toast.makeText(save.getContext(),"Task is updated!",Toast.LENGTH_SHORT).show();
                            startActivity(new Intent(save.getContext(),todo.class));
                            finish();
                        }
                        else
                        {
                            Toast.makeText(save.getContext(),"Task is failed to update!",Toast.LENGTH_SHORT).show();
                            return;
                        }
                    }
                });
            }
        });

    }
}
