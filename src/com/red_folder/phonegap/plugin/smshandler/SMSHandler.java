package com.red_folder.phonegap.plugin.smshandler;

import com.red_folder.phonegap.plugin.smshandler.interfaces.INewMessage;
import com.red_folder.phonegap.plugin.smshandler.interfaces.IRulesProvider;
import com.red_folder.phonegap.plugin.smshandler.interfaces.ISMSHandler;
import com.red_folder.phonegap.plugin.smshandler.interfaces.IMessageProvider;
import com.red_folder.phonegap.plugin.smshandler.models.MessageModel;

import android.util.Log;

public class SMSHandler implements ISMSHandler {
	private static String TAG = SMSHandler.class.getSimpleName();

	private IRulesProvider mRulesProvider;
	private IMessageProvider mSMSProvider;
	private INewMessage mNewMessage;
	
	public SMSHandler(IRulesProvider rulesProvider, IMessageProvider smsProvider, INewMessage newMessage) {
		this.mRulesProvider = rulesProvider;
		this.mSMSProvider = smsProvider;
		this.mNewMessage = newMessage;
	}
	
	@Override
	public boolean willHandle(MessageModel model) {
		Log.d(TAG, "willHandle called");
		
		return this.mRulesProvider.ruleExists(model);
	}

	@Override
	public boolean save(MessageModel model) {
		Log.d(TAG, "save called");
		
		return this.mSMSProvider.saveSMS(model);
	}

	@Override
	public boolean onNewMssage(MessageModel model) {
		if (this.mNewMessage != null)
			return this.mNewMessage.Execute();
		else
			return true;
	}

}
