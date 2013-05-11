package com.red_folder.phonegap.plugin.smshandler.interfaces;

import com.red_folder.phonegap.plugin.smshandler.models.MessageModel;

public interface ISMSHandler {
	
	public boolean willHandle(MessageModel model);
	
	public boolean save(MessageModel model);
	
	public boolean onNewMssage(MessageModel model);

}
