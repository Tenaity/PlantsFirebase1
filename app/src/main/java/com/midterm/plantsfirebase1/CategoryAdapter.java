package com.midterm.plantsfirebase1;

import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.firebase.ui.database.FirebaseRecyclerAdapter;
import com.firebase.ui.database.FirebaseRecyclerOptions;
import com.midterm.plantsfirebase1.Model.Category;

public class CategoryAdapter extends FirebaseRecyclerAdapter<Category,CategoryAdapter.ViewHolder>{

    public CategoryAdapter(@NonNull FirebaseRecyclerOptions<Category> options) {
        super(options);
    }

    @Override
    protected void onBindViewHolder(@NonNull ViewHolder holder, int position, @NonNull Category model) {
        holder.mName.setText(model.getName());
        Glide.with(holder.mImage.getContext()).load(model.getImage()).into(holder.mImage);
//        final Category clickItem = model;
//        holder.itemView.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View v) {
//                Toast.makeText(Home.this, " "+clickItem.getName(), Toast.LENGTH_SHORT).show();
//            }
//        });
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.menu_item,parent,false);
        return new ViewHolder(view);
    }


    public class ViewHolder extends RecyclerView.ViewHolder{
        TextView mName;
        ImageView mImage;
        View view;
        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            view = itemView;
            mName = view.findViewById(R.id.tv_menu_item);
            mImage = view.findViewById(R.id.iv_menu_item);
        }
    }

}