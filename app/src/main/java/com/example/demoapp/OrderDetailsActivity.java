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
	private FrameLayout frame;
	private String numItems;
	private String bill;

	private Handler mainHandler = new Handler();

	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.order_details);

		Bundle extras = getIntent().getExtras();

		frame = findViewById(R.id.order_details_frame);
		frame.findViewWithTag("back_header").setVisibility(extras.getInt("BACK_HEADER_VIS"));
		frame.findViewWithTag("close_header").setVisibility(extras.getInt("CLOSE_HEADER_VIS"));
		findViewById(R.id.order_btn).setVisibility(extras.getInt("ORDER_BTN_VIS"));
		numItems = extras.getString("NUM_ITEMS");
		bill = extras.getString("BILL");

		orderList = extras.getParcelableArrayList("ORDER_LIST");
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
					TextView n = frame.findViewWithTag("num_item");
					n.setText(String.valueOf(numItems));
					TextView b = frame.findViewWithTag("total_bill");
					b.setText(String.valueOf(bill));
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
					MainActivity.addOrderToDb();
					finish();
				}
			});
		}

		private void addListenerBackBtn() {
			Button backBtn = frame.findViewWithTag("back");
			Button closeBtn = frame.findViewWithTag("close");
			backBtn.setOnClickListener(new View.OnClickListener() {
				@Override
				public void onClick(View v) {
					finish();
				}
			});
			closeBtn.setOnClickListener(new View.OnClickListener() {
				@Override
				public void onClick(View v) {
					finish();
				}
			});
		}
	}

}
