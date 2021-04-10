package com.example.demoapp;

import android.content.Context;
import android.content.Intent;
import android.os.AsyncTask;
import android.os.Handler;
import android.os.Parcelable;
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
    private static float totalBill = 0;
    private static ArrayList<OrderItem> orderList;

    Intent foodDetailPage;
    Intent orderDetailPage;

    private static FrameLayout orderDetailBtn;
    private ArrayList<FoodItem> newDishesList, appetizersList;
    private LinearLayout newDishes;
    private ListView appetizers;

    private Handler mainHandler = new Handler();

    @Override
    protected void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        foodDetailPage = new Intent(this, FoodDetailsActivity.class);
        orderDetailPage = new Intent(this, OrderDetailsActivity.class);

        orderDetailBtn = findViewById(R.id.order_detail_frame);
        newDishesList = new ArrayList<>();
        newDishes = findViewById(R.id.new_dishes);
        appetizersList = new ArrayList<>();
        appetizers = findViewById(R.id.appetizers);
        orderList = new ArrayList<>();

        new PreloadTask(this).execute();
    }

    public static void addItem(FoodItem foodItem) {
        if (orderDetailBtn.getVisibility() == View.INVISIBLE)
            orderDetailBtn.setVisibility(View.VISIBLE);
        totalBill += foodItem.getPrice();
        itemCounter++;

        orderList.add(new OrderItem(foodItem.getName(), foodItem.getPrice(), 1));

        TextView numItem = orderDetailBtn.findViewWithTag("num_item");
        TextView total = orderDetailBtn.findViewWithTag("total_bill");
        numItem.setText(String.valueOf(itemCounter));
        total.setText(String.valueOf(totalBill));


    }

    public static void resetCart() {
        orderDetailBtn.setVisibility(View.INVISIBLE);
        totalBill = 0;
        itemCounter = 0;
        TextView numItem = orderDetailBtn.findViewWithTag("num_item");
        TextView total = orderDetailBtn.findViewWithTag("total_bill");
        numItem.setText(String.valueOf(itemCounter));
        total.setText(String.valueOf(totalBill));
    }

    public static ArrayList<OrderItem> getOrderList() {
        return orderList;
    }

    private class PreloadTask extends AsyncTask<Void, Void, Void> {
        Context context;

        PreloadTask(Context context) {
            this.context = context;
        }

//        @Override
//        protected void onPreExecute() {
////            System.out.println("-----onPreExecute------");
//        }

        @Override
        protected Void doInBackground(Void... voids) {
//            System.out.println("-----doInBackground------");
            readJSON();
            addListenerToOrderDetail();
//            loadNewDishes();
            return null;
        }

        @Override
        protected void onPostExecute(Void unused) {
//            System.out.println("-----onPostExecute------");
            super.onPostExecute(unused);
            addListenerToFoodDetails();
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
                mainHandler.post(new Runnable() {
                    @Override
                    public void run() {
                        CustomListAdapter adapter = new CustomListAdapter(context, R.layout.list_item, appetizersList);
                        appetizers.setAdapter(adapter);
//                        System.out.println(appetizers.getAdapter().getCount());
                    }
                });
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
            mainHandler.post(new Runnable() {
                @Override
                public void run() {
                    for (int i=0; i<newDishesList.size(); i++) {
                        FoodItem foodItem = newDishesList.get(i);
                        FrameLayout frame = (FrameLayout) newDishes.getChildAt(i+1);//Because the LinearLayout start with a <Space/>
                        RoundedImageView imageView = frame.findViewWithTag("image");
                        TextView name = frame.findViewWithTag("name");
                        TextView des = frame.findViewWithTag("description");
                        TextView price = frame.findViewWithTag("price");
                        int resId = context.getResources().getIdentifier(foodItem.getImage(), "raw", context.getPackageName());
                        imageView.setImageResource(resId);
                        name.setText(foodItem.getName());
                        des.setText(foodItem.getDescription());
                        price.setText(String.valueOf(foodItem.getPrice()));
                    }
                }
            });
        }

        private void addListenerToFoodDetails() {
            for (int i=1; i<newDishes.getChildCount()-1; i++) {
                final FoodItem foodItem = newDishesList.get(i-1);
                FrameLayout frame = (FrameLayout) newDishes.getChildAt(i);
                frame.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        foodDetailPage.putExtra("THE_ITEM", foodItem);
                        startActivity(foodDetailPage);
                    }
                });
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
    }

}
