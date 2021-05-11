package com.example.demoapp;

import android.os.Parcel;
import android.os.Parcelable;

import java.util.ArrayList;

public class Orderz implements Parcelable {
	private String itemCounter;
	private String totalBill;
	private String time;
	private final ArrayList<OrderItem> orderList;

	public Orderz(String itemCounter, String totalBill, String time, ArrayList<OrderItem> orderList) {
		this.itemCounter = itemCounter;
		this.totalBill = totalBill;
		this.time = time;
		this.orderList = orderList;
	}

	protected Orderz(Parcel in) {
		itemCounter = in.readString();
		totalBill = in.readString();
		time = in.readString();
		orderList = in.createTypedArrayList(OrderItem.CREATOR);
	}

	public static final Creator<Orderz> CREATOR = new Creator<Orderz>() {
		@Override
		public Orderz createFromParcel(Parcel in) {
			return new Orderz(in);
		}

		@Override
		public Orderz[] newArray(int size) {
			return new Orderz[size];
		}
	};

	@Override
	public int describeContents() {
		return 0;
	}

	@Override
	public void writeToParcel(Parcel dest, int flags) {
		dest.writeString(itemCounter);
		dest.writeString(totalBill);
		dest.writeString(time);
		dest.writeTypedList(orderList);
	}

	public String getItemCounter() {
		return itemCounter;
	}

	public String getTotalBill() {
		return totalBill;
	}

	public String getTime() {
		return time;
	}

	public ArrayList<OrderItem> getOrderList() {
		return orderList;
	}

	@Override
	public String toString() {
		return "{" +
				"itemCounter='" + itemCounter + '\'' +
				", totalBill='" + totalBill + '\'' +
				", time='" + time + '\'' +
				", orderList=" + orderList +
				'}';
	}
}
