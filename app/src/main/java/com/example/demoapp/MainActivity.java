package com.example.demoapp;

import android.content.Context;
import android.content.Intent;
import android.database.Cursor;
import android.os.AsyncTask;
import android.os.Handler;
import android.os.Parcelable;
import android.util.Log;
import android.view.View;
import android.widget.*;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import android.os.Bundle;
import com.android.volley.*;
import com.android.volley.toolbox.*;
import com.makeramen.roundedimageview.RoundedImageView;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;
import java.io.IOException;
import java.io.InputStream;
import java.io.UnsupportedEncodingException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

public class MainActivity extends AppCompatActivity {
    private static int itemCounter = 0;
    private static double totalBill = 0;
    private static ArrayList<OrderItem> orderList;

    Intent foodDetailPage;
    Intent orderDetailPage;

    private static FrameLayout orderDetailBtn;
    private ArrayList<FoodItem> newDishesList, appetizersList;
    private LinearLayout newDishes;
    private ListView appetizers;

    private Handler mainHandler = new Handler();

    private static DBManager dbManager;
    private static Cursor cursor;

    private static RequestQueue requestQueue;

    @Override
    protected void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        requestQueue = Volley.newRequestQueue(this);

        foodDetailPage = new Intent(this, FoodDetailsActivity.class);
        orderDetailPage = new Intent(this, OrderDetailsActivity.class);

        orderDetailBtn = findViewById(R.id.order_detail_btn);
        newDishesList = new ArrayList<>();
        newDishes = findViewById(R.id.new_dishes);
        appetizersList = new ArrayList<>();
        appetizers = findViewById(R.id.appetizers);
        orderList = new ArrayList<>();

        dbManager = new DBManager(this);
        dbManager.open();
        cursor = dbManager.fetch();

        new PreloadTask(this).execute();
    }

    public void addJSONArrayRequest(JsonArrayRequest request) {
        requestQueue.add(request);
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
        orderList.clear();
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

    public static int getItemCounter() {
        return itemCounter;
    }

    public static double getTotalBill() {
        return totalBill;
    }

    public static void addOrderToDb() {
//	    System.out.println("---------Actual placed order-------");
//        System.out.println(String.valueOf(itemCounter));
//        System.out.println(String.valueOf(totalBill));
//        dbManager.insert(String.valueOf(itemCounter), String.valueOf(totalBill));
//        // Test database
//	    System.out.println("---------The latest order info from before refreshing the DB-------");
//        cursor.moveToLast();
//        System.out.println(cursor.getString(1));
//        System.out.println(cursor.getString(2));
        JSONArray array = new JSONArray();
        for (final OrderItem orderItem : getOrderList()) {
            JSONObject object = new JSONObject();
            try {
                object.put("name", orderItem.getName());
                object.put("price", orderItem.getPrice());
                object.put("quantity", orderItem.getQuantity());
            } catch (JSONException e) {
                e.printStackTrace();
            }
            array.put(object);
        }
        String url = "http://192.168.100.10:3000/";// 192.168.100.10
//        JsonArrayRequest jsonArrayRequest = new JsonArrayRequest(
//                Request.Method.POST,
//                url,
//                array,
//                new Response.Listener<JSONArray>() {
//                    @Override
//                    public void onResponse(JSONArray response) {
//                        Log.d("POST RESPONSE", response.toString());
//                    }
//                },
//                new Response.ErrorListener() {
//                    @Override
//                    public void onErrorResponse(VolleyError error) {
//                        Log.e("POST RESPONSE", error.toString());
//                    }
//                }
//        );
        final String requestBody = array.toString();
        StringRequest stringRequest = new StringRequest(
                Request.Method.POST,
                url,
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {
                        Log.d("POST RESPONSE", response);
                    }
                },
                new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {
                        Log.e("POST RESPONSE", error.toString());
                    }
                }
        ) {
            @Override
            public String getBodyContentType() {
                return "application/x-www-form-urlencoded";
            }

            @Override
            protected Map<String, String> getParams() throws AuthFailureError {
                Map<String, String> params = new HashMap<String, String>();
                params.put("num_items", String.valueOf(itemCounter));
                params.put("bill", String.valueOf(totalBill));
                params.put("orderz", requestBody);
                return params;
            }
        };
        requestQueue.add(stringRequest);
    }

    private class PreloadTask extends AsyncTask<Void, Void, Void> {
        Context context;

        PreloadTask(Context context) {
            this.context = context;
        }

        @Override
        protected void onPreExecute() {
//            System.out.println("-----onPreExecute------");
            readJSON();
        }

        @Override
        protected Void doInBackground(Void... voids) {
//            System.out.println("-----doInBackground------");
//            loadNewDishes();
            addListenerToOrderDetail();
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
                                foodItemObject.getDouble("price"),
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
                                foodItemObject.getDouble("price"),
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
