package com.example.restaurantfinder;

import androidx.annotation.RequiresApi;
import androidx.appcompat.app.AppCompatActivity;
import androidx.cardview.widget.CardView;

import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Color;
import android.graphics.Typeface;
import android.net.Uri;
import android.os.AsyncTask;
import android.os.Build;
import android.os.Bundle;
import android.util.Log;
import android.view.Gravity;
import android.view.View;
import android.widget.GridLayout;
import android.widget.ImageView;
import android.widget.TextView;

import java.io.IOException;
import java.io.InputStream;
import java.net.URL;


public class FinalRestaurant extends AppCompatActivity {

    private CardView destination_btn;
    GridLayout gridLayout;
    Context context;
    TextView textview;
    ImageView imageview;
    Bitmap bmp;
    String name, image, placeid;
    Double rating = 0.0, lat = 0.0, lng = 0.0;
    URL url;

    @RequiresApi(api = Build.VERSION_CODES.O)
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_final_restaurant);


        Bundle bundle = getIntent().getExtras();
        name = bundle.getString("restaurant_name");
        image = bundle.getString("image");
        rating = bundle.getDouble("rating");
        placeid = bundle.getString("placeid");
        lat = bundle.getDouble("lat");
        lng = bundle.getDouble("lng");

        gridLayout = (GridLayout) findViewById(R.id.restaurants_grid);
        GridLayout.LayoutParams param =new GridLayout.LayoutParams();
        param.height = androidx.gridlayout.widget.GridLayout.LayoutParams.WRAP_CONTENT;
        param.width = androidx.gridlayout.widget.GridLayout.LayoutParams.MATCH_PARENT;
        param.setGravity(Gravity.CENTER);
        context = getApplicationContext();

        GridLayout.LayoutParams param2 =new GridLayout.LayoutParams();
        param2.setGravity(Gravity.CENTER);

        imageview = new ImageView(context);
        imageview.setLayoutParams(param2);
        LoadImage placeImage = new LoadImage(imageview);
        placeImage.execute(image);

        Typeface face = getResources().getFont(R.font.poppins_medium);

        textview = new TextView(context);
        textview.setGravity(Gravity.CENTER);
        textview.setTextColor(Color.BLACK);
        textview.setTypeface(face);
        textview.setTextSize(21);
        textview.setText("" + name + "\nRating: " + rating +" Stars");
        textview.setLayoutParams(param);
        gridLayout.addView(imageview);
        gridLayout.addView(textview);


        destination_btn = (CardView) findViewById(R.id.destination);
        destination_btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                openDestination();
            }
        });
    }

    public void openDestination() {

        String search = "geo:" +lat+ "," + lng + "?q="+ Uri.encode(name) +"";
        Log.i("searchterm", search);
        Uri gmmIntentUri = Uri.parse(search);
        Intent mapIntent = new Intent(Intent.ACTION_VIEW, gmmIntentUri);
        mapIntent.setPackage("com.google.android.apps.maps");
        startActivity(mapIntent);
    }

    private class LoadImage extends AsyncTask<String, Void, Bitmap> {
        ImageView imageView;
        public LoadImage(ImageView imageview) {
            this.imageView = imageview;
        }

        @Override
        protected Bitmap doInBackground(String... strings) {
            String urlLink = strings[0];
            Bitmap bitmap = null;
            try {
                InputStream inputStream = new URL(urlLink).openStream();
                bitmap = BitmapFactory.decodeStream(inputStream);
            } catch (IOException e) {
                e.printStackTrace();
            }
            return bitmap;
        }

        @Override
        protected void onPostExecute(Bitmap bitmap) {
            imageview.setImageBitmap(bitmap);
        }
    }
}