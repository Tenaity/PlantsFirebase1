package com.midterm.plantsfirebase1;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.cepheuen.elegantnumberbutton.view.ElegantNumberButton;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.midterm.plantsfirebase1.Model.Plant;

public class PlantDetail extends AppCompatActivity {
    TextView mTvNamePlantDetail,mTvPricePlantDetail;
    ImageView mImagePlantDetail;
    Button mBtnBuy;
    ElegantNumberButton mBtnCount;
    String plantId = "";
    FirebaseDatabase database;
    DatabaseReference plant;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_plant_detail);

        database = FirebaseDatabase.getInstance();
        plant = database.getReference("Plant");

        //Init View
        mTvNamePlantDetail = findViewById(R.id.tv_plantName_detail);
        mTvPricePlantDetail = findViewById(R.id.tv_plantPrice_detail);
        mImagePlantDetail = findViewById(R.id.iv_plantImage_detail);
        mBtnBuy = findViewById(R.id.btn_buy);
        mBtnCount = findViewById(R.id.btn_count);

        if(getIntent() != null ){
            plantId = getIntent().getStringExtra("PlantId");
        }
        if(!plantId.isEmpty()){
            getDetailPlant(plantId);
        }
    }

    private void getDetailPlant(String plantId) {
        plant.child(plantId).addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                Plant plant = snapshot.getValue(Plant.class);

                Glide.with(getBaseContext()).load(plant.getImage()).into(mImagePlantDetail);
                mTvNamePlantDetail.setText(plant.getName());
                mTvPricePlantDetail.setText(plant.getPrice());

            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });
    }
}