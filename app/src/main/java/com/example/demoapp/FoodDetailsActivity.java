/* *****************************************
 * CSCI205 - Software Engineering and Design
 * Fall 2020
 * Instructor: Prof. Brian King
 *
 * Name: Cuong Nguyen
 * Section: Section 2
 * Date: 3/23/2021
 * Time: 2:28 PM
 *
 * Project: DemoApp
 * Package: com.example.demoapp
 * Class: FoodDetailsActivity
 *
 * Description:
 *
 * ****************************************
 */
package com.example.demoapp;

import android.os.AsyncTask;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import androidx.appcompat.app.AppCompatActivity;

public class FoodDetailsActivity extends AppCompatActivity {
	private FoodItem foodItem;
	private ImageView image;
	private TextView name;
	private TextView longDes;
	private TextView price;

	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.food_details);

		Bundle extras = getIntent().getExtras();
		foodItem = extras.getParcelable("THE_ITEM");

		new PreloadTask().execute();
	}

//	@Override
//	protected void onDestroy() {
//		name.setText("");
//		longDes.setText("");
//		price.setText("");
//	}

	private class PreloadTask extends AsyncTask<Void, Void, Void> {

		@Override
		protected void onPostExecute(Void unused) {
			loadInterface();
		}

		@Override
		protected Void doInBackground(Void... voids) {
			addListenerBackBtn();
			addListenerAddToCartBtn();
			return null;
		}

		private void loadInterface() {
			image = findViewById(R.id.food_details_frame).findViewWithTag("image");
			name = findViewById(R.id.food_details_frame).findViewWithTag("name");
			longDes = findViewById(R.id.food_details_frame).findViewWithTag("long_description");
			price = findViewById(R.id.food_details_frame).findViewWithTag("price");
			int resId = getResources().getIdentifier(foodItem.getImage(), "raw", getPackageName());
			image.setImageResource(resId);
			name.setText(foodItem.getName());
			longDes.setText(foodItem.getLongDescription());
			price.setText(String.valueOf(foodItem.getPrice()));
		}

		private void addListenerAddToCartBtn() {
			LinearLayout addBtn = findViewById(R.id.add_to_cart);
			addBtn.setOnClickListener(new View.OnClickListener() {
				@Override
				public void onClick(View v) {
					MainActivity.addItem(foodItem);
					finish();
				}
			});
		}

		private void addListenerBackBtn() {
			Button backBtn = findViewById(R.id.food_details_frame).findViewWithTag("back");
			backBtn.setOnClickListener(new View.OnClickListener() {
				@Override
				public void onClick(View v) {
					finish();
				}
			});
		}
	}
}
