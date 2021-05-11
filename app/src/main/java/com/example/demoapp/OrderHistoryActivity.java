package com.example.demoapp;

import android.content.Context;
import android.os.AsyncTask;
import android.os.Bundle;
import android.os.Handler;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.ListView;
import androidx.appcompat.app.AppCompatActivity;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonArrayRequest;
import com.android.volley.toolbox.Volley;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;

public class OrderHistoryActivity extends AppCompatActivity {
	private final static String TAG = OrderHistoryActivity.class.getSimpleName();
	private ArrayList<Orderz> orderzList;
	private ListView historyListview;

	private Handler mainHandler = new Handler();

	private static RequestQueue requestQueue;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.order_history);

		requestQueue = Volley.newRequestQueue(this);

		orderzList = new ArrayList<Orderz>();
		historyListview = findViewById(R.id.history_listview_frame);

		new PreloadTask(this).execute();
	}

	private class PreloadTask extends AsyncTask<Void, Void, Void> {
		Context context;

		PreloadTask(Context context) {
			this.context = context;
		}

		@Override
		protected void onPreExecute() {
			Log.d(TAG, "onPreExecute");
			getRequest();
		}

		@Override
		protected Void doInBackground(Void... voids) {
			Log.d(TAG, "doInBackground");
			addListenerBackBtn();
			return null;
		}

		@Override
		protected void onPostExecute(Void unused) {
			Log.d(TAG, "onPostExecute");
//			System.out.println("Post orderzList: " + orderzList);
			mainHandler.post(new Runnable() {
				@Override
				public void run() {
					HistoryViewAdapter historyViewAdapter = new HistoryViewAdapter(context, R.layout.history_listview, orderzList);
					historyListview.setAdapter(historyViewAdapter);
				}
			});
		}

		private void getRequest() {
			Log.d("GET REQUEST", "starting getRequest function");
			String url = "http://192.168.100.10:3000/";// 192.168.100.10
			JsonArrayRequest jsonArrayRequest = new JsonArrayRequest(
					Request.Method.GET,
					url,
					null,
					new Response.Listener<JSONArray>() {
						@Override
						public void onResponse(JSONArray response) {
							JSONArray jsonArray = response;
							try {
								for(int i=jsonArray.length()-1; i>=0; i--) {
									JSONObject jsonObject = jsonArray.getJSONObject(i);
									String numItem = jsonObject.getString("num_items");
									String bill = jsonObject.getString("bill");
									String time = jsonObject.getString("time");
									// Get the orderz array
									String jsonData = jsonObject.getString("orderz");
									JSONArray orderz = new JSONArray(jsonData);
//									System.out.println("orderz: " + orderz);
									ArrayList<OrderItem> itemList = new ArrayList<OrderItem>();
									if (orderz != null) {
										for (int j=0; j<orderz.length(); j++) {
											JSONObject item = orderz.getJSONObject(j);
//											System.out.println(item);
											itemList.add(new OrderItem(
													item.getString("name"),
													item.getDouble("price"),
													item.getInt("quantity")));
										}
									}
//									System.out.println("itemList: " + itemList);
									Orderz orderz1 = new Orderz(numItem, bill, time, itemList);
//									System.out.println("orderz1: " + orderz1);
									orderzList.add(orderz1);
								}
							} catch (JSONException e) {
								e.printStackTrace();
							}
						}
					},
					new Response.ErrorListener() {
						@Override
						public void onErrorResponse(VolleyError error) {
							Log.e("GET REQUEST", error.toString());
						}
					}
			);
			requestQueue.add(jsonArrayRequest);
		}

		private void addListenerBackBtn() {
			Button backBtn = findViewById(R.id.history_page_frame).findViewWithTag("back");
			backBtn.setOnClickListener(new View.OnClickListener() {
				@Override
				public void onClick(View v) {
					finish();
				}
			});
		}
	}

}
