package com.red_folder.phonegap.plugin.smshandler;

import org.apache.cordova.api.CallbackContext;
import org.apache.cordova.api.CordovaPlugin;
import org.json.JSONArray;
import org.json.JSONObject;

import android.util.Log;

import com.red_folder.phonegap.plugin.smshandler.db.DBMessageProvider;
import com.red_folder.phonegap.plugin.smshandler.db.DBRulesProvider;
import com.red_folder.phonegap.plugin.smshandler.interfaces.IMessageProvider;
import com.red_folder.phonegap.plugin.smshandler.interfaces.IRulesProvider;
import com.red_folder.phonegap.plugin.smshandler.models.MessageModel;
import com.red_folder.phonegap.plugin.smshandler.models.RuleModel;

public class SMSHandlerPlugin extends CordovaPlugin {
	private static String TAG = SMSHandlerPlugin.class.getSimpleName();
	
	@Override
	public boolean execute(final String action, final JSONArray data, final CallbackContext callback) {
		Log.d(TAG, "Execute method started with action '" + action + "'");
		
		boolean result = true;
		
		try {
			if (action.equals("getAllRules")) callback.success(getAllRules(new DBRulesProvider(this.cordova.getActivity())));
			
			else if (action.equals("deleteRules")) callback.success(deleteRules(new DBRulesProvider(this.cordova.getActivity()), data));
			
			else if (action.equals("deleteAllRules")) callback.success(deleteAllRules(new DBRulesProvider(this.cordova.getActivity())));
			
			else if (action.equals("addRules")) callback.success(addRules(new DBRulesProvider(this.cordova.getActivity()), data));
			
			else if (action.equals("getMessages")) callback.success(getMessages(new DBMessageProvider(this.cordova.getActivity()), data));
			
			else if (action.equals("getAllUnreadMessages")) callback.success(getAllUnreadMessages(new DBMessageProvider(this.cordova.getActivity())));
			
			else if (action.equals("getAllMessages")) callback.success(getAllMessages(new DBMessageProvider(this.cordova.getActivity())));
			
			else if (action.equals("deleteMessages")) callback.success(deleteMessages(new DBMessageProvider(this.cordova.getActivity()), data));
			
			else if (action.equals("deleteAllMessages")) callback.success(deleteAllMessages(new DBMessageProvider(this.cordova.getActivity())));
			
			else if (action.equals("markMessagesAsRead")) callback.success(markMessagesAsRead(new DBMessageProvider(this.cordova.getActivity()), data));
			
			else if (action.equals("markAllMessagesAsRead")) callback.success(markAllMessagesAsRead(new DBMessageProvider(this.cordova.getActivity())));

			else {
				Log.d(TAG, "Action not found");
				callback.error("Action '" + action + "' not found");
				result = false;
			}
		} catch (Exception ex) {
			Log.d(TAG, "Exception occurred");
			
			Log.d(TAG, ex.toString());
			ex.printStackTrace();
			
			callback.error(ex.getMessage());
			result = false;
		}

		return result;
	}
	
	private JSONObject getAllRules(IRulesProvider provider) throws Exception {
		Log.d(TAG, "Started getAllRules");
		JSONObject result = new JSONObject();
		result.put("Rules", ruleModelsToJSONArray(provider.getRules(null)));
		Log.d(TAG, "Returning getAllRules - " + result.toString());
		return result;
	}
	
	private JSONObject deleteRules(IRulesProvider provider, JSONArray data) throws Exception  {
		JSONObject result = new JSONObject();

		JSONArray rules = new JSONArray();
		
		if (data.length() > 0) {
			for (int i = 0; i < data.length(); i++) {
				JSONObject rule = data.optJSONObject(i);
				if (rule != null) {
					rule.put("Deleted", provider.deleteRule(new RuleModel(rule.optLong("Id"), "", "")));
					rules.put(rule);
				}
			}
		}
		
		result.put("Rules", rules);
		
		return result;
	}
	
	private JSONObject deleteAllRules(IRulesProvider provider) throws Exception {
		provider.deleteRule(null);
		
		return new JSONObject();
	}
	
	private JSONObject addRules(IRulesProvider provider, JSONArray data) throws Exception {
		Log.d(TAG, "Started addRules");
		
		JSONObject result = new JSONObject();

		Log.d(TAG, "Contents of data: " + data.toString());
		JSONArray rules = new JSONArray();
		
		Log.d(TAG, "Found " + data.length() + " rules");
		if (data.length() > 0) {
			
			JSONObject rulesObject = data.optJSONObject(0);
			
			if (rulesObject != null) {
				Log.d(TAG, "Contents of rulesObject: " + rulesObject.toString());

				JSONArray rulesArray = rulesObject.getJSONArray("Rules");
				
				if (rulesArray != null) {
					for (int i = 0; i < rulesArray.length(); i++) {
						JSONObject rule = rulesArray.optJSONObject(i);
						if (rule != null) {
							rule.put("Added", provider.addRule(new RuleModel(0, rule.optString("Origin"), rule.optString("MessageBody"))));
							rules.put(rule);
							Log.d(TAG, "Rules added");
						}
					}
				}
			}
		}
		
		result.put("Rules", rules);
		
		Log.d(TAG, "Ended addRules");
		return result;
	}
	
	private JSONObject getMessages(IMessageProvider provider, JSONArray data) throws Exception {
		JSONObject result = new JSONObject();
		result.put("Messages", messageModelsToJSONArray(new MessageModel[] {provider.get(new MessageModel(data.optLong(0), "", "", false))}));
		return result;
	}

	private JSONObject getAllUnreadMessages(IMessageProvider provider) throws Exception {
		JSONObject result = new JSONObject();
		result.put("Messages", messageModelsToJSONArray(provider.getUnread()));
		return result;
	}

	private JSONObject getAllMessages(IMessageProvider provider) throws Exception {
		JSONObject result = new JSONObject();
		result.put("Messages", messageModelsToJSONArray(provider.getAll()));
		return result;
	}
	
	private JSONObject deleteMessages(IMessageProvider provider, JSONArray data) throws Exception {
		JSONObject result = new JSONObject();

		JSONArray messages = new JSONArray();
		
		if (data.length() > 0) {
			for (int i = 0; i < data.length(); i++) {
				JSONObject message = data.optJSONObject(i);
				if (message != null) {
					message.put("Deleted", provider.delete(new MessageModel(message.optLong("Id"), "", "", false)));
					messages.put(message);
				}
			}
		}
		
		result.put("Messages", messages);
		
		return result;
	}

	private JSONObject deleteAllMessages(IMessageProvider provider) throws Exception {
		provider.deleteAll();
		
		return new JSONObject();
	}

	private JSONObject markMessagesAsRead(IMessageProvider provider, JSONArray data) throws Exception {
		JSONObject result = new JSONObject();

		JSONArray messages = new JSONArray();
		
		if (data.length() > 0) {
			for (int i = 0; i < data.length(); i++) {
				JSONObject message = data.optJSONObject(i);
				if (message != null) {
					message.put("Marked", provider.markAsRead(new MessageModel(message.optLong("Id"), "", "", false)));
					messages.put(message);
				}
			}
		}
		
		result.put("Messages", messages);
		
		return result;
	}
	
	private JSONObject markAllMessagesAsRead(IMessageProvider provider) throws Exception {
		provider.markAllRead();
		
		return new JSONObject();
	}

	private JSONArray ruleModelsToJSONArray(RuleModel[] models) throws Exception {
		JSONArray rules = new JSONArray();
		for (int i = 0; i < models.length; i++) {
			JSONObject rule = new JSONObject();
			rule.put("Id", models[i].getId());
			rule.put("Origin", models[i].getOrigin());
			rule.put("MessageBody", models[i].getMessageBody());
			rules.put(rule);
		}
		
		return rules;
	}
	
	private JSONArray messageModelsToJSONArray(MessageModel[] models) throws Exception {
		JSONArray rules = new JSONArray();
		for (int i = 0; i < models.length; i++) {
			JSONObject rule = new JSONObject();
			rule.put("Id", models[i].getId());
			rule.put("Origin", models[i].getOrigin());
			rule.put("MessageBody", models[i].getMessageBody());
			rule.put("Read", models[i].getRead() ? 1 : 0);
			rules.put(rule);
		}
		
		return rules;
	}

}
