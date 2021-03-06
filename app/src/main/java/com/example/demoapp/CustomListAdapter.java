package com.example.demoapp;

import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.BaseAdapter;
import android.widget.Button;
import android.widget.TextView;
import com.makeramen.roundedimageview.RoundedImageView;
import java.util.ArrayList;

public class CustomListAdapter extends BaseAdapter {

	ArrayList<FoodItem> foodItems;
	Context context;
	int resource;
	Intent foodDetailPage;

	public CustomListAdapter(Context context, int resource, ArrayList<FoodItem> foodItems) {
		this.foodItems = foodItems;
		this.context = context;
		this.resource = resource;
	}

	@Override
	public int getCount() {
		return foodItems.size();
	}

	@Override
	public Object getItem(int position) {
		return foodItems.get(position);
	}

	@Override
	public long getItemId(int position) {
		return position;
	}

	@Override
	public View getView(final int position, View convertView, ViewGroup parent) {
		if (convertView == null) {
			convertView = LayoutInflater.from(context).inflate(resource, parent, false);
		}
		final FoodItem item = foodItems.get(position);

		RoundedImageView roundedImageView = convertView.findViewWithTag("image");
//		Picasso.get().load(item.getImage()).into(roundedImageView);
		int resId = context.getResources().getIdentifier(item.getImage(), "raw", context.getPackageName());
		roundedImageView.setImageResource(resId);

		TextView name = convertView.findViewWithTag("name");
		name.setText(item.getName());

		TextView price = convertView.findViewWithTag("price");
		price.setText(String.valueOf(item.getPrice()));

		TextView description = convertView.findViewWithTag("description");
		description.setText(item.getDescription());

		// Button listener
		foodDetailPage = new Intent(context, FoodDetailsActivity.class);
		Button add = convertView.findViewWithTag("add");
		add.setOnClickListener(new View.OnClickListener() {
			@Override
			public void onClick(View v) {
				foodDetailPage.putExtra("THE_ITEM", item);
				context.startActivity(foodDetailPage);
			}
		});

		return convertView;
	}
}
