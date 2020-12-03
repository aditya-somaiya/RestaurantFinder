package com.example.restaurantfinder;

import androidx.appcompat.app.AppCompatActivity;
import androidx.cardview.widget.CardView;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.location.Location;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Toast;

import com.google.android.gms.location.FusedLocationProviderClient;
import com.google.android.gms.location.LocationServices;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.tasks.OnSuccessListener;

import java.util.ArrayList;

public class TravelDistance extends AppCompatActivity {

    private CardView currntLocationBtn, chooseLocationBtn;
    private Double wayLatitude = 0.0, wayLongitude = 0.0;
    private FusedLocationProviderClient fusedLocationClient;
    ArrayList<String> chosen = new ArrayList<>();
    Integer radius;

    @SuppressLint("MissingPermission")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_travel_distance);

        Bundle bundle = getIntent().getExtras();
        chosen = bundle.getStringArrayList("chosenFoods");
        radius = bundle.getInt("radius");


        fusedLocationClient = LocationServices.getFusedLocationProviderClient(this);

        currntLocationBtn = (CardView) findViewById(R.id.currentLocationBtn);
        currntLocationBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                openCurrentLocation();
            }
        });

        chooseLocationBtn = (CardView) findViewById(R.id.searchLocationBtn);
        chooseLocationBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                openFindLocation();
            }
        });
    }


    @SuppressLint("MissingPermission")
    public void openCurrentLocation() {

        fusedLocationClient.getLastLocation().addOnSuccessListener(this, new OnSuccessListener<Location>() {
            @Override
            public void onSuccess(Location location) {
                if (location != null) {
                    Intent intent = new Intent(TravelDistance.this, Loading.class);
                    Bundle extras = new Bundle();
                    wayLatitude = location.getLatitude();
                    wayLongitude = location.getLongitude();
                    extras.putDouble("LocationLat", wayLatitude);
                    extras.putDouble("LocationLng", wayLongitude);
                    extras.putStringArrayList("chosenFoods", chosen);
                    extras.putInt("radius", radius);
                    intent.putExtras(extras);
                    TravelDistance.this.startActivity(intent);
                }
            }
        });


    }
    public void openFindLocation() {
        Intent intent = new Intent(this, FindLocation.class);
        Bundle extras = new Bundle();
        extras.putStringArrayList("chosenFoods", chosen);
        extras.putInt("radius", radius);
        intent.putExtras(extras);
        startActivity(intent);
    }
}
