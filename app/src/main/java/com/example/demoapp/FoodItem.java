package com.example.demoapp;

import android.os.Parcel;
import android.os.Parcelable;

public class FoodItem implements Parcelable {
	private String image;
	private String name;
	private double price;
	private String description;
	private String longDescription;

	public FoodItem(String image, String name, double price, String description, String longDescription) {
		this.image = image;
		this.name = name;
		this.price = price;
		this.description = description;
		this.longDescription = longDescription;
	}

	protected FoodItem(Parcel in) {
		image = in.readString();
		name = in.readString();
		price = in.readDouble();
		description = in.readString();
		longDescription = in.readString();
	}

	public static final Creator<FoodItem> CREATOR = new Creator<FoodItem>() {
		@Override
		public FoodItem createFromParcel(Parcel in) {
			return new FoodItem(in);
		}

		@Override
		public FoodItem[] newArray(int size) {
			return new FoodItem[size];
		}
	};

	public String getImage() {
		return image;
	}

	public String getName() {
		return name;
	}

	public double getPrice() {
		return price;
	}

	public String getDescription() {
		return description;
	}

	public String getLongDescription() {
		return longDescription;
	}

	@Override
	public int describeContents() {
		return 0;
	}

	@Override
	public void writeToParcel(Parcel dest, int flags) {
		dest.writeString(image);
		dest.writeString(name);
		dest.writeDouble(price);
		dest.writeString(description);
		dest.writeString(longDescription);
	}
}
