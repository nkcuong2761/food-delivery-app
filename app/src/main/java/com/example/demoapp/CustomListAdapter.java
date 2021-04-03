/* *****************************************
 * CSCI205 - Software Engineering and Design
 * Fall 2020
 * Instructor: Prof. Brian King
 *
 * Name: Cuong Nguyen
 * Section: Section 2
 * Date: 4/3/2021
 * Time: 12:29 PM
 *
 * Project: DemoApp
 * Package: com.example.demoapp
 * Class: CustomListAdapter
 *
 * Description:
 *
 * ****************************************
 */
package com.example.demoapp;

import android.app.Activity;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;
import androidx.annotation.NonNull;
import com.makeramen.roundedimageview.RoundedImageView;
import com.squareup.picasso.Picasso;
import java.util.ArrayList;

public class CustomListAdapter extends ArrayAdapter<FoodItem> {

	ArrayList<FoodItem> foodItems;
	Context context;
	int resource;

	public CustomListAdapter(Context context, int resource, ArrayList<FoodItem> foodItems) {
		super(context, resource, foodItems);
		this.foodItems = foodItems;
		this.context = context;
		this.resource = resource;
	}

	@NonNull
	@Override
	public View getView(int position, View convertView, ViewGroup parent) {
		if (convertView == null) {
			LayoutInflater layoutInflater = (LayoutInflater) getContext().getSystemService(Activity.LAYOUT_INFLATER_SERVICE);
			convertView = layoutInflater.inflate(R.layout.list_item, null, true);
		}
		FoodItem item = getItem(position);

		RoundedImageView roundedImageView = convertView.findViewWithTag("image");
		Picasso.get().load(item.getImage()).into(roundedImageView);

		TextView name = convertView.findViewWithTag("name");
		name.setText(item.getName());

		TextView price = convertView.findViewWithTag("price");
		price.setText(item.getPrice());

		TextView description = convertView.findViewWithTag("description");
		description.setText(item.getDescription());

		return convertView;
	}
}
