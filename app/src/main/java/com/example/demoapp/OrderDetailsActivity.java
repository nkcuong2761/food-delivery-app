/* *****************************************
 * CSCI205 - Software Engineering and Design
 * Fall 2020
 * Instructor: Prof. Brian King
 *
 * Name: Cuong Nguyen
 * Section: Section 2
 * Date: 3/23/2021
 * Time: 4:47 PM
 *
 * Project: DemoApp
 * Package: com.example.demoapp
 * Class: OrderDetailsActivity
 *
 * Description:
 *
 * ****************************************
 */
package com.example.demoapp;

import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import androidx.appcompat.app.AppCompatActivity;

public class OrderDetailsActivity extends AppCompatActivity {

	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.order_details);
		addListenerBackBtn();
		addListenerOrderBtn();
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
