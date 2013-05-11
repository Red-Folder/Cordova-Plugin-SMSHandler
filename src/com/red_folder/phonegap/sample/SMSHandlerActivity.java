package com.red_folder.phonegap.sample;

import org.apache.cordova.DroidGap;
import org.apache.cordova.api.LOG;

import android.os.Bundle;

public class SMSHandlerActivity extends DroidGap {
	private static String TAG = SMSHandlerActivity.class.getSimpleName();
	
    /** Called when the activity is first created. */
    @Override
    public void onCreate(Bundle savedInstanceState) {
    	
		LOG.d(TAG, "onCreate called");

		LOG.d(TAG, "Creating the Phonegap activity");
        super.onCreate(savedInstanceState);
        setContentView(R.layout.main);
        
        super.loadUrl("file:///android_asset/www/index.html");
    }
}