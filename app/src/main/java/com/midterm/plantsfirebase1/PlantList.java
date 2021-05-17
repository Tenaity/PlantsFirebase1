package com.midterm.plantsfirebase1;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.GridLayoutManager;
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
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.midterm.plantsfirebase1.Commom.Commom;
import com.midterm.plantsfirebase1.Database.Database;
import com.midterm.plantsfirebase1.Interface.ItemClickListener;
import com.midterm.plantsfirebase1.Model.Favorites;
import com.midterm.plantsfirebase1.Model.Plant;
import com.midterm.plantsfirebase1.ViewHolder.PlantViewHolder;

public class PlantList extends AppCompatActivity {
    RecyclerView mRvPlant;
    FirebaseRecyclerAdapter<Plant, PlantViewHolder> adapterPlant;
    String categoryId = "";
    FirebaseDatabase database;
    DatabaseReference favorite;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_plant_list);

        database = FirebaseDatabase.getInstance();
        favorite = database.getReference("Favorites");

        mRvPlant = (RecyclerView) findViewById(R.id.recyler_plants);
        mRvPlant.setLayoutManager(new GridLayoutManager(this, 2));

        //Get intent here
        if (getIntent() != null) {
            categoryId = getIntent().getStringExtra("CategoryId");
        }
        if (!categoryId.isEmpty() && categoryId != null) {

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

                    //add Favorites
                    String key = Commom.currentUser.getPhone() + adapterPlant.getRef(position).getKey();
                    favorite.child(key).addValueEventListener(new ValueEventListener() {
                        @Override
                        public void onDataChange(@NonNull DataSnapshot snapshot) {
                            if (snapshot.exists()) {
//                                Toast.makeText(PlantList.this,"Zo roi ne ma oiii",Toast.LENGTH_LONG).show();
                                holder.imageFav.setImageResource(R.drawable.ic_baseline_favorite_24);

                            } else {

                            }
                        }

                        @Override
                        public void onCancelled(@NonNull DatabaseError error) {

                        }
                    });
                    // click to add favorites

                    holder.imageFav.setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View v) {
                            String key = Commom.currentUser.getPhone() + adapterPlant.getRef(position).getKey();

                            favorite.child(key).addListenerForSingleValueEvent(new ValueEventListener() {
                                @Override
                                public void onDataChange(@NonNull DataSnapshot snapshot) {
                                    if (!snapshot.exists()) {
                                        Favorites fav = new Favorites(Commom.currentUser.getPhone(), adapterPlant.getRef(position).getKey());
                                        favorite.child(key).setValue(fav);
                                        holder.imageFav.setImageResource(R.drawable.ic_baseline_favorite_24);
//                                        Toast.makeText(PlantList.this, "" + model.getName() + " was added to Favorites", Toast.LENGTH_LONG).show();

                                    } else {
                                        favorite.child(key).removeValue();
                                        holder.imageFav.setImageResource(R.drawable.ic_baseline_favorite_border_24);
                                        //Toast.makeText(PlantList.this, "" + model.getName() + " was removed from Favorites", Toast.LENGTH_LONG).show();

                                    }
                                }

                                @Override
                                public void onCancelled(@NonNull DatabaseError error) {

                                }
                            });
                        }
                    });

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