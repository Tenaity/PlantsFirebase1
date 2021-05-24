package com.midterm.plantsfirebase1;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.firebase.ui.database.FirebaseRecyclerAdapter;
import com.firebase.ui.database.FirebaseRecyclerOptions;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.midterm.plantsfirebase1.Commom.Commom;
import com.midterm.plantsfirebase1.Model.Category;
import com.midterm.plantsfirebase1.Model.Order;
import com.midterm.plantsfirebase1.Model.Request;
import com.midterm.plantsfirebase1.ViewHolder.OrderViewHolder;
import com.midterm.plantsfirebase1.ViewHolder.PlantViewHolder;

public class OrderStatus extends AppCompatActivity {

    RecyclerView mRvOrder;
    FirebaseRecyclerAdapter<Request, OrderViewHolder> mRequestAdapter;
    String phoneNum = "";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_order_status);


        mRvOrder = findViewById(R.id.recycler_orderStatus);
        mRvOrder.setLayoutManager(new LinearLayoutManager(this));

        phoneNum = Commom.currentUser.getPhone();


        FirebaseRecyclerOptions<Request> options =
                new FirebaseRecyclerOptions.Builder<Request>()
                        .setQuery(FirebaseDatabase.getInstance().getReference().child("request").orderByChild("phone").equalTo(phoneNum), Request.class)
                        .build();

        mRequestAdapter = new FirebaseRecyclerAdapter<Request, OrderViewHolder>(options) {
            @Override
            protected void onBindViewHolder(@NonNull OrderViewHolder holder, int position, @NonNull Request model) {
                //holder.mTvOrderId.setText(mRequestAdapter.getRef(position).getKey());
                holder.mTvOrderPhone.setText(model.getPhone());
                holder.mTvOrderAddress.setText(model.getAddress());
                holder.mTvOrderStatus.setText(convertCodeToStatus(model.getStatus()));
                holder.mTvOrderPrice.setText(model.getTotal());
            }

            @NonNull
            @Override
            public OrderViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
                View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.order_status_item, parent, false);
                return new OrderViewHolder(view);
            }
        };
        mRvOrder.setAdapter(mRequestAdapter);

//        Log.d("DEBUG",""+phoneNum);
//        Log.d("DEBUG",""+mRequestAdapter.getItemCount());
    }

    @Override
    public void onStart() {
        super.onStart();
        mRequestAdapter.startListening();
    }

    @Override
    public void onStop() {
        super.onStop();
        mRequestAdapter.stopListening();
    }

    private String convertCodeToStatus(String status) {
        if(status.equals("")) return "0";
        if(status.equals("0")){
            return "Đã đặt";
        }
        else if(status.equals("1")){
            return "Đang vận chuyển";
        }
        else{
            return "Đã giao hàng";
        }
    }
}