package com.midterm.plantsfirebase1.ViewHolder;

import android.media.Image;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.midterm.plantsfirebase1.Interface.ItemClickListener;
import com.midterm.plantsfirebase1.R;


public class MenuViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {

    public TextView tv_NameMenu;
    public ImageView imageMenu;
    private ItemClickListener itemClickListener;

    public MenuViewHolder(@NonNull View itemView) {
        super(itemView);
        tv_NameMenu = itemView.findViewById(R.id.tv_menu_item);
        imageMenu = itemView.findViewById(R.id.iv_menu_item);

        itemView.setOnClickListener(this);
    }

    public void setItemClickListener(ItemClickListener itemClickListener) {
        this.itemClickListener = itemClickListener;
    }

    @Override
    public void onClick(View v) {
        itemClickListener.onClick(v,getAdapterPosition(),false);
    }
}
