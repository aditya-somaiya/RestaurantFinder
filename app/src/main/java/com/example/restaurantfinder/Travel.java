package com.example.restaurantfinder;

import androidx.appcompat.app.AppCompatActivity;
import androidx.cardview.widget.CardView;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;

import java.util.ArrayList;

public class Travel extends AppCompatActivity {

    private CardView walkBtn, driveBtn, transitBtn, bikeBtn;
    Integer radius = 1000;
    ArrayList<String> chosen = new ArrayList<>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_travel);

        Bundle bundle = getIntent().getExtras();
        chosen = bundle.getStringArrayList("chosenFoods");


        walkBtn = (CardView) findViewById(R.id.walk_btn);
        walkBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                radius = 1000;
                openTravelDistance();
            }
        });

        driveBtn = (CardView) findViewById(R.id.drive_btn);
        driveBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                radius = 10000;
                openTravelDistance();
            }
        });

        transitBtn = (CardView) findViewById(R.id.transit_btn);
        transitBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                radius = 10000;
                openTravelDistance();
            }
        });

        bikeBtn = (CardView) findViewById(R.id.bike_btn);
        bikeBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                radius = 5000;
                openTravelDistance();
            }
        });
    }

    public void openTravelDistance() {
        Intent intent = new Intent(this, TravelDistance.class);
        Bundle extras = new Bundle();
        extras.putStringArrayList("chosenFoods",  chosen);
        extras.putInt("radius",  radius);
        intent.putExtras(extras);
        startActivity(intent);
    }
}
