package com.midterm.plantsfirebase1;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.RatingBar;
import android.widget.TextView;
import android.widget.Toast;

import com.bumptech.glide.Glide;
import com.cepheuen.elegantnumberbutton.view.ElegantNumberButton;
import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.Query;
import com.google.firebase.database.ValueEventListener;
import com.midterm.plantsfirebase1.Commom.Commom;
import com.midterm.plantsfirebase1.Database.Database;
import com.midterm.plantsfirebase1.Model.Order;
import com.midterm.plantsfirebase1.Model.Plant;
import com.midterm.plantsfirebase1.Model.Rating;
import com.stepstone.apprating.AppRatingDialog;
import com.stepstone.apprating.listener.RatingDialogListener;

import org.jetbrains.annotations.NotNull;

import java.util.Arrays;
import java.util.Random;

public class PlantDetail extends AppCompatActivity implements RatingDialogListener {
    TextView mTvNamePlantDetail,mTvPricePlantDetail;
    ImageView mImagePlantDetail;
    Button mBtnBuy;
    ElegantNumberButton mBtnCount;
    String plantId = "";
    FirebaseDatabase database;
    DatabaseReference plant;
    DatabaseReference ratingTbl;
    Plant currentPlant;
    FloatingActionButton btn_rating;
    RatingBar ratingBar;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_plant_detail);

        database = FirebaseDatabase.getInstance();
        plant = database.getReference("Plant");
        ratingTbl = database.getReference("Rating");

        //Init View
        mTvNamePlantDetail = findViewById(R.id.tv_plantName_detail);
        mTvPricePlantDetail = findViewById(R.id.tv_plantPrice_detail);
        mImagePlantDetail = findViewById(R.id.iv_plantImage_detail);
        mBtnBuy = findViewById(R.id.btn_buy);
        mBtnCount = findViewById(R.id.btn_count);
        btn_rating = (FloatingActionButton) findViewById(R.id.btn_rating);
        ratingBar = (RatingBar) findViewById(R.id.ratingBar);

        //On click Rating Button

        btn_rating.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                showRatingDialog();
            }

        });

        mBtnBuy.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                new Database(PlantDetail.this).addToCart(new Order(
                        plantId,
                        currentPlant.getName(),
                        mBtnCount.getNumber(),
                        currentPlant.getPrice()
                ));
                Toast.makeText(PlantDetail.this, "Added to Cart", Toast.LENGTH_SHORT).show();
            }
        });

        if(getIntent() != null ){
            plantId = getIntent().getStringExtra("PlantId");
        }
        if(!plantId.isEmpty()){
            getDetailPlant(plantId);
            getRatingPlant(plantId);
        }
    }

    private void getRatingPlant(String plantId) {
        Query plantRating = ratingTbl.orderByChild("plantId").equalTo(plantId);
        plantRating.addValueEventListener(new ValueEventListener() {
            int count = 0 ,sum =0;
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                for (DataSnapshot postSnapshot:snapshot.getChildren()){
                    Rating item = postSnapshot.getValue(Rating.class);
                    sum+= Integer.parseInt(item.getRateValue());
                    count++;
                }
                if (count!=0){
                    float average = sum/count;
                    ratingBar.setRating(average);
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });
    }

    private void showRatingDialog() {
//        Toast.makeText(PlantDetail.this,"zo day roi nef",Toast.LENGTH_LONG).show();
        new AppRatingDialog.Builder()
                .setPositiveButtonText("Submit")
                .setNegativeButtonText("Cancel")
                .setNoteDescriptions(Arrays.asList("Very Bad","Not Good","Quite OK","Very Good","Excellent" ))
                .setDefaultRating(1)
                .setTitle("Rate this plant")
                .setDescription("Please select some stars and give your feedback")
                .setHint("Please write your comment here... ")
                .setWindowAnimation(R.style.RatingDialogFadeAnim)
                .create(PlantDetail.this)
                .show();
    }

    private void getDetailPlant(String plantId) {
        plant.child(plantId).addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                currentPlant = snapshot.getValue(Plant.class);

                Glide.with(getBaseContext()).load(currentPlant.getImage()).into(mImagePlantDetail);
                mTvNamePlantDetail.setText(currentPlant.getName());
                mTvPricePlantDetail.setText(currentPlant.getPrice());

            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });
    }

    @Override
    public void onNegativeButtonClicked() {

    }

    @Override
    public void onPositiveButtonClicked(int value, String comment) {
        //get Rating and update Firebase
        Rating rating = new Rating(Commom.currentUser.getPhone(),plantId,String.valueOf(value),comment);
        //id random
        String id = Commom.currentUser.getPhone()+plantId;
        ratingTbl.child(id).addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                if (snapshot.child(id).exists()){
                    //remove old value
                    ratingTbl.child(id).removeValue();
                    //update value
                    ratingTbl.child(id).setValue(rating);
                }
                else {
                    ratingTbl.child(id).setValue(rating);
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });

    }
}