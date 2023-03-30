package com.example.mynotes;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.RecyclerView;
import androidx.recyclerview.widget.StaggeredGridLayoutManager;

import android.content.Intent;
import android.os.Bundle;
import android.renderscript.ScriptGroup;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Adapter;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.PopupMenu;
import android.widget.SearchView;
import android.widget.Switch;
import android.widget.Toast;

import com.firebase.ui.firestore.FirestoreRecyclerAdapter;
import com.firebase.ui.firestore.FirestoreRecyclerOptions;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.android.material.bottomnavigation.BottomNavigationItemView;
import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.Query;

import java.util.ArrayList;
import java.util.Random;

import javax.net.ssl.SSLSessionBindingEvent;

public class mother extends AppCompatActivity {

    RecyclerView r;
    FirestoreRecyclerAdapter<Notes,Viewholder>adapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_mother);

        FirebaseUser user = FirebaseAuth.getInstance().getCurrentUser();
        FirebaseFirestore store = FirebaseFirestore.getInstance();

        Query query = store.collection("MyNotes").document(user.getUid()).collection("Notes").orderBy("date",Query.Direction.DESCENDING);

        FirestoreRecyclerOptions<Notes> allcontents = new FirestoreRecyclerOptions.Builder<Notes>().setQuery(query,Notes.class).build();

            adapter = new FirestoreRecyclerAdapter<Notes, Viewholder>(allcontents) {
            @Override
            protected void onBindViewHolder(@NonNull Viewholder holder, int position, @NonNull Notes model) {
                int color = new getcolour().getrandomcolour();
                holder.n.setBackgroundColor(holder.itemView.getResources().getColor(color,null));
                holder.title.setText(model.getTitle());
                holder.time.setText(model.getTime());
                holder.date.setText(model.getDate());

                String noteid = adapter.getSnapshots().getSnapshot(position).getId();

                //see the note detail by clicking on it
                holder.itemView.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        Intent i = new Intent(mother.this,content.class);
                        i.putExtra("title",model.getTitle());
                        i.putExtra("notes",model.getContents());
                        i.putExtra("time",model.getTime());
                        i.putExtra("date",model.getDate());
                        i.putExtra("noteid",noteid);
                        startActivity(i);
                    }
                });

                //find the popupmenu by a long press on the note
                holder.itemView.setOnLongClickListener(new View.OnLongClickListener() {
                    @Override
                    public boolean onLongClick(View view) {
                        PopupMenu popup = new PopupMenu(view.getContext(),view);
                        popup.setGravity(Gravity.END);

                        //Edit a note by clicking the popup "Edit" option
                        popup.getMenu().add("Edit").setOnMenuItemClickListener(new MenuItem.OnMenuItemClickListener() {
                            @Override
                            public boolean onMenuItemClick(MenuItem menuItem) {
                                Intent i = new Intent(mother.this,EditNotes.class);
                                i.putExtra("title",model.getTitle());
                                i.putExtra("notes",model.getContents());
                                i.putExtra("time",model.getTime());
                                i.putExtra("date",model.getDate());
                                i.putExtra("noteid",noteid);
                                startActivity(i);
                                return false;
                            }
                        });

                        popup.getMenu().add("Share").setOnMenuItemClickListener(new MenuItem.OnMenuItemClickListener() {
                            @Override
                            public boolean onMenuItemClick(MenuItem menuItem) {
                                Toast.makeText(view.getContext(),"share is pressed",Toast.LENGTH_SHORT).show();
                                return false;
                            }
                        });

                        //Delete a note by clicking the popup "Delete" option
                        popup.getMenu().add("Delete").setOnMenuItemClickListener(new MenuItem.OnMenuItemClickListener() {
                            @Override
                            public boolean onMenuItemClick(MenuItem menuItem) {

                                DocumentReference ref = FirebaseFirestore.getInstance().collection("MyNotes").document(user.getUid()).collection("Notes").document(noteid);
                                ref.delete().addOnCompleteListener(new OnCompleteListener<Void>() {
                                    @Override
                                    public void onComplete(@NonNull Task<Void> task) {
                                        if(task.isSuccessful())
                                        {
                                            Toast.makeText(view.getContext(),"Note is deleted!",Toast.LENGTH_SHORT).show();
                                        }
                                        else
                                        {
                                            Toast.makeText(view.getContext(),"Note deletion is failed!",Toast.LENGTH_SHORT).show();
                                        }
                                    }
                                });
                                return false;
                            }
                        });
                        popup.show();
                        return true;
                    }
                });
            }

            @NonNull
            @Override
            public Viewholder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
                View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.child,parent,false);
                return new Viewholder(view);
            }
        };

        r = findViewById(R.id.recycle);
        r.setHasFixedSize(true);
        StaggeredGridLayoutManager stlayout = new StaggeredGridLayoutManager(2,StaggeredGridLayoutManager.VERTICAL);
        r.setLayoutManager(stlayout);
        r.setAdapter(adapter);


        //add new notes and update it to database
        FloatingActionButton add = findViewById(R.id.add);
        add.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(add.getContext(),addnotes.class));
            }
        });
    }





    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu,menu);
        return super.onCreateOptionsMenu(menu);
    }

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        FirebaseAuth auth = FirebaseAuth.getInstance();
        switch(item.getItemId())
        {
            case R.id.Log:
                auth.signOut();
                startActivity(new Intent(mother.this,Signin.class));
                finish();
                break;
            case  R.id.parenttask:
                startActivity(new Intent(mother.this,todo.class));
                finish();
                break;
            case R.id.search:
                SearchView searchView = (SearchView) item.getActionView();
                searchView.setOnQueryTextListener(new SearchView.OnQueryTextListener() {
                    @Override
                    public boolean onQueryTextSubmit(String s) {
                        searching(s);
                        return false;
                    }

                    @Override
                    public boolean onQueryTextChange(String s) {
                        searching(s);
                        return false;
                    }
                });
                break;
            case R.id.profile:
                String e = FirebaseAuth.getInstance().getCurrentUser().getEmail();
                Toast.makeText(mother.this,"These are the notes of\n"+e,Toast.LENGTH_LONG).show();
                break;
            case R.id.about:
                Toast.makeText(this,"This android application is developed by:\nHasibul Islam\nDepartment of CSE,KUET\nID:1907006",Toast.LENGTH_LONG).show();
                break;
            default:
                String s = item.toString();
                Toast.makeText(this,s,Toast.LENGTH_SHORT).show();
                break;
        }
        return super.onOptionsItemSelected(item);
    }

    private void  searching(String s)
    {
        FirebaseUser user = FirebaseAuth.getInstance().getCurrentUser();
        Query search = FirebaseFirestore.getInstance().collection("MyNotes").document(user.getUid()).collection("Notes").orderBy("title").startAt(s).endAt(s+"~");
        FirestoreRecyclerOptions<Notes> options = new FirestoreRecyclerOptions.Builder<Notes>().setQuery(search,Notes.class).build();
        adapter = new FirestoreRecyclerAdapter<Notes, Viewholder>(options) {
            @Override
            protected void onBindViewHolder(@NonNull Viewholder holder, int position, @NonNull Notes model) {
                int color = new getcolour().getrandomcolour();
                holder.n.setBackgroundColor(holder.itemView.getResources().getColor(color,null));
                holder.title.setText(model.getTitle());
                holder.time.setText(model.getTime());
                holder.date.setText(model.getDate());
                String noteid = adapter.getSnapshots().getSnapshot(position).getId();
                holder.itemView.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        Intent i = new Intent(mother.this,content.class);
                        i.putExtra("title",model.getTitle());
                        i.putExtra("notes",model.getContents());
                        i.putExtra("time",model.getTime());
                        i.putExtra("date",model.getDate());
                        i.putExtra("noteid",noteid);
                        startActivity(i);
                    }
                });
            }

            @NonNull
            @Override
            public Viewholder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
                View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.child,parent,false);
                return new Viewholder(view);
            }
        };
        adapter.startListening();
        r.setAdapter(adapter);

    }


    @Override
    protected void onStart() {
        adapter.startListening();
        super.onStart();
    }

    @Override
    protected void onResume() {
        adapter.startListening();
        super.onResume();
    }
}