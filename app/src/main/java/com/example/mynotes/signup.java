package com.example.mynotes;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;

public class signup extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_signup);
        getSupportActionBar().hide();
        EditText user = findViewById(R.id.user),pass1= findViewById(R.id.pass),pass2= findViewById(R.id.pass2);
        TextView sign = findViewById(R.id.button);
        FirebaseAuth auth = FirebaseAuth.getInstance();

        sign.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String name = user.getText().toString().trim();
                String p1 = pass1.getText().toString().trim();
                String p2 = pass2.getText().toString().trim();
                Toast.makeText(sign.getContext(),"Signup is sucesfull!",Toast.LENGTH_SHORT).show();

                if(TextUtils.isEmpty(name))
                {
                    Toast.makeText(sign.getContext(),"You must enter your mail!",Toast.LENGTH_SHORT).show();
                    return;
                }
                else if(TextUtils.isEmpty(p1) || TextUtils.isEmpty(p2) )
                {
                    Toast.makeText(sign.getContext(),"You must enter your password!",Toast.LENGTH_SHORT).show();
                    return;
                }

                else if(!p1.equals(p2))
                {
                    Toast.makeText(sign.getContext(),"Passwords are not matched!",Toast.LENGTH_SHORT).show();
                    return;
                }
                else if(p1.length()<6)
                {
                    Toast.makeText(sign.getContext(),"Passwords must be at least 6 character!",Toast.LENGTH_SHORT).show();
                    return;
                }
                else
                {
                    auth.createUserWithEmailAndPassword(name,p1).addOnCompleteListener(new OnCompleteListener<AuthResult>() {
                        @Override
                        public void onComplete(@NonNull Task<AuthResult> task) {
                            if(task.isSuccessful())
                            {
                                Toast.makeText(sign.getContext(),"New account is created!",Toast.LENGTH_SHORT).show();
                                startActivity(new Intent(sign.getContext(),mother.class));
                                finish();
                            }
                            else
                            {
                                Toast.makeText(sign.getContext(),"Account cannot be created due to network error!",Toast.LENGTH_SHORT).show();
                                return;
                            }
                        }
                    });
                }

            }
        });
    }
}