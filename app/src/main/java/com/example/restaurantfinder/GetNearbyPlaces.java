package com.example.restaurantfinder;

import android.content.Context;
import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.util.Log;
import android.widget.Toast;

import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.MarkerOptions;
import com.google.gson.JsonObject;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;
import org.json.JSONStringer;


import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;

public class GetNearbyPlaces extends AsyncTask<Object, String, String> {

    GoogleMap mMap;
    String url, data;
    InputStream is;
    BufferedReader bufferedReader;
    StringBuilder stringBuilder;

    public String name_restaurant;
    public String image;
    public String placeid;
    public Double rating = 0.0 ,lat = 0.0, lng = 0.0;

    private Intent intent;


    @Override
    protected String doInBackground(Object... params) {

        //mMap = (GoogleMap) params[0];
        url = (String) params[0];

        try {

            URL myurl = new URL(url);
            HttpURLConnection httpURLConnection = (HttpURLConnection) myurl.openConnection();
            is = httpURLConnection.getInputStream();
            bufferedReader = new BufferedReader(new InputStreamReader(is));

            String line = "";
            stringBuilder = new StringBuilder();

            while ((line = bufferedReader.readLine()) != null) {
                stringBuilder.append(line);
            }

            data = stringBuilder.toString();

        } catch (IOException e) {
            e.printStackTrace();
        }

        return data;
    }

    public String getName_restaurant() {
        return name_restaurant;
    }

    public String getImage() {
        return image;
    }

    public Double getRating() {
        return rating;
    }

    public String getPlaceid() {
        return placeid;
    }

    public Double getLat() {
        return lat;
    }

    public Double getLng() {
        return lng;
    }

    @Override
    protected void onPostExecute(String s) {
        try {
            JSONObject parentObject = new JSONObject(s);
            JSONArray resultsArray = parentObject.getJSONArray("results");

            int randomNum = 0 + (int)(Math.random() * (resultsArray.length()-1 ));


            JSONObject nameObject = resultsArray.getJSONObject(randomNum);
            JSONArray photos = nameObject.getJSONArray("photos");
            JSONObject photo = photos.getJSONObject(0);

            JSONObject geometry = nameObject.getJSONObject("geometry");
            JSONObject location = geometry.getJSONObject("location");

            lat = location.getDouble("lat");
            lng = location.getDouble("lng");
            String imageUrl = "https://maps.googleapis.com/maps/api/place/photo?key=AIzaSyBE3zgLJ3y6lM8q8IoTt6L9Y6zEMXp5hyQ&photoreference=" + photo.getString("photo_reference") + "&maxwidth=" + 600;
            name_restaurant = nameObject.getString("name");
            image = imageUrl;
            rating = nameObject.getDouble("rating");
            placeid = nameObject.getString("place_id");




        } catch (JSONException e) {
            e.printStackTrace();
        }

    }
}
