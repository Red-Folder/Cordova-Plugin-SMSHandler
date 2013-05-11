# IMPORTANT NOTICE #
This plugin is work in progress.

Your welcome to try it, but please feedback (via issues) any problems you experience.



# SMS Handler Plugin for Phonegap #

This plugin is used to allow Phonegap to handle incoming SMS messages.

The plugin allows you to define rules.  It will then monitor incoming SMS messages, if it matches the defined rules it will save to database.  The plugin provides a mesages to retieve those mssages from the database.


## Installation instructions ##
1) Copy the contents from the Github respository into your project

2) Add the following to AndroidManifest.xml - within the <manifest> node:
	<uses-permission android:name="android.permission.RECEIVE_SMS"></uses-permission>

3) Add the following to AndroidManifest.xml - within the <application> node:
	<receiver android:name="com.red_folder.phonegap.plugin.smshandler.SMSReceiver"> 
		<intent-filter android:priority="999"> 
			<action android:name="android.provider.Telephony.SMS_RECEIVED"  /> 
		</intent-filter> 
	</receiver> 

4) Add the following to res/xml/config.xml - within the <plugins> node:
	<plugin name="SMSHandlerPlugin" value="com.red_folder.phonegap.plugin.smshandler.SMSHandlerPlugin" />


## Further information ##

* Rules should allow regex expressions (untested)
* If you add to your own project, amend SMSReceiver.java and update CLASS_TO_RUN to point at your own activity


## Change Log ##

* 11th May 2013 - Initial version



## Licence ##

The MIT License

Copyright (c) 2013 Red Folder Consultancy Ltd

Permission is hereby granted, free of charge, to any person obtaining a copy of this software and associated documentation files (the "Software"), to deal in the Software without restriction, including without limitation the rights to use, copy, modify, merge, publish, distribute, sublicense, and/or sell copies of the Software, and to permit persons to whom the Software is furnished to do so, subject to the following conditions:

The above copyright notice and this permission notice shall be included in all copies or substantial portions of the Software.

THE SOFTWARE IS PROVIDED "AS IS", WITHOUT WARRANTY OF ANY KIND, EXPRESS OR IMPLIED, INCLUDING BUT NOT LIMITED TO THE WARRANTIES OF MERCHANTABILITY, FITNESS FOR A PARTICULAR PURPOSE AND NONINFRINGEMENT. IN NO EVENT SHALL THE AUTHORS OR COPYRIGHT HOLDERS BE LIABLE FOR ANY CLAIM, DAMAGES OR OTHER LIABILITY, WHETHER IN AN ACTION OF CONTRACT, TORT OR OTHERWISE, ARISING FROM, OUT OF OR IN CONNECTION WITH THE SOFTWARE OR THE USE OR OTHER DEALINGS IN THE SOFTWARE.

