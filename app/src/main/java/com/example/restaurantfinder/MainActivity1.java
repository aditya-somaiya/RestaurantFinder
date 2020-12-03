package com.example.restaurantfinder;

import androidx.appcompat.app.AppCompatActivity;
import androidx.cardview.widget.CardView;

import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.Toast;

import com.google.firebase.auth.FirebaseAuth;

public class MainActivity1 extends AppCompatActivity {
    private CardView button_takeout, button_dinein;
    Button signOutButton;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main1);

        signOutButton = findViewById(R.id.signout);

        signOutButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                FirebaseAuth.getInstance().signOut();
                finish();
                Intent toLogin = new Intent(MainActivity1.this, LoginActivity.class);
                startActivity(toLogin);
            }
        });

        button_takeout = (CardView) findViewById(R.id.takeout_btn);
        button_takeout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                openDietaryRestrictions();
            }
        });

        button_dinein = (CardView) findViewById(R.id.dinein_btn);
        button_dinein.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                displayDisabledMessage();
            }
        });
    }

    public void openDietaryRestrictions() {
        Intent intent = new Intent(this, FoodType.class);
        startActivity(intent);
    }

    public void displayDisabledMessage() {
        Intent intent = new Intent(this, FoodType.class);
        startActivity(intent);
    }
}
