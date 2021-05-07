package com.example.demoapp;

import android.content.Context;
import android.os.AsyncTask;
import android.os.Bundle;
import android.os.Handler;
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
	private ArrayList<Orderz> orderzList;
	private ListView historyListview;
	private Handler mainHandler = new Handler();

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.order_history);

		historyListview = findViewById(R.id.history_listview_frame);
	}

	private class PreloadTask extends AsyncTask<Void, Void, Void> {
		Context context;

		PreloadTask(Context context) {
			this.context = context;
		}

		@Override
		protected Void doInBackground(Void... voids) {
			getRequest();
			return null;
		}

		@Override
		protected void onPostExecute(Void unused) {
			HistoryViewAdapter historyViewAdapter = new HistoryViewAdapter(context, R.layout.history_listview, orderzList);
		}

		private void getRequest() {
			mainHandler.post(new Runnable() {
				@Override
				public void run() {
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
										for(int i=0; i<jsonArray.length(); i++) {
											JSONObject jsonObject = jsonArray.getJSONObject(i);
											String numItem = jsonObject.getString("num_items");
											String bill = jsonObject.getString("bill");
											JSONArray orderz = jsonObject.getJSONArray("orderz");
											ArrayList<OrderItem> itemList = new ArrayList<OrderItem>();
											if (orderz != null) {
												for (int j=0; j<orderz.length(); j++) {
													JSONObject item = orderz.getJSONObject(i);
													itemList.add(new OrderItem(
															item.getString("name"),
															item.getDouble("price"),
															item.getInt("quantity")));
												}
											}
											orderzList.add(new Orderz(numItem, bill, itemList));
										}
									} catch (JSONException e) {
										e.printStackTrace();
									}
								}
							},
							new Response.ErrorListener() {
								@Override
								public void onErrorResponse(VolleyError error) {

								}
							}
					);
				}
			});
		}
	}

}
