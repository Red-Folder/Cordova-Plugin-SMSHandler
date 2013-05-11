package com.red_folder.phonegap.plugin.smshandler.action;

import android.app.Notification;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.Context;
import android.content.Intent;

import com.red_folder.phonegap.plugin.smshandler.interfaces.INewMessage;
import com.red_folder.phonegap.sample.R;

public class NewMessageNotification implements INewMessage {
	
	private static String TITLE = "New Message";
	private static String MESSAGE = "A new SMS has been received";
	
	private Context mContext;
	private Class<?> mClassToRun;
	
	public NewMessageNotification(Context context, Class<?> classToRun) {
		this.mContext = context;
		this.mClassToRun = classToRun;
	}
	
	@Override
	public Boolean Execute() {
		int icon = R.drawable.ic_stat_notification;
        long when = System.currentTimeMillis();
	        
        Notification notification = new Notification(icon, TITLE, when);
			
        notification.flags |= Notification.FLAG_AUTO_CANCEL;
		Intent notificationIntent = new Intent(this.mContext, this.mClassToRun);
			
        PendingIntent contentIntent = PendingIntent.getActivity(this.mContext, 0, notificationIntent, 0);
        notification.setLatestEventInfo(this.mContext, TITLE, MESSAGE, contentIntent);
	        
        NotificationManager nm = (NotificationManager)this.mContext.getSystemService(Context.NOTIFICATION_SERVICE);
        nm.notify(1, notification);
        
        return true;
	}

}
