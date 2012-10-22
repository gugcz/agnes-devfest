package net.filiph.mothership;

import android.app.AlarmManager;
import android.app.Notification;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;

import android.support.v4.app.NotificationCompat;
import android.util.Log;
import android.widget.Toast;

/* from http://justcallmebrian.com/?p=129 */

public class AlarmReceiver extends BroadcastReceiver {

	@SuppressWarnings("unused")
	private static final String TAG = "alarmReceiver";

	public static final int REQUEST_CODE = 192837;
	public int notificationId = 0;

	@Override
	public void onReceive(Context context, Intent intent) {
		//Log.v(TAG, "onReceive called");
		Bundle bundle = intent.getExtras();
		
		try {
			Intent updateIntent = new Intent("net.filiph.mothership.NEW_MOTHERSHIP_MESSAGE");
			context.sendBroadcast(updateIntent);
		} catch (Exception e) {
			Log.e(TAG, "There was an error in sending intent to the main activity. The main activity doesn't know it's time for update.");
			e.printStackTrace();
		}
			
		try {
			// intent to show the main activity
			if (bundle.getBoolean("forceShowActivity")) {
				Intent showIntent = new Intent(context, MainActivity.class);
				showIntent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
				context.startActivity(showIntent);
			}
		} catch (Exception e) {
			Log.e(TAG, "There was an error while trying to force show the main activity. Failing that silently.");
			e.printStackTrace();
		}
		
		NotificationHelper.clearNotification(context);
		if (bundle.getBoolean("notify")) {
			NotificationHelper.notify(context, bundle.getBoolean("vibrate"));
		}

		setAlarmForNextMessage(context);
	}

	/**
	 * Gets the upcoming message and sets the AlarmManager to call this class
	 * when the message's time comes.
	 * 
	 * @param context
	 */
	public static void setAlarmForNextMessage(Context context) {
		// set alarm for next message, if any
		Message nextMessage = Schedule.getNextMessage();

		if (nextMessage != null) {
			Intent newIntent = new Intent(context, AlarmReceiver.class);
			newIntent.putExtra("forceShowActivity", nextMessage.forceShowActivity);
			newIntent.putExtra("vibrate", nextMessage.vibrate);
			newIntent.putExtra("notify", nextMessage.notify);
			PendingIntent sender = PendingIntent.getBroadcast(context, REQUEST_CODE, newIntent, PendingIntent.FLAG_UPDATE_CURRENT);

			try {
				// Get the AlarmManager service
				AlarmManager am = (AlarmManager) context.getSystemService(Context.ALARM_SERVICE);
				am.set(AlarmManager.RTC_WAKEUP, nextMessage.time.getTime(), sender);
				//Log.v(TAG, "Next message alarm set for " + nextMessage.time.toString() + " (now it's  "+ new Date().toString() +")");
			} catch (Exception e) {
				Log.e(TAG, "There was an error while trying to set the AlarmManager for next mothership message.");
				e.printStackTrace();
			}
		}
	}
}
