package com.red_folder.phonegap.plugin.smshandler.db;

import java.util.ArrayList;
import java.util.List;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.SQLException;
import android.database.sqlite.SQLiteDatabase;

import com.red_folder.phonegap.plugin.smshandler.interfaces.IMessageProvider;
import com.red_folder.phonegap.plugin.smshandler.models.MessageModel;

public class DBMessageProvider implements IMessageProvider {

	public static String TABLE_NAME = "Message";
	public static String COLUMN_ID = "_id";
	public static String COLUMN_ORIGIN = "Origin";
	public static String COLUMN_MESSAGEBODY = "MessageBody";
	public static String COLUMN_READ = "Read";
	
	private static String[] ALL_COLUMNS = { COLUMN_ID, COLUMN_ORIGIN, COLUMN_MESSAGEBODY, COLUMN_READ };
	
	// Database fields
	private SQLiteDatabase mDB;
	private SMSHandlerSQLiteHelper mHelper;

	public DBMessageProvider(Context context) {
		this.mHelper = new SMSHandlerSQLiteHelper(context);
	}

	
	public void open() throws SQLException {
		this.mDB = this.mHelper.getWritableDatabase();
	}

	public void close() {
		try {
			this.mHelper.close();
		} catch (Exception ex) {
			
		}
	}
	
	
	

	public Boolean saveSMS(MessageModel model) {
		boolean result = false;
		
		try {
			ContentValues values = new ContentValues();
			values.put(COLUMN_ORIGIN, model.getOrigin());
			values.put(COLUMN_MESSAGEBODY, model.getMessageBody());
			values.put(COLUMN_READ, model.getRead() ? 1 : 0);

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

	public Boolean markAsRead(MessageModel model) {
		ContentValues values = new ContentValues();
		values.put(COLUMN_READ, 1);
		
		int recUpdated = 0;
		try {
			open();
			recUpdated = this.mDB.update(TABLE_NAME, values, COLUMN_ID + " = ?", new String[] { String.valueOf(model.getId()) });
			close();
		} catch (Exception ex) {
			close();
		}
		
		if (recUpdated > 0)
			return true;
		else
			return false;
	}

	@Override
	public Boolean markAllRead() {
		ContentValues values = new ContentValues();
		values.put(COLUMN_READ, 1);
		
		int recUpdated = 0;
		try {
			open();
			recUpdated = this.mDB.update(TABLE_NAME, values, null, null);
			close();
		} catch (Exception ex) {
			close();
		}
		
		if (recUpdated > 0)
			return true;
		else
			return false;
	}

	@Override
	public Boolean markAsUnRead(MessageModel model) {
		ContentValues values = new ContentValues();
		values.put(COLUMN_READ, 0);
		
		int recUpdated = 0;
		try {
			open();
			recUpdated = this.mDB.update(TABLE_NAME, values, COLUMN_ID + " = ?", new String[] { String.valueOf(model.getId()) });
			close();
		} catch (Exception ex) {
			close();
			
		}
		if (recUpdated > 0)
			return true;
		else
			return false;
	}

	@Override
	public Boolean markAllUnRead(MessageModel model) {
		ContentValues values = new ContentValues();
		values.put(COLUMN_READ, 0);
		
		int recUpdated = 0;
		try {
			open();
			recUpdated = this.mDB.update(TABLE_NAME, values, null, null);
			close();
		} catch (Exception ex) {
			close();
		}
		
		if (recUpdated > 0)
			return true;
		else
			return false;
	}

	@Override
	public Boolean delete(MessageModel model) {
		boolean result = false;
		
		try {
			long id = model.getId();
	    
			open();
			int recordsDeleted = this.mDB.delete(TABLE_NAME, COLUMN_ID + " = " + id, null);
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
	public Boolean deleteAll() {
		boolean result = false;
		
		try {
			open();
			int recordsDeleted = this.mDB.delete(TABLE_NAME, null, null);
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
	public MessageModel get(MessageModel model) {
		MessageModel[] list = null;
		
		try {
			open();
			Cursor cursor = this.mDB.query(TABLE_NAME, ALL_COLUMNS, COLUMN_ID + " = " + model.getId(), null, null, null, null);
		
			list = cursorToArray(cursor);
			close();
		} catch (Exception ex) {
			close();
		}
		
		if (list == null || list.length == 0)
			return null;
		else
			// Should only be 1, but we return first just to be sure
			return list[1];		
	}

	@Override
	public MessageModel[] getUnread() {
		MessageModel[] list = null;
		
		try {
			open();
			list = cursorToArray(this.mDB.query(TABLE_NAME, ALL_COLUMNS, COLUMN_READ + " = 0", null, null, null, COLUMN_ID));
			close();
		} catch (Exception ex) {
			close();
		}
		
		return list;
	}

	@Override
	public MessageModel[] getAll() {
		MessageModel[] list = null;
		
		try {
			open();
			list = cursorToArray(this.mDB.query(TABLE_NAME, ALL_COLUMNS, null, null, null, null, COLUMN_ID));
			close();
		} catch (Exception ex) {
			close();
		}
		
		return list;
	}

	private MessageModel[] cursorToArray(Cursor cursor) {
	    List<MessageModel> list = new ArrayList<MessageModel>();

	    cursor.moveToFirst();
	    while (!cursor.isAfterLast()) {
	    	MessageModel rec = cursorToModel(cursor);
	        list.add(rec);
	        cursor.moveToNext();
	    }
	    
	    // Make sure to close the cursor
	    cursor.close();
	    return list.toArray(new MessageModel[list.size()]);
	}

	private MessageModel cursorToModel(Cursor cursor) {
		MessageModel model = new MessageModel();
	    model.setId(cursor.getLong(0));
	    model.setOrigin(cursor.getString(1));
	    model.setMessageBody(cursor.getString(2));
	    if (cursor.getInt(3) == 1)
	    	model.setRead(true);
	    else
	    	model.setRead(false);
	    
	    return model;
	}	

	
	public static void UpgradeDatabase(SQLiteDatabase db, int oldVersion, int newVersion) {
		// Upgrades for empty database
		if (oldVersion < 1)
			db.execSQL("create table " + TABLE_NAME + "( " +
							COLUMN_ID + " integer primary key autoincrement, " + 
							COLUMN_ORIGIN + " text, " +
							COLUMN_MESSAGEBODY + " text, " +
							COLUMN_READ + " integer not null " +
						");");

	}
}
