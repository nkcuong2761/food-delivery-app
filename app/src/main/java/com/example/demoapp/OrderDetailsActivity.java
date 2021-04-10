package com.example.demoapp;

import android.content.Context;
import android.os.AsyncTask;
import android.os.Bundle;
import android.os.Handler;
import android.view.View;
import android.widget.Button;
import android.widget.FrameLayout;
import android.widget.ListView;
import android.widget.TextView;
import androidx.appcompat.app.AppCompatActivity;

import java.util.ArrayList;

public class OrderDetailsActivity extends AppCompatActivity {
	private ArrayList<OrderItem> orderList;
	private ListView yourOrder;

	private Handler mainHandler = new Handler();

	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.order_details);

		orderList = MainActivity.getOrderList();
		yourOrder = findViewById(R.id.your_order);

		new PreloadTask(this).execute();
	}

	private class PreloadTask extends AsyncTask<Void, Void, Void> {
		Context context;

		PreloadTask(Context context) {
			this.context = context;
		}

		@Override
		protected void onPreExecute() {
			mainHandler.post(new Runnable() {
				@Override
				public void run() {
					OrderItemAdapter adapter = new OrderItemAdapter(context, R.layout.order_item, orderList);
					yourOrder.setAdapter(adapter);
					FrameLayout frame = findViewById(R.id.order_details_frame);
					TextView numItem = frame.findViewWithTag("num_item");
					numItem.setText(String.valueOf(MainActivity.getItemCounter()));
					TextView bill = frame.findViewWithTag("total_bill");
					bill.setText(String.valueOf(MainActivity.getTotalBill()));
				}
			});
		}

		@Override
		protected Void doInBackground(Void... voids) {
			addListenerBackBtn();
			addListenerOrderBtn();
			return null;
		}

		private void addListenerOrderBtn() {
			Button orderBtn = findViewById(R.id.order_btn);
			orderBtn.setOnClickListener(new View.OnClickListener() {
				@Override
				public void onClick(View v) {
					MainActivity.resetCart();
					finish();
				}
			});
		}

		private void addListenerBackBtn() {
			Button backBtn = findViewById(R.id.order_details_frame).findViewWithTag("back");
			backBtn.setOnClickListener(new View.OnClickListener() {
				@Override
				public void onClick(View v) {
					finish();
				}
			});
		}
	}

}
