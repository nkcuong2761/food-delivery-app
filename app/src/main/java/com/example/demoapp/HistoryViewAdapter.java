package com.example.demoapp;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import java.util.ArrayList;

public class HistoryViewAdapter extends BaseAdapter {
	Context context;
	int resource;
	ArrayList<Orderz> orderzs;

	public HistoryViewAdapter(Context context, int resource, ArrayList<Orderz> orderzs) {
		this.context = context;
		this.resource = resource;
		this.orderzs = orderzs;
	}

	@Override
	public int getCount() {
		return orderzs.size();
	}

	@Override
	public Object getItem(int position) {
		return orderzs.get(position);
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
		final Orderz orderz = orderzs.get(position);

		TextView time = convertView.findViewWithTag("time");
		time.setText(orderz.getTime());

		TextView bill = convertView.findViewWithTag("bill");
		bill.setText(String.valueOf(orderz.getTotalBill()));

		TextView numItem = convertView.findViewWithTag("num_item");
		numItem.setText(String.valueOf(orderz.getItemCounter()));

		return convertView;
	}
}
