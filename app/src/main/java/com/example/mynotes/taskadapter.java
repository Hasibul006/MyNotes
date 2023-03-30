package com.example.mynotes;

import android.content.Intent;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CompoundButton;
import android.widget.PopupMenu;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;
import com.firebase.ui.database.FirebaseRecyclerAdapter;
import com.firebase.ui.database.FirebaseRecyclerOptions;
import com.firebase.ui.firestore.FirestoreRecyclerAdapter;
import com.firebase.ui.firestore.FirestoreRecyclerOptions;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import android.widget.CompoundButton.OnCheckedChangeListener;

import com.google.firebase.database.Query;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.FirebaseFirestore;

public class taskadapter extends FirebaseRecyclerAdapter<Target,taskholder> {

    /**
     * Initialize a {@link RecyclerView.Adapter} that listens to a Firebase query. See
     * {@link FirebaseRecyclerOptions} for configuration options.
     *
     * @param options
     */
    public String id;

    public taskadapter(@NonNull FirebaseRecyclerOptions<Target> options) {
        super(options);
    }

    @Override
    protected void onBindViewHolder(@NonNull taskholder holder, int position, @NonNull Target model) {
        holder.text.setText(model.getTodo());
        String s = model.getValue();
        String noteid = getSnapshots().getSnapshot(position).getKey();
        id = noteid;
        if(s.equals("0"))
        {
            holder.text.setChecked(false);
        }
        else
        {
            holder.text.setChecked(true);
        }

        holder.text.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {


                FirebaseUser user = FirebaseAuth.getInstance().getCurrentUser();
                DatabaseReference dref = FirebaseDatabase.getInstance().getReference().child("MyTask").child(user.getUid()).child(noteid).child("value");
                if(holder.text.isChecked())
                {
                    dref.setValue("1");
                    Toast.makeText(holder.text.getContext(), model.getTodo()+ " is completed!", Toast.LENGTH_SHORT).show();
                }
                else
                {
                    dref.setValue("0");
                    Toast.makeText(holder.text.getContext(), model.getTodo()+ " is not completed yet!", Toast.LENGTH_SHORT).show();
                }

            }
        });



        holder.text.setOnLongClickListener(new View.OnLongClickListener() {
            @Override
            public boolean onLongClick(View view) {
                PopupMenu popup = new PopupMenu(view.getContext(), view);
                popup.setGravity(Gravity.END);

                //Edit a note by clicking the popup "Edit" option
                popup.getMenu().add("Edit").setOnMenuItemClickListener(new MenuItem.OnMenuItemClickListener() {
                    @Override
                    public boolean onMenuItemClick(MenuItem menuItem) {
                        Intent i = new Intent(view.getContext(), EditToDo.class);
                        i.putExtra("title", model.getTodo());
                        i.putExtra("value", model.getValue());
                        i.putExtra("noteid", noteid);
                        view.getContext().startActivity(i);
                        return false;
                    }
                });

                popup.getMenu().add("Share").setOnMenuItemClickListener(new MenuItem.OnMenuItemClickListener() {
                    @Override
                    public boolean onMenuItemClick(MenuItem menuItem) {
                        Toast.makeText(view.getContext(), "share is pressed", Toast.LENGTH_SHORT).show();
                        return false;
                    }
                });

                //Delete a note by clicking the popup "Delete" option
                popup.getMenu().add("Delete").setOnMenuItemClickListener(new MenuItem.OnMenuItemClickListener() {
                    @Override
                    public boolean onMenuItemClick(MenuItem menuItem) {
                        FirebaseUser user = FirebaseAuth.getInstance().getCurrentUser();
                        DatabaseReference ref = FirebaseDatabase.getInstance().getReference().child("MyTask").child(user.getUid()).child(noteid);
                        ref.removeValue().addOnCompleteListener(new OnCompleteListener<Void>() {
                            @Override
                            public void onComplete(@NonNull Task<Void> task) {
                                if (task.isSuccessful()) {
                                    Toast.makeText(view.getContext(), "Task is deleted!", Toast.LENGTH_SHORT).show();
                                } else {
                                    Toast.makeText(view.getContext(), "Task deletion is failed!", Toast.LENGTH_SHORT).show();
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
    public taskholder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.taskchild,parent,false);
        return new taskholder(view);
    }


}
