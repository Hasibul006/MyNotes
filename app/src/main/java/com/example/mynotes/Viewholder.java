package com.example.mynotes;

import android.view.View;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

public class Viewholder extends RecyclerView.ViewHolder {

    TextView title,time,date;
    LinearLayout n;

    public Viewholder(@NonNull View itemView) {
        super(itemView);
        title = itemView.findViewById(R.id.titl);
        time = itemView.findViewById(R.id.time);
        date = itemView.findViewById(R.id.date);
        n = itemView.findViewById(R.id.not);
    }
}
