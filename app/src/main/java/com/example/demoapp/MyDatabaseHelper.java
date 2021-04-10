package com.example.demoapp;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

public class MyDatabaseHelper extends SQLiteOpenHelper {

	private static final int DATABASE_VERSION = 1;

	private static final String DATABASE_NAME = "Order_Manager";

	public MyDatabaseHelper(Context context) {
		super(context, DATABASE_NAME, null, DATABASE_VERSION);
	}

	// Execute a single SQL statement that is NOT a SELECT or any other SQL statement that returns data
	public void queryData(String sql) {
		SQLiteDatabase database = getWritableDatabase();
		database.execSQL(sql);
	}

	// Insert data into database
	public void insertData(OrderItem orderItem, float totalBill) {
		SQLiteDatabase database = getWritableDatabase();
	}

	@Override
	public void onCreate(SQLiteDatabase db) {

	}

	@Override
	public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {

	}
}
