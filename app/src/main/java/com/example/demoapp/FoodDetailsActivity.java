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

import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.LinearLayout;
import androidx.appcompat.app.AppCompatActivity;

public class FoodDetailsActivity extends AppCompatActivity {

	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.food_details);
		addListenerBackBtn();
		addListenerAddToCartBtn();
	}

	private void addListenerAddToCartBtn() {
		LinearLayout addBtn = findViewById(R.id.add_to_cart);
		addBtn.setOnClickListener(new View.OnClickListener() {
			@Override
			public void onClick(View v) {
				MainActivity.addItem();
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
