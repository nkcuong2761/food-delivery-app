/* *****************************************
 * CSCI205 - Software Engineering and Design
 * Fall 2020
 * Instructor: Prof. Brian King
 *
 * Name: Cuong Nguyen
 * Section: Section 2
 * Date: 4/10/2021
 * Time: 12:08 PM
 *
 * Project: DemoApp
 * Package: com.example.demoapp
 * Class: OrderItemAdapter
 *
 * Description:
 *
 * ****************************************
 */
package com.example.demoapp;

import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import java.util.ArrayList;

public class OrderItemAdapter extends BaseAdapter {

	ArrayList<OrderItem> orderItems;
	Context context;
	int resource;

	public OrderItemAdapter(Context context, int resource, ArrayList<OrderItem> orderItems) {
		this.context = context;
		this.resource = resource;
		this.orderItems = orderItems;
	}

	@Override
	public int getCount() {
		return orderItems.size();
	}

	@Override
	public Object getItem(int position) {
		return orderItems.get(position);
	}

	@Override
	public long getItemId(int position) {
		return position;
	}

	@Override
	public View getView(int position, View convertView, ViewGroup parent) {
		if (convertView == null) {
			convertView = LayoutInflater.from(context).inflate(resource, parent, false);
		}
		final OrderItem item = orderItems.get(position);

		TextView name = convertView.findViewWithTag("name");
		name.setText(item.getName());

		TextView price = convertView.findViewWithTag("price");
		price.setText(String.valueOf(item.getPrice()));

		return convertView;
	}
}
