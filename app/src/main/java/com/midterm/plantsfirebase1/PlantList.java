package com.midterm.plantsfirebase1;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import com.bumptech.glide.Glide;
import com.firebase.ui.database.FirebaseRecyclerAdapter;
import com.firebase.ui.database.FirebaseRecyclerOptions;
import com.google.firebase.database.FirebaseDatabase;
import com.midterm.plantsfirebase1.Interface.ItemClickListener;
import com.midterm.plantsfirebase1.Model.Plant;
import com.midterm.plantsfirebase1.ViewHolder.PlantViewHolder;

public class PlantList extends AppCompatActivity {
    RecyclerView mRvPlant;
    FirebaseRecyclerAdapter<Plant,PlantViewHolder> adapterPlant;
    String categoryId = "";


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_plant_list);


        mRvPlant = (RecyclerView)findViewById(R.id.recyler_plants);
        mRvPlant.setLayoutManager(new LinearLayoutManager(this));

        //Get intent here
        if(getIntent() != null){
            categoryId = getIntent().getStringExtra("CategoryId");
        }
        if(!categoryId.isEmpty() && categoryId != null) {

            FirebaseRecyclerOptions<Plant> option =
                    new FirebaseRecyclerOptions.Builder<Plant>()
                            .setQuery(FirebaseDatabase.getInstance().getReference().child("Plant").orderByChild("categoryId").equalTo(categoryId), Plant.class)
                            .build();

            adapterPlant = new FirebaseRecyclerAdapter<Plant, PlantViewHolder>(option) {
                @Override
                protected void onBindViewHolder(@NonNull PlantViewHolder holder, int position, @NonNull Plant model) {
                    holder.tv_NamePlant.setText(model.getName());
                    holder.tv_PricePlant.setText(model.getPrice());
                    Glide.with(holder.imagePlant).load(model.getImage()).into(holder.imagePlant);

                    final Plant itemClickPlant = model;
                    holder.setItemClickListener(new ItemClickListener() {
                        @Override
                        public void onClick(View view, int position, boolean isLongClick) {
                            //Start new Activity
                            Intent plantDetail = new Intent(PlantList.this, PlantDetail.class);
                            plantDetail.putExtra("PlantId", adapterPlant.getRef(position).getKey());
                            startActivity(plantDetail);
                        }
                    });
                }

                @NonNull
                @Override
                public PlantViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
                    View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.plant_item, parent, false);
                    return new PlantViewHolder(view);
                }
            };

            mRvPlant.setAdapter(adapterPlant);
        }
    }
    @Override
    public void onStart() {
        super.onStart();
        adapterPlant.startListening();
    }

    @Override
    public void onStop() {
        super.onStop();
        adapterPlant.stopListening();
    }
}