package com.example.android.skeletonapp;

import android.app.AlarmManager;
import android.app.Notification;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;

import android.support.v4.app.NotificationCompat;

/* from http://justcallmebrian.com/?p=129 */

public class AlarmReceiver extends BroadcastReceiver {

	@SuppressWarnings("unused")
	private static final String TAG = "alarmReceiver";

	public static final int REQUEST_CODE = 192837;
	public int notificationId = 0;

	@Override
	public void onReceive(Context context, Intent intent) {
		//Log.v(TAG, "onReceive called");
		try {
			Bundle bundle = intent.getExtras();

			Intent updateIntent = new Intent("NewMothershipMessage");
			context.sendBroadcast(updateIntent);

			// intent to show the main activity
			if (bundle.getBoolean("forceShowActivity")) {
				Intent showIntent = new Intent(context, SkeletonActivity.class);
				showIntent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
				context.startActivity(showIntent);
			}

			if (bundle.getBoolean("notify")) {
				//Log.v(TAG, "showing notification");

				Intent showIntent = new Intent(context, SkeletonActivity.class); 
				showIntent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
				PendingIntent pendingShowIntent = PendingIntent.getActivity(context, 0, showIntent, PendingIntent.FLAG_UPDATE_CURRENT);

				Notification noti = new NotificationCompat.Builder(context)
				.setContentTitle(context.getText(R.string.notificationMessage))
				.setContentText(context.getText(R.string.notificationMessageText))
				.setTicker(context.getText(R.string.notificationMessage))
				.setContentIntent(pendingShowIntent)
				.setSmallIcon(R.drawable.devfest_small_icon)
				.setAutoCancel(true)
				.setDefaults(bundle.getBoolean("vibrate") ? Notification.DEFAULT_VIBRATE | Notification.DEFAULT_SOUND 
									: Notification.DEFAULT_SOUND)
				.build();
				NotificationManager nm = (NotificationManager) context.getSystemService(Context.NOTIFICATION_SERVICE);
				nm.notify(0, noti);
			}

		} catch (Exception e) {
			// TODO
			//Toast.makeText(context, "There was an error somewhere, but we still received an alarm", Toast.LENGTH_SHORT).show();
			e.printStackTrace();
		}

		setAlarmForNextMessage(context);
	}

	public static void setAlarmForNextMessage(Context context) {
		// set alarm for next message, if any
		Message nextMessage = Schedule.getNextMessage();

		if (nextMessage != null) {
			Intent newIntent = new Intent(context, AlarmReceiver.class);
			newIntent.putExtra("forceShowActivity", nextMessage.forceShowActivity);
			newIntent.putExtra("vibrate", nextMessage.vibrate);
			newIntent.putExtra("notify", nextMessage.notify);
			PendingIntent sender = PendingIntent.getBroadcast(context, REQUEST_CODE, newIntent, PendingIntent.FLAG_UPDATE_CURRENT);

			// Get the AlarmManager service
			AlarmManager am = (AlarmManager) context.getSystemService(Context.ALARM_SERVICE);
			am.set(AlarmManager.RTC_WAKEUP, nextMessage.time.getTime(), sender);
			//Log.v(TAG, "Next message alarm set for " + nextMessage.time.toString() + " (now it's  "+ new Date().toString() +")");
		}
	}
}
