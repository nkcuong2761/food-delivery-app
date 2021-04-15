package com.example.demoapp;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;

public class DBHelper extends SQLiteOpenHelper {
	private static final String TAG = DBHelper.class.getSimpleName();

	private static final int DATABASE_VERSION = 1;
	private static final String DATABASE_NAME = "orders_manager.db";

	// Table name
	public static final String TABLE_NAME = "ORDERS_HISTORY";

	// Table columns
	public static final String _ID = "_id";
	public static final String NUM_ITEMS = "num_items";
	public static final String BILL = "bill";

	public DBHelper(Context context) {
		super(context, DATABASE_NAME, null, DATABASE_VERSION);
	}

	@Override
	public void onCreate(SQLiteDatabase db) {
		final String CREATE_TABLE = "CREATE TABLE " + TABLE_NAME + " (" +
				_ID + " INTEGER PRIMARY KEY AUTOINCREMENT, " +
				NUM_ITEMS + " TEXT NOT NULL, " +
				BILL + " TEXT NOT NULL);";

		db.execSQL(CREATE_TABLE);
		Log.d(TAG, "--------Database Created Successfully");
	}

	@Override
	public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
		db.execSQL("DROP TABLE IF EXISTS " + TABLE_NAME);
		onCreate(db);
		Log.d(TAG, "--------Database Upgraded");
	}
}
