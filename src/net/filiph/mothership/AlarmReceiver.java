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
		try {
			Bundle bundle = intent.getExtras();

			Intent updateIntent = new Intent("NewMothershipMessage");
			context.sendBroadcast(updateIntent);

			try {
	
				// intent to show the main activity
				if (bundle.getBoolean("forceShowActivity")) {
					Intent showIntent = new Intent(context, MainActivity.class);
					showIntent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
					context.startActivity(showIntent);
				}
			} catch (Exception e) {
				Log.e(TAG, "There was an error while trying to force show the main activity. Failing.");
				e.printStackTrace();
			}
			
			try {
				if (bundle.getBoolean("notify")) {
					//Log.v(TAG, "showing notification");
	
					Intent showIntent = new Intent(context, MainActivity.class); 
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
				Log.e(TAG, "There was an error while creating notification. Trying a Toast.");
				e.printStackTrace();
				
				if (bundle.getBoolean("notify")) {
					try {
						Toast.makeText(context, context.getText(R.string.notificationMessage), Toast.LENGTH_LONG).show();
					} catch (Exception e2) {
						Log.e(TAG, "There was an error while creating a Toast. Failed to update user on the .");
						e2.printStackTrace();
					}
				}
			}
		
		} catch (Exception e) {
			Log.e(TAG, "There was an error in sending intent to the main activity. The main activity doesn't know it's time for update.");
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
