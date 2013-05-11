package com.red_folder.phonegap.plugin.smshandler;

import com.red_folder.phonegap.plugin.smshandler.action.NewMessageNotification;
import com.red_folder.phonegap.plugin.smshandler.db.DBRulesProvider;
import com.red_folder.phonegap.plugin.smshandler.db.DBMessageProvider;
import com.red_folder.phonegap.plugin.smshandler.interfaces.ISMSHandler;
import com.red_folder.phonegap.plugin.smshandler.models.MessageModel;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.telephony.SmsMessage;
import android.util.Log;

public class SMSReceiver extends BroadcastReceiver {
	private static String TAG = SMSReceiver.class.getSimpleName();
	
	private static Class<?> CLASS_TO_RUN = com.red_folder.phonegap.sample.SMSHandlerActivity.class;
	
	
	@Override
	public void onReceive(Context context, Intent intent) {

		ISMSHandler handler = new SMSHandler(	new DBRulesProvider(context), 
												new DBMessageProvider(context), 
												new NewMessageNotification(context, CLASS_TO_RUN));

		boolean willHandle = false;
		
		// Parse the SMS.
        Bundle bundle = intent.getExtras();
        MessageModel[] msgs = null;
        String str = "";
        
        if (bundle != null)
        {
            // Retrieve the SMS.
            Object[] pdus = (Object[]) bundle.get("pdus");
            msgs = new MessageModel[pdus.length];

            Log.d(TAG, "Decoding " + msgs.length + " messages");
            for (int i=0; i<msgs.length; i++)
            {
            	Log.d(TAG, "Decoding - message " + (i+1));
            	
            	SmsMessage msg = SmsMessage.createFromPdu((byte[])pdus[i]);
            	
            	String from = msg.getOriginatingAddress();
            	String message = msg.getMessageBody();
            	
            	MessageModel model = new MessageModel(from, message);
            	
            	if (!willHandle) {
                    Log.d(TAG, "Checking if Handler will handle");
            		if (handler.willHandle(model)) {
                        Log.d(TAG, "Handler will handle");

                        willHandle = true;
            			
            			// Abort the broadcast as soon as possible to avoid it being passed on
            	        this.abortBroadcast();
            		}
            	}
            	
                msgs[i] = model;
                // In case of a particular App / Service.
                //if(msgs[i].getOriginatingAddress().equals("+91XXX"))
                //{
                str += "SMS from " + from;
                str += " :";
                str += message;
                str += "\n";
                //}
                
                Log.d(TAG, str);
            }
            
            if (willHandle) {
            	Log.d(TAG, "Saving " + msgs.length + " messages");
            	for (int i=0; i < msgs.length; i++) {
            		if (handler.save(msgs[i])) {
            			Log.d(TAG, "Message " + (i+1) + " saved");
            			
            			handler.onNewMssage(msgs[i]);
            		} else {
            			Log.d(TAG, "Message " + (i+1) + " not saved");
            		}
            	}
            }
        }
        
		
	}

}


