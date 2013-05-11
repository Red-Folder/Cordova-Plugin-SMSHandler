/*
 * Copyright 2013 Red Folder Consultancy Ltd
 *   
 * Licensed under the Apache License, Version 2.0 (the "License");   
 * you may not use this file except in compliance with the License.   
 * You may obtain a copy of the License at       
 * 
 * 	http://www.apache.org/licenses/LICENSE-2.0   
 *
 * Unless required by applicable law or agreed to in writing, software   
 * distributed under the License is distributed on an "AS IS" BASIS,   
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.   
 * See the License for the specific language governing permissions and   
 * limitations under the License.
 */

/*
 * Constructor
 */
function CreateSMSHandler(require, exports, module) {
	var exec = require("cordova/exec");
	
	var SMSHandler = function () {
	};

	var SMSHandlerError = function (code, message) {
		this.code = code || null;
		this.message = message || null;
	};	

	SMSHandler.prototype.getAllRules = function(successCallback, failureCallback) {
		return exec(successCallback,      
					failureCallback,      
					'SMSHandlerPlugin',      
					'getAllRules',      
					[]);
	};
	
	SMSHandler.prototype.deleteRules = function(rules, successCallback, failureCallback) { 
		return exec(successCallback,      
					failureCallback,      
					'SMSHandlerPlugin',      
					'deleteRules',      
					[rules]);
	};
	
	SMSHandler.prototype.deleteAllRules = function(successCallback, failureCallback) { 
		return exec(successCallback,      
					failureCallback,      
					'SMSHandlerPlugin',      
					'deleteAllRules',      
					[]);
	};
	
	SMSHandler.prototype.addRules = function(rules, successCallback, failureCallback) { 
		return exec(successCallback,      
					failureCallback,      
					'SMSHandlerPlugin',      
					'addRules',      
					[rules]);
	};
	
	SMSHandler.prototype.getMessages = function(messages, successCallback, failureCallback) { 
		return exec(successCallback,      
					failureCallback,      
					'SMSHandlerPlugin',      
					'getMessages',      
					[messages]);
	};
	
	SMSHandler.prototype.getAllUnreadMessages = function(successCallback, failureCallback) { 
		return exec(successCallback,      
					failureCallback,      
					'SMSHandlerPlugin',      
					'getAllUnreadMessages',      
					[]);
	};
	
	SMSHandler.prototype.getAllMessages = function(successCallback, failureCallback) { 
		return exec(successCallback,      
					failureCallback,      
					'SMSHandlerPlugin',      
					'getAllMessages',      
					[]);
	};
	
	SMSHandler.prototype.deleteMessages = function(messages, successCallback, failureCallback) { 
		return exec(successCallback,      
					failureCallback,      
					'SMSHandlerPlugin',      
					'deleteMessages',      
					[messages]);
	};
	
	SMSHandler.prototype.deleteAllMessages = function(successCallback, failureCallback) { 
		return exec(successCallback,      
					failureCallback,      
					'SMSHandlerPlugin',      
					'deleteAllMessages',      
					[]);
	};
	
	SMSHandler.prototype.markMessagesAsRead = function(messages, successCallback, failureCallback) { 
		return exec(successCallback,      
					failureCallback,      
					'SMSHandlerPlugin',      
					'markMessagesAsRead',      
					[messages]);
	};
	
	SMSHandler.prototype.markAllMessagesAsRead = function(successCallback, failureCallback) { 
		return exec(successCallback,      
					failureCallback,      
					'SMSHandlerPlugin',      
					'markAllMessagesAsRead',      
					[]);
	};
	

	var smsHandler = new SMSHandler();
	module.exports = smsHandler;
}; 



cordova.define(	'cordova/plugin/smsHandler',	
				function(require, exports, module) {
					CreateSMSHandler(require, exports, module);
				});


