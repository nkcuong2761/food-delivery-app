package com.example.demoapp;

import android.content.Context;
import android.content.Intent;
import android.os.AsyncTask;
import android.view.MotionEvent;
import android.view.View;
import android.widget.*;
import androidx.appcompat.app.AppCompatActivity;
import android.os.Bundle;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.net.URL;
import java.net.URLConnection;
import java.util.ArrayList;

import static android.app.PendingIntent.getActivity;

public class MainActivity extends AppCompatActivity {
    private static int itemCounter = 0;
    static FrameLayout orderDetailBtn;
    Intent foodDetailPage;
    Intent orderDetailPage;
    ArrayList<FoodItem> menu;
    ListView appetizers;

    @Override
    protected void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        final Context context = this;
        foodDetailPage = new Intent(context, FoodDetailsActivity.class);
        orderDetailPage = new Intent(context, OrderDetailsActivity.class);

        orderDetailBtn = findViewById(R.id.order_detail_frame);
        //intent.putExtra(object), Bundle, Fragment
//        ArrayAdapter adapter = new ArrayAdapter(this, R.layout.list_item);
        menu = new ArrayList<>();
        appetizers = findViewById(R.id.appetizers);

//        runOnUiThread(new Runnable() {
//            @Override
//            public void run() {
//                new ReadJSON().execute(String.valueOf(R.raw.food));
//            }
//        });
        try {
            JSONObject jsonObject = new JSONObject(loadJSONFromAsset());
            JSONArray jsonArray = jsonObject.getJSONArray("menu");
            for (int i=0; i<jsonArray.length(); i++) {
                JSONObject foodItemObject = jsonArray.getJSONObject(i);
                menu.add(new FoodItem(
                        foodItemObject.getString("image"),
                        foodItemObject.getString("name"),
                        (float) foodItemObject.getDouble("price"),
                        foodItemObject.getString("description")
                ));
            }
        } catch (JSONException e) {
            e.printStackTrace();
        }
        CustomListAdapter adapter = new CustomListAdapter(this, R.layout.list_item, menu);
        appetizers.setAdapter(adapter);
//        readJSON();
        addListenerToFoodDetails();
        addListenerToOrderDetail();
    }

    private void readJSON() {
    }

    public String loadJSONFromAsset() {
        String json;
        try {
            // Opening food.json file
            InputStream inputStream = getAssets().open("raw/food.json");
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

//    class ReadJSON extends AsyncTask<String, Integer, String> {
//        @Override
//        protected String doInBackground(String... params) {
//            return readURL(params[0]);
//        }
//
//        @Override
//        protected void onPostExecute(String s) {
//            System.out.println("-------- dit con me --------");
//            /* Re-paste the code here */
//        }
//    }
//
//    private static String readURL(String theUrl) {
//        StringBuilder content = new StringBuilder();
//        try {
//            // create a url object
//            URL url = new URL(theUrl);
//            // create a urlConnection object
//            URLConnection urlConnection = url.openConnection();
//            // wrap the urlConnection in a bufferedReader
//            BufferedReader bufferedReader = new BufferedReader(new InputStreamReader(urlConnection.getInputStream()));
//            String line;
//            // read from the urlConnection via the bufferedReader
//            while ((line = bufferedReader.readLine()) != null) {
//                content.append(line + "\n");
//            }
//            bufferedReader.close();
//        }
//        catch (Exception e) {
//            e.printStackTrace();
//        }
//        return content.toString();
//    }

    private void addListenerToOrderDetail() {
        orderDetailBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(orderDetailPage);
            }
        });
    }

    private void addListenerToFoodDetails() {
        LinearLayout newDishes = findViewById(R.id.new_dishes);
        for (int i=1; i<newDishes.getChildCount()-1; i++) {
            FrameLayout frame = (FrameLayout) newDishes.getChildAt(i);
            frame.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    startActivity(foodDetailPage);
                }
            });
        }
//        for (int i=0; i<appetizers.getChildCount(); i++) {
//            Button addBtn = appetizers.getChildAt(i).findViewWithTag("add");
//            addBtn.setOnClickListener(new View.OnClickListener() {
//                @Override
//                public void onClick(View v) {
//                    startActivity(foodDetailPage);
//                }
//            });
//        }
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
