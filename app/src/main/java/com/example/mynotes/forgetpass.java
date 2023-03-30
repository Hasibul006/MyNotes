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
import com.google.firebase.auth.FirebaseAuth;

public class forgetpass extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_forgetpass);
        TextView forget = findViewById(R.id.button2);
        EditText mail = findViewById(R.id.user);
        FirebaseAuth auth = FirebaseAuth.getInstance();
        getSupportActionBar().hide();
        forget.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String email = mail.getText().toString().trim();
                if (TextUtils.isEmpty(email))
                {
                    Toast.makeText(forget.getContext(),"You must enter a valid mail!",Toast.LENGTH_SHORT).show();
                    return;
                }
                else
                {
                    auth.sendPasswordResetEmail(email).addOnCompleteListener(new OnCompleteListener<Void>() {
                        @Override
                        public void onComplete(@NonNull Task<Void> task) {
                            if(task.isSuccessful())
                            {
                                Toast.makeText(forget.getContext(),"A password reset mail is send!",Toast.LENGTH_SHORT).show();
                                startActivity(new Intent(forget.getContext(),Signin.class));
                                finish();
                            }
                            else
                            {
                                Toast.makeText(forget.getContext(),"Mail cannot be sent. Please try again!",Toast.LENGTH_SHORT).show();
                                return;
                            }
                        }
                    });
                }
            }
        });
    }
}