package com.red_folder.phonegap.plugin.smshandler.db;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;

public class SMSHandlerSQLiteHelper extends SQLiteOpenHelper {
	private static String TAG = SMSHandlerSQLiteHelper.class.getSimpleName();
	
	private static final String DATABASE_NAME = "SMSHandler.db";
	private static final int DATABASE_VERSION = 1;

	public SMSHandlerSQLiteHelper(Context context) {
		super(context, DATABASE_NAME, null, DATABASE_VERSION);
	}

	@Override
	public void onCreate(SQLiteDatabase database) {
		onUpgrade(database, 0, 1);
	}

	@Override
	public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
		Log.d(TAG, "Upgrading database from version " + oldVersion + " to " + newVersion);
		
		DBMessageProvider.UpgradeDatabase(db, oldVersion, newVersion);
		DBRulesProvider.UpgradeDatabase(db, oldVersion, newVersion);
	}
}
