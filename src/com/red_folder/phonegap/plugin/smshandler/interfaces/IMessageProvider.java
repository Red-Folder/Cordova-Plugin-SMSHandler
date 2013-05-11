package com.red_folder.phonegap.plugin.smshandler.interfaces;

import com.red_folder.phonegap.plugin.smshandler.models.MessageModel;

public interface IMessageProvider {

	public Boolean saveSMS(MessageModel model);
	
	public Boolean markAsRead(MessageModel model);
	public Boolean markAllRead();
	public Boolean markAsUnRead(MessageModel model);
	public Boolean markAllUnRead(MessageModel model);
	
	public Boolean delete(MessageModel model);
	public Boolean deleteAll();
	
	public MessageModel get(MessageModel model);
	public MessageModel[] getUnread();
	public MessageModel[] getAll();
	
}
