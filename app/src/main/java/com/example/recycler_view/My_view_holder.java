package com.example.recycler_view;

import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

public class My_view_holder extends RecyclerView.ViewHolder
{
    ImageView image;
    TextView name,contact;

    LinearLayout linear;

    public My_view_holder(@NonNull View itemView)
    {
        super(itemView);
        image=itemView.findViewById(R.id.image);
        name=itemView.findViewById(R.id.name);
        contact=itemView.findViewById(R.id.contact);
        linear= itemView.findViewById(R.id.linear);
    }
}
