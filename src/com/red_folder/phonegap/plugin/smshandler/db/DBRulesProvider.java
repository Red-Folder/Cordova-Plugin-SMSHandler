package com.red_folder.phonegap.plugin.smshandler.db;

import java.util.ArrayList;
import java.util.List;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.SQLException;
import android.database.sqlite.SQLiteDatabase;

import com.red_folder.phonegap.plugin.smshandler.interfaces.IRulesProvider;
import com.red_folder.phonegap.plugin.smshandler.models.RuleModel;
import com.red_folder.phonegap.plugin.smshandler.models.MessageModel;

public class DBRulesProvider implements IRulesProvider {

	public static String TABLE_NAME = "Rules";
	public static String COLUMN_ID = "_id";
	public static String COLUMN_ORIGIN = "Origin";
	public static String COLUMN_MESSAGEBODY = "MessageBody";
	
	private static String[] ALL_COLUMNS = { COLUMN_ID, COLUMN_ORIGIN, COLUMN_MESSAGEBODY };
	
	// Database fields
	private SQLiteDatabase mDB;
	private SMSHandlerSQLiteHelper mHelper;

	public DBRulesProvider(Context context) {
		this.mHelper = new SMSHandlerSQLiteHelper(context);
	}

	public void open() throws SQLException {
		this.mDB = this.mHelper.getWritableDatabase();
	}

	public void close() {
		this.mHelper.close();
	}
	
	@Override
	public boolean ruleExists(MessageModel model) {
		boolean matched = false;
		
		// Get all rules
		RuleModel[] rules = getRules(null);
		
		for (int i=0; i < rules.length && !matched; i++) {
			RuleModel rule = rules[i];
			
			if (rule.getOrigin().length() > 0 && rule.getOrigin().length() > 0) {
				matched = model.getOrigin().matches(rule.getOrigin()) && model.getMessageBody().matches(rule.getMessageBody());
			} else if (rule.getOrigin().length() > 0) {
				matched = model.getOrigin().matches(rule.getOrigin());
			} else if (rule.getMessageBody().length() > 0) {
				matched = model.getMessageBody().matches(rule.getMessageBody());
			}
		}
		
		return matched;
	}

	@Override
	public boolean addRule(RuleModel model) {
		boolean result = false;
		
		try {
			ContentValues values = new ContentValues();
			values.put(COLUMN_ORIGIN, model.getOrigin());
			values.put(COLUMN_MESSAGEBODY, model.getMessageBody());
	    
			open();
	    	long insertId = this.mDB.insert(TABLE_NAME, null, values);
	    	close();
	    	
	    	if (insertId > 0)
	    		result = true;
	    	
		} catch (Exception ex) {
			close();
		}
	    
	    return result;
	}

	@Override
	public boolean deleteRule(RuleModel model) {
		boolean result = false;
		
		try {
			int recordsDeleted = 0;
			
			open();
			if (model == null)
				recordsDeleted = this.mDB.delete(TABLE_NAME, null, null);
			else 
				recordsDeleted = this.mDB.delete(TABLE_NAME, COLUMN_ID + " = " + model.getId(), null);
			close();
			
			if (recordsDeleted > 0) {
				result = true;
			}
	        
		} catch (Exception ex) {
			close();
		}
		
		return result;
	}

	@Override
	public RuleModel getRule(RuleModel model) {
		RuleModel[] rules = null;
		
		try {
			open();
			Cursor cursor = this.mDB.query(TABLE_NAME, ALL_COLUMNS, COLUMN_ID + " = " + model.getId(), null, null, null, null);
			
			rules = cursorToArray(cursor);
			close();
		} catch (Exception ex) {
			close();
		}
		
		if (rules == null || rules.length == 0)
			return null;
		else
			// Should only be 1, but we return first just to be sure
			return rules[1];		
	}

	@Override
	public RuleModel[] getRules(RuleModel model) {
		RuleModel[] rules = null;
		
		try {
			Cursor cursor;

			open();
			
			// Return all
			if (model == null)
				cursor = this.mDB.query(TABLE_NAME, ALL_COLUMNS, null, null, null, null, null);
			else {
				if (model.getOrigin().length() > 0 && model.getMessageBody().length() > 0) {
					cursor = this.mDB.query(TABLE_NAME, ALL_COLUMNS, COLUMN_ORIGIN + " = ? and " + COLUMN_MESSAGEBODY + " = ?", new String[] {model.getOrigin(), model.getMessageBody()}, null, null, null);
				} else if (model.getOrigin().length() > 0) {
					cursor = this.mDB.query(TABLE_NAME, ALL_COLUMNS, COLUMN_ORIGIN + " = ?", new String[] {model.getOrigin()}, null, null, null);
				} else if (model.getMessageBody().length() > 0) {
					cursor = this.mDB.query(TABLE_NAME, ALL_COLUMNS, COLUMN_MESSAGEBODY + " = ?", new String[] {model.getMessageBody()}, null, null, null);
				} else {
					// Default to all
					cursor = this.mDB.query(TABLE_NAME, ALL_COLUMNS, null, null, null, null, null);
				}
			}

			rules = cursorToArray(cursor);
			close();
		} catch (Exception ex) {
			close();
		}
		
		return rules;
	}
	
	private RuleModel[] cursorToArray(Cursor cursor) {
	    List<RuleModel> rules = new ArrayList<RuleModel>();

	    cursor.moveToFirst();
	    while (!cursor.isAfterLast()) {
	    	RuleModel rule = cursorToModel(cursor);
	        rules.add(rule);
	        cursor.moveToNext();
	    }
	    
	    // Make sure to close the cursor
	    cursor.close();
	    return rules.toArray(new RuleModel[rules.size()]);
	}

	private RuleModel cursorToModel(Cursor cursor) {
		RuleModel model = new RuleModel();
	    model.setId(cursor.getLong(0));
	    model.setOrigin(cursor.getString(1));
	    model.setMessage(cursor.getString(2));
	    return model;
	}	
	
	public static void UpgradeDatabase(SQLiteDatabase db, int oldVersion, int newVersion) {
		// Upgrades for empty database
		if (oldVersion < 1)
			db.execSQL("create table " + TABLE_NAME + "(" +
							COLUMN_ID + " integer primary key autoincrement, " + 
							COLUMN_ORIGIN + " text, " +
							COLUMN_MESSAGEBODY + " text" +
						");");
	}

}
