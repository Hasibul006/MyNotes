package com.example.mynotes;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.speech.tts.TextToSpeech;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.TextView;

import com.google.android.material.floatingactionbutton.FloatingActionButton;

import java.util.Locale;

public class content extends AppCompatActivity {
    String textnote;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_content);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);



        Intent intent = getIntent();
        Bundle bundle = intent.getExtras();
        String t = bundle.getString("title");
        String n = bundle.getString("notes");
        textnote = bundle.getString("notes");
        String ti = bundle.getString("time");
        String d = bundle.getString("date");
        String i = bundle.getString("noteid");
        TextView title = findViewById(R.id.title),
                content = findViewById(R.id.content);
        title.setText(t);
        content.setText(n);

        FloatingActionButton edit = findViewById(R.id.save);
        edit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent1 = new Intent(edit.getContext(),EditNotes.class);
                intent1.putExtra("title",t);
                intent1.putExtra("notes",n);
                intent1.putExtra("time",ti);
                intent1.putExtra("date",d);
                intent1.putExtra("noteid",i);
                startActivity(intent1);
                finish();
            }
        });

    }


    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        switch (item.getItemId())
        {
            case android.R.id.home:
                startActivity(new Intent(content.this,mother.class));
                finish();
                break;
        }
        return super.onOptionsItemSelected(item);
    }
}