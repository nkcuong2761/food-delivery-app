package com.example.demoapp;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.SQLException;
import android.database.sqlite.SQLiteDatabase;

public class DBManager {
	private DBHelper dbHelper;
	private Context context;
	private SQLiteDatabase database;

	public DBManager(Context c) {
		context = c;
	}

	public DBManager open() throws SQLException {
		dbHelper = new DBHelper(context);
		database = dbHelper.getWritableDatabase();
		if (dbHelper == null)
			System.out.println("------DMM NO NULL M OI------");
		else
			System.out.println("------HMMMM------");
		return this;
	}

	public void close() {
		dbHelper.close();
	}

	public void insert(String numItems, String bill) {
		ContentValues contentValues = new ContentValues();
		contentValues.put(DBHelper.NUM_ITEMS, numItems);
		contentValues.put(DBHelper.BILL, bill);
		database.insert(DBHelper.TABLE_NAME, null, contentValues);
//		System.out.println("---------INSERT R DAY DMM-------");
	}

	public Cursor fetch() {
		String[] columns = new String[] {DBHelper._ID, DBHelper.NUM_ITEMS, DBHelper.BILL};
		Cursor cursor = database.query(DBHelper.TABLE_NAME, columns, null, null, null, null, null);
		if (cursor != null)
			cursor.moveToFirst();
		return cursor;
	}

	public void delete(long _id) {
		database.delete(DBHelper.TABLE_NAME, DBHelper._ID + "=" + _id, null);
	}
}
