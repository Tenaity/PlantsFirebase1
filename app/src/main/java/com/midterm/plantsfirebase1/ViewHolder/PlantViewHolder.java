package com.midterm.plantsfirebase1.ViewHolder;

import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.midterm.plantsfirebase1.Interface.ItemClickListener;
import com.midterm.plantsfirebase1.R;

public class PlantViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {

    public TextView tv_NamePlant,tv_PricePlant;
    public ImageView imagePlant,imageFav;
    private ItemClickListener itemClickListener;

    public PlantViewHolder(@NonNull View itemView) {
        super(itemView);
        tv_NamePlant = itemView.findViewById(R.id.tv_plantName_item);
        tv_PricePlant = itemView.findViewById(R.id.tv_plantPrice_item);
        imagePlant = itemView.findViewById(R.id.iv_plantImage_item);
        imageFav = itemView.findViewById(R.id.im_fav);
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
