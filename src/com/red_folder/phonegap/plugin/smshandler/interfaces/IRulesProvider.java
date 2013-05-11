package com.red_folder.phonegap.plugin.smshandler.interfaces;

import com.red_folder.phonegap.plugin.smshandler.models.RuleModel;
import com.red_folder.phonegap.plugin.smshandler.models.MessageModel;

public interface IRulesProvider {
	
	public boolean ruleExists(MessageModel model);
	public boolean addRule(RuleModel model);
	public boolean deleteRule(RuleModel model);
	public RuleModel getRule(RuleModel model);
	public RuleModel[] getRules(RuleModel model);

}
