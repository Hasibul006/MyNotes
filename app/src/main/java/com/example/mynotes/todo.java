package com.example.mynotes;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import androidx.recyclerview.widget.StaggeredGridLayoutManager;

import android.content.Intent;
import android.os.Bundle;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.PopupMenu;
import android.widget.SearchView;
import android.widget.Toast;

import com.firebase.ui.database.FirebaseRecyclerAdapter;
import com.firebase.ui.database.FirebaseRecyclerOptions;
import com.firebase.ui.firestore.FirestoreRecyclerAdapter;
import com.firebase.ui.firestore.FirestoreRecyclerOptions;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.Query;

public class todo extends AppCompatActivity {

    RecyclerView r;
    taskadapter tadapter;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_todo);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setTitle("MyTasks");

        FirebaseUser user = FirebaseAuth.getInstance().getCurrentUser();
        FirebaseFirestore store = FirebaseFirestore.getInstance();
        DatabaseReference reference = FirebaseDatabase.getInstance().getReference().child("MyTask").child(user.getUid());
        com.google.firebase.database.Query query = reference.orderByChild("value");
        FirebaseRecyclerOptions<Target> options = new FirebaseRecyclerOptions.Builder<Target>().setQuery(query,Target.class).build();
        tadapter = new taskadapter(options);
        r = findViewById(R.id.recycle);
        r.setLayoutManager(new LinearLayoutManager(this));
        r.setAdapter(tadapter);





        //add new notes and update it to database
        FloatingActionButton add = findViewById(R.id.add);
        add.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(add.getContext(),addTask.class));
            }
        });
    }


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu2,menu);
        return super.onCreateOptionsMenu(menu);
    }

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        FirebaseAuth auth = FirebaseAuth.getInstance();
        switch(item.getItemId())
        {
            case R.id.Log:
                auth.signOut();
                startActivity(new Intent(todo.this,Signin.class));
                finish();
                break;
            case R.id.profile:
                String e = FirebaseAuth.getInstance().getCurrentUser().getEmail();
                Toast.makeText(todo.this,"These are the notes of\n"+e,Toast.LENGTH_LONG).show();
                break;
            case R.id.about:
                Toast.makeText(this,"This android application is developed by:\nHasibul Islam\nDepartment of CSE,KUET\nID:1907006",Toast.LENGTH_LONG).show();
                break;
            case R.id.parentnotes:
            case android.R.id.home:
                startActivity(new Intent(todo.this,mother.class));
                finish();
                break;
            default:
                String s = item.toString();
                Toast.makeText(this,s,Toast.LENGTH_SHORT).show();
                break;
        }
        return super.onOptionsItemSelected(item);
    }



    @Override
    protected void onStart() {
        tadapter.startListening();
        super.onStart();
    }

    @Override
    protected void onResume() {
        tadapter.startListening();
        super.onResume();
    }


}