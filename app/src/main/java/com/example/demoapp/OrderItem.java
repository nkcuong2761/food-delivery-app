package com.example.demoapp;

import android.os.Parcel;
import android.os.Parcelable;

public class OrderItem implements Parcelable {
	private String name;
	private float price;
	private int quantity;

	public OrderItem(String name, float price, int quantity) {
		this.name = name;
		this.price = price;
		this.quantity = quantity;
	}

	protected OrderItem(Parcel in) {
		name = in.readString();
		price = in.readFloat();
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
		dest.writeFloat(price);
		dest.writeInt(quantity);
	}

	public String getName() {
		return name;
	}

	public float getPrice() {
		return price;
	}

	public int getQuantity() {
		return quantity;
	}

	public void setQuantity(int quantity) {
		this.quantity = quantity;
	}
}
