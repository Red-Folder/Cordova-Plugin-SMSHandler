package com.red_folder.phonegap.plugin.smshandler.models;

public class MessageModel {
	private long mId = -1;
	private String mOrigin = "";
	private String mMessageBody = "";
	private Boolean mRead = false;
	
	public MessageModel() {
		
	}
	
	public MessageModel(String origin, String messageBody) {
		this.mOrigin = origin;
		this.mMessageBody = messageBody;
	}
	
	public MessageModel(long id, String origin, String messageBody, Boolean read) {
		this.mId = id;
		this.mOrigin = origin;
		this.mMessageBody = messageBody;
		this.mRead = read;
	}
	
	public long getId() {
		return this.mId;
	}
	
	public void setId(long id) {
		this.mId = id;
	}
	
	public String getOrigin() {
		return this.mOrigin;
	}
	
	public void setOrigin(String origin) {
		this.mOrigin = origin;
	}
	
	public String getMessageBody() {
		return this.mMessageBody;
	}
	
	public void setMessageBody(String messageBody) {
		this.mMessageBody = messageBody;
	}
	
	public Boolean getRead() {
		return this.mRead;
	}
	
	public void setRead(Boolean read) {
		this.mRead = read;
	}
}

