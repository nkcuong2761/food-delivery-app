package com.example.demoapp;

import android.os.Parcel;
import android.os.Parcelable;

public class OrderItem implements Parcelable {
	private String name;
	private double price;
	private int quantity;

	public OrderItem(String name, double price, int quantity) {
		this.name = name;
		this.price = price;
		this.quantity = quantity;
	}

	protected OrderItem(Parcel in) {
		name = in.readString();
		price = in.readDouble();
		quantity = in.readInt();
	}

	public static final Creator<OrderItem> CREATOR = new Creator<OrderItem>() {
		@Override
		public OrderItem createFromParcel(Parcel in) {
			return new OrderItem(in);
		}

		@Override
		public OrderItem[] newArray(int size) {
			return new OrderItem[size];
		}
	};

	@Override
	public int describeContents() {
		return 0;
	}

	@Override
	public void writeToParcel(Parcel dest, int flags) {
		dest.writeString(name);
		dest.writeDouble(price);
		dest.writeInt(quantity);
	}

	public String getName() {
		return name;
	}

	public double getPrice() {
		return price;
	}

	public int getQuantity() {
		return quantity;
	}

	public void setQuantity(int quantity) {
		this.quantity = quantity;
	}

	@Override
	public String toString() {
		return "{" +
				"name='" + name + '\'' +
				", price=" + price +
				", quantity=" + quantity +
				'}';
	}
}
