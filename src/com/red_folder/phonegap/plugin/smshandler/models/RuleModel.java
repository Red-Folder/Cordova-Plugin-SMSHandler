package com.red_folder.phonegap.plugin.smshandler.models;

public class RuleModel {
	private long mId = -1;
	private String mOrigin = "";
	private String mMessageBody = "";
	
	public RuleModel() {
	}
	
	public RuleModel(long id, String origin, String messageBody) {
		this.mId = id;
		this.mOrigin = origin;
		this.mMessageBody = messageBody;
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
	
	public void setMessage(String messageBody) {
		this.mMessageBody = messageBody;
	}
}

