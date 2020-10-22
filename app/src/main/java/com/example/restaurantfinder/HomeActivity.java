package com.example.restaurantfinder;

import androidx.appcompat.app.AppCompatActivity;

import android.app.Activity;
import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.Spinner;
import android.widget.TextView;

import com.google.firebase.auth.FirebaseAuth;

public class HomeActivity extends AppCompatActivity {
    Button signOutButton;
    TextView city_choice;
    FirebaseAuth mFirebaseAuth;
    private FirebaseAuth.AuthStateListener mAuthStateListener;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home);
        city_choice = findViewById(R.id.city_choice);

        signOutButton = findViewById(R.id.signout);

        signOutButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                FirebaseAuth.getInstance().signOut();
                finish();
                Intent toLogin = new Intent(HomeActivity.this, LoginActivity.class);
                startActivity(toLogin);
            }
        });
        Spinner spinner = (Spinner) findViewById(R.id.cities_list);
// Create an ArrayAdapter using the string array and a default spinner layout
        ArrayAdapter<CharSequence> adapter = ArrayAdapter.createFromResource(this,
                R.array.cities_list, android.R.layout.simple_spinner_item);
// Specify the layout to use when the list of choices appears
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
// Apply the adapter to the spinner
        spinner.setAdapter(adapter);

        class SpinnerActivity extends Activity implements AdapterView.OnItemSelectedListener {
            public void onItemSelected(AdapterView<?> parent, View view,
                                       int pos, long id) {
                String show_city = (String) parent.getItemAtPosition(pos);
                ((TextView) view).setTextColor(Color.RED);
                city_choice.setText(""+show_city);
            }

            public void onNothingSelected(AdapterView<?> parent) {
                // Another interface callback
                city_choice.setText(R.string.city_choose_text);
            }
        }
    }
}