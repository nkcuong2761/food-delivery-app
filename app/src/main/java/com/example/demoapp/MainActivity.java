package com.example.demoapp;

import android.content.Context;
import android.content.Intent;
import android.view.View;
import android.widget.*;
import androidx.appcompat.app.AppCompatActivity;
import android.os.Bundle;
import com.makeramen.roundedimageview.RoundedImageView;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;
import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;

public class MainActivity extends AppCompatActivity {
    private static int itemCounter = 0;
    static FrameLayout orderDetailBtn;

    Intent foodDetailPage;
    Intent orderDetailPage;

    ArrayList<FoodItem> newDishesList, appetizersList;
    LinearLayout newDishes;
    ListView appetizers;

    @Override
    protected void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        final Context context = this;
        foodDetailPage = new Intent(context, FoodDetailsActivity.class);
        orderDetailPage = new Intent(context, OrderDetailsActivity.class);
        //intent.putExtra(object), Bundle, Fragment

        orderDetailBtn = findViewById(R.id.order_detail_frame);
        newDishesList = new ArrayList<>();
        newDishes = findViewById(R.id.new_dishes);
        appetizersList = new ArrayList<>();
        appetizers = findViewById(R.id.appetizers);

        readJSON();
        loadNewDishes();
        addListenerToFoodDetails();
        addListenerToOrderDetail();
    }

    private void readJSON() {
        if(loadJSONFromAsset() != null) {
            try {
                JSONObject jsonObject = new JSONObject(loadJSONFromAsset());
                // read from newDishes array
                JSONArray newDishesArray = jsonObject.getJSONArray("newDishes");
                for (int i=0; i<newDishesArray.length(); i++) {
                    JSONObject foodItemObject = newDishesArray.getJSONObject(i);
                    newDishesList.add(new FoodItem(
                            foodItemObject.getString("image"),
                            foodItemObject.getString("name"),
                            (float) foodItemObject.getDouble("price"),
                            foodItemObject.getString("description"),
                            foodItemObject.getString("longDescription")
                    ));
                }
                // read from appetizers array
                JSONArray appetizersArray = jsonObject.getJSONArray("appetizers");
                for (int i=0; i<appetizersArray.length(); i++) {
                    JSONObject foodItemObject = appetizersArray.getJSONObject(i);
                    appetizersList.add(new FoodItem(
                            foodItemObject.getString("image"),
                            foodItemObject.getString("name"),
                            (float) foodItemObject.getDouble("price"),
                            foodItemObject.getString("description"),
                            foodItemObject.getString("longDescription")
                    ));
                }
            } catch (JSONException e) {
                e.printStackTrace();
            }
            CustomListAdapter adapter = new CustomListAdapter(this, R.layout.list_item, appetizersList);
            appetizers.setAdapter(adapter);
        } else {
            System.out.println("--------- DIT CON ME 1 ---------");
        }
    }

    public String loadJSONFromAsset() {
        String json;
        try {
            // Opening food.json file
            InputStream inputStream = getResources().openRawResource(R.raw.food);
            int size = inputStream.available();
            byte[] buffer = new byte[size];
            // read values in the byte array
            inputStream.read(buffer);
            inputStream.close();
            // convert byte to string
            json = new String(buffer, "UTF-8");
        } catch (IOException e) {
            e.printStackTrace();
            return null;
        }
        return json;
    }

    private void loadNewDishes() {
        for (int i=0; i<newDishesList.size(); i++) {
            FoodItem foodItem = newDishesList.get(i);
            FrameLayout frame = (FrameLayout) newDishes.getChildAt(i+1);//Because the LinearLayout start with a <Space/>
            RoundedImageView imageView = frame.findViewWithTag("image");
            TextView name = frame.findViewWithTag("name");
            TextView des = frame.findViewWithTag("description");
            TextView price = frame.findViewWithTag("price");
            int resId = this.getResources().getIdentifier(foodItem.getImage(), "raw", this.getPackageName());
            imageView.setImageResource(resId);
            name.setText(foodItem.getName());
            des.setText(foodItem.getDescription());
            price.setText(String.valueOf(foodItem.getPrice()));
        }
    }

    private void addListenerToOrderDetail() {
        orderDetailBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(orderDetailPage);
            }
        });
    }

    private void addListenerToFoodDetails() {
        for (int i=1; i<newDishes.getChildCount()-1; i++) {
            FrameLayout frame = (FrameLayout) newDishes.getChildAt(i);
            frame.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    startActivity(foodDetailPage);
                }
            });
        }
        for (int i=0; i<appetizers.getCount(); i++) {
            System.out.println(i);
            View v = appetizers.getAdapter().getView(i, null, null);
            if(v != null) {
                System.out.println("--------- OKEOKEOKE ---------");
                Button addBtn = v.findViewWithTag("add");
                addBtn.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        startActivity(foodDetailPage);
                    }
                });
            } else {
                System.out.println("--------- DIT CON ME 2 ---------");
            }
        }
    }

    public static void addItem() {
        orderDetailBtn.setVisibility(View.VISIBLE);
        itemCounter++;
    }

    public static void resetCart() {
        orderDetailBtn.setVisibility(View.INVISIBLE);
        itemCounter = 0;
    }

}
