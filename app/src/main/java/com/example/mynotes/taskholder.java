package com.example.mynotes;

import android.view.View;
import android.widget.CheckBox;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

public class taskholder extends RecyclerView.ViewHolder {
    CheckBox text;
    public taskholder(@NonNull View itemView) {
        super(itemView);
        text = itemView.findViewById(R.id.titl);
    }
}
