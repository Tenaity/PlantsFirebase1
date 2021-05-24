package com.midterm.plantsfirebase1.ViewHolder;

import android.view.View;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.midterm.plantsfirebase1.Interface.ItemClickListener;
import com.midterm.plantsfirebase1.R;

public class OrderViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {

    public TextView mTvOrderId, mTvOrderStatus, mTvOrderPhone, mTvOrderAddress, mTvOrderPrice;

    private ItemClickListener itemClickListener;

    public OrderViewHolder(@NonNull View itemView) {
        super(itemView);

        mTvOrderId = itemView.findViewById(R.id.tv_orderId);
        mTvOrderStatus = itemView.findViewById(R.id.tv_orderStatus);
        mTvOrderPhone = itemView.findViewById(R.id.tv_orderPhone);
        mTvOrderAddress = itemView.findViewById(R.id.tv_orderAddress);
        mTvOrderPrice = itemView.findViewById(R.id.tv_orderPrice);
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
