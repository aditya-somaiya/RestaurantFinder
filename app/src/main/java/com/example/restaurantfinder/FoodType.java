package com.example.restaurantfinder;

import androidx.annotation.RequiresApi;
import androidx.appcompat.app.AppCompatActivity;
import androidx.cardview.widget.CardView;

import android.animation.ObjectAnimator;
import android.content.Context;
import android.content.Intent;
import android.graphics.Color;
import android.graphics.Typeface;
import android.os.Build;
import android.os.Bundle;
import android.util.Log;
import android.view.Gravity;
import android.view.View;
import android.widget.GridLayout;
import android.widget.LinearLayout;
import android.widget.GridLayout.LayoutParams;
import android.widget.TextView;

import com.google.android.gms.common.util.ArrayUtils;

import java.util.ArrayList;
import java.util.List;

public class FoodType extends AppCompatActivity {

    private CardView continue_btn;
    CardView[] cardviews;
    Context context;
    GridLayout gridLayout;
    TextView textview;
    GridLayout.LayoutParams layoutParamsCard;
    ArrayList<String> chosen = new ArrayList<>();
    boolean[] button_values;

    public FoodType() {
        button_values = new boolean[]{true, true, true, true, true, true, true, true};
    }

    @RequiresApi(api = Build.VERSION_CODES.O)
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_food_type);

        context = getApplicationContext();
        gridLayout = (GridLayout) findViewById(R.id.food_type_grid);

        String choices[] = new String[]{"burrito", "mexican", "pizza", "sushi", "pasta", "indian", "salads", "Chinese"};
        int[] ids = new int[]{0, 1, 2, 3, 4, 5, 6, 7};
        cardviews = new CardView[choices.length];
        CreateFoodChoices(choices);

        CardView[] btns = new CardView[8];
        for (int i = 0; i < ids.length; i++) {
            btns[i] = (CardView) findViewById(ids[i]);
            final CardView tempBtn = btns[i];
            final int j = i;

            btns[i].setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    boolean current_status = toggleButtonValue(j);

                    int background_color;
                    int from_val;
                    int to_val;

                    if (current_status) {
                        from_val = 0;
                        to_val = 16;
                        background_color = Color.WHITE;
                    } else {
                        from_val = 16;
                        to_val = 0;
                        background_color = Color.rgb(88, 135, 255);
                    }
                    ObjectAnimator animator = ObjectAnimator.ofFloat(tempBtn, "cardElevation", from_val, to_val);
                    animator.start();

                    tempBtn.setCardBackgroundColor(background_color);

                }
            });
        }

        continue_btn = (CardView) findViewById(R.id.continueBtn);
        continue_btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                openTravel();
            }
        });

    }


    @RequiresApi(api = Build.VERSION_CODES.O)
    public void CreateFoodChoices(String[] choices) {

        for (int i = 0; i < choices.length; i++) {
            cardviews[i] = new CardView(context);
            cardviews[i].setId(i);

            layoutParamsCard = new LayoutParams(GridLayout.spec(GridLayout.UNDEFINED, 1f), GridLayout.spec(GridLayout.UNDEFINED, 1f));

            layoutParamsCard.setMargins(16, 16, 16, 16);
            cardviews[i].setLayoutParams(layoutParamsCard);
            cardviews[i].setRadius(16);
            cardviews[i].setElevation(16);
            textview = new TextView(context);
            Typeface font = getResources().getFont(R.font.poppins_medium);
            textview.setTypeface(font);
            textview.setGravity(Gravity.CENTER);
            textview.setTextColor(Color.BLACK);
            textview.setTextSize(18);
            textview.setText(choices[i]);

            cardviews[i].addView(textview);
            gridLayout.addView(cardviews[i]);
        }
    }

    public void openTravel() {
        Intent intent = new Intent(FoodType.this, Travel.class);
        Bundle extras = new Bundle();
        extras.putStringArrayList("chosenFoods",  chosen);
        intent.putExtras(extras);
        startActivity(intent);
    }

    public boolean toggleButtonValue(int id) {
        String choices[] = new String[]{"burrito", "mexican", "pizza", "sushi", "pasta", "indian", "salads", "Chinese"};

        button_values[id] = !button_values[id];

        if(!button_values[id]){
            chosen.add(choices[id]);
        }else{
            chosen.remove(choices[id]);
        }
        Log.i("CHOSEN", chosen.toString());
        return button_values[id];

    }
}
