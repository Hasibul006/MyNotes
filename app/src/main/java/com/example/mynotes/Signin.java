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

public class Signin extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_signin);
        getSupportActionBar().hide();
        TextView forget = findViewById(R.id.forget),sign = findViewById(R.id.signup);
        TextView login = findViewById(R.id.login);
        FirebaseAuth auth = FirebaseAuth.getInstance();

        forget.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(forget.getContext(),forgetpass.class);
                startActivity(intent);
                finish();
            }
        });

        login.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                EditText user = findViewById(R.id.user),
                        pass = findViewById(R.id.pass);
                String mail = user.getText().toString().trim();
                String pas =  pass.getText().toString().trim();
                if(TextUtils.isEmpty(mail))
                {
                    Toast.makeText(login.getContext(),"Email is not inserted!",Toast.LENGTH_SHORT).show();
                    return;
                }
                else if(TextUtils.isEmpty(pas))
                {
                    Toast.makeText(login.getContext(),"Password is not inserted!",Toast.LENGTH_SHORT).show();
                    return;
                }
                else
                {
                    auth.signInWithEmailAndPassword(mail,pas).addOnCompleteListener(new OnCompleteListener<AuthResult>() {
                        @Override
                        public void onComplete(@NonNull Task<AuthResult> task) {
                            if(task.isSuccessful())
                            {
                                Toast.makeText(login.getContext(),"Log in succesfull!",Toast.LENGTH_SHORT).show();
                                startActivity(new Intent(login.getContext(),mother.class));
                                finish();
                            }
                            else
                            {
                                Toast.makeText(login.getContext(),"Log in unsuccesfull!",Toast.LENGTH_SHORT).show();
                                return;
                            }
                        }
                    });
                }

            }
        });

        sign.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(Signin.this,signup.class );
                startActivity(intent);
                finish();
            }
        });

    }
}