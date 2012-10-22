/*
 * Copyright 2012 Google Inc.
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *   http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
package net.filiph.mothership.gcm;

import java.util.Date;

import net.filiph.mothership.MainActivity;
import net.filiph.mothership.NotificationHelper;
import android.content.Context;
import android.content.Intent;
import android.os.Vibrator;
import android.util.Log;

/**
 * Helper class providing methods and constants common to other classes in the
 * app.
 */
public final class CommonUtilities {

    /**
     * Base URL of the Demo Server (such as http://my_host:8080/gcm-demo)
     */
    static final String SERVER_URL = "http://mothership-backdoor.appspot.com";

    /**
     * Google API project id registered to use GCM.
     */
    public static final String SENDER_ID = "436765997997";

    /**
     * Tag used on log messages.
     */
    static final String TAG = "GCMDemo";

    /**
     * Intent used to display a message in the screen.
     */
    public static final String DISPLAY_MESSAGE_ACTION =
            "net.filiph.mothership.DISPLAY_MESSAGE";

    /**
     * Intent's extra that contains the message to be displayed.
     */
    public static final String EXTRA_MESSAGE = "message";

    /**
     * Notifies UI to display a message. 
     * <p>
     * Vibrates and/or notifies according to
     * the content of the messageString: "[nv] Hello" will both vibrate and notify.
     * "[n] Hello" will just notify. "[] Hello" will only update message, but will
     * not notify user in any way (unless they are looking at the app at the moment).
     * Unimplemented "[u]" will make the application get new message schedule from web.
     * <p>
     * This method is defined in the common helper because it's used both by
     * the UI and the background service.
     *
     * @param context application's context.
     * @param message message to be displayed. Should start with "[]" meta options.
     */
    public static void displayMessage(Context context, String messageString) {
    	// defaults
    	boolean vibrate = false;
    	boolean notify = false;
    	boolean update = false;
    	
    	// extract meta information
    	int startBracketIndex = messageString.indexOf("[");
    	int endBracketIndex = messageString.indexOf("]");
    	if (startBracketIndex == 0 && endBracketIndex > 0) {
    		for (int i = 1; i < endBracketIndex; i++) {
    			char ch = messageString.charAt(i);
    			switch (ch) {
    				case 'v': 
    					vibrate = true;
    					break;
    				case 'n':
    					notify = true;
    					break;
    				case 'u':
    					update = true;
    					break;
    			}
    		}
    	} else {
    		Log.w(TAG, "Received message without '[]' options in the front.");
    	}
    	
    	if (update) {
    		// TODO: update message schedule from web JSON
    	}
    	
    	if (endBracketIndex < messageString.length() - 1) {
    		String message = messageString.substring(endBracketIndex + 1);

			// put the newest message into a pref so we show it even after resuming the app
			context.getSharedPreferences(MainActivity.PREFS_NAME, 0).edit()
				.putString("gcmMessageString", message)
				.putLong("gcmMessageTime", new Date().getTime())
				.commit();
	    	
	//        Intent intent = new Intent(DISPLAY_MESSAGE_ACTION);
	//        intent.putExtra(EXTRA_MESSAGE, messageString);
	//        context.sendBroadcast(intent);
			
			Intent updateIntent = new Intent("net.filiph.mothership.NEW_MOTHERSHIP_MESSAGE");
			context.sendBroadcast(updateIntent);
    	}
			
		if (notify) {
			NotificationHelper.notify(context, vibrate); // notify on new message and vibrate
		} else if (vibrate) {
			Vibrator v = (Vibrator) context.getSystemService(Context.VIBRATOR_SERVICE);
			long[] pattern = {
					0, 	// start immediately
					300	// vibrate for 300ms 
			};
			v.vibrate(pattern, -1);
		}
    }
}
