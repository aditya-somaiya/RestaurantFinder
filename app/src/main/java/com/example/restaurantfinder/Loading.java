package com.example.restaurantfinder;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.util.Log;
import android.view.View;
import android.widget.Toast;

import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.MarkerOptions;

import java.util.ArrayList;

public class Loading extends AppCompatActivity {

    Double lat, lng;
    Intent intent;
    GetNearbyPlaces getNearbyPlaces;
    ArrayList<String> chosenFoods = new ArrayList<>();
    String result = "";
    Integer radius = 1000;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_loading);

        Bundle bundle = getIntent().getExtras();
        lat = bundle.getDouble("LocationLat");
        lng = bundle.getDouble("LocationLng");
        chosenFoods = bundle.getStringArrayList("chosenFoods");
        radius = bundle.getInt("radius");

        findRestaurants(getWindow().getDecorView().findViewById(android.R.id.content));
    }

    public void getKeywords(){

        for (int i = 0; i < chosenFoods.size(); i++){
            result = result + chosenFoods.get(i) + ",";
        }
        Log.i("CHOSEN2", result);

    }


    public void findRestaurants(View v) {
        getKeywords();
        StringBuilder stringBuilder = new StringBuilder("https://maps.googleapis.com/maps/api/place/nearbysearch/json?");
        stringBuilder.append("location=" + lat + "," + lng);
        stringBuilder.append("&radius=" + radius);
        stringBuilder.append("&type=" + "restaurant");
        stringBuilder.append("&keyword=" + result);
        stringBuilder.append("&key=" + getResources().getString(R.string.google_maps_api));
        String url = stringBuilder.toString();

        Log.i("url", url);

        Object[] dataTransfer = new Object[1];
        dataTransfer[0] = url;

        getNearbyPlaces = new GetNearbyPlaces();
        getNearbyPlaces.execute(dataTransfer);

        new Handler().postDelayed(new Runnable() {
            @Override
            public void run() {

                if (getNearbyPlaces.getName_restaurant() == null || getNearbyPlaces.getPlaceid() == null) {
                    Toast.makeText(Loading.this, "Unable to find a location :( Maybe try changing your previous selections", Toast.LENGTH_LONG).show();
                    intent = new Intent(Loading.this, MainActivity.class);
                    startActivity(intent);

                }else{

                    intent = new Intent(Loading.this, FinalRestaurant.class);
                    Bundle extras = new Bundle();
                    extras.putString("restaurant_name", getNearbyPlaces.getName_restaurant());
                    extras.putString("image", getNearbyPlaces.getImage());
                    extras.putDouble("rating", getNearbyPlaces.getRating());
                    extras.putString("placeid",getNearbyPlaces.getPlaceid());
                    extras.putDouble("lat", getNearbyPlaces.getLat());
                    extras.putDouble("lng", getNearbyPlaces.getLng());
                    intent.putExtras(extras);
                    startActivity(intent);

                }
                //finish();
            }
        }, 2000);
    }
}
