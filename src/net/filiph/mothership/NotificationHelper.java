package net.filiph.mothership;

import android.app.Notification;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.Context;
import android.content.Intent;
import android.support.v4.app.NotificationCompat;
import android.util.Log;
import android.widget.Toast;

public class NotificationHelper {
	static final String TAG = "NotificationHelper";
	
	public static void notify(Context context, boolean vibrate) {
		try {
			Log.v(TAG, "Showing notification.");
			
			Intent showIntent = new Intent(context, MainActivity.class); 
			showIntent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
			PendingIntent pendingShowIntent = PendingIntent.getActivity(context, 0, 
					showIntent, PendingIntent.FLAG_UPDATE_CURRENT);

			Notification noti = new NotificationCompat.Builder(context)
			.setContentTitle(context.getText(R.string.notificationMessage))
			.setContentText(context.getText(R.string.notificationMessageText))
			.setTicker(context.getText(R.string.notificationMessage))
			.setContentIntent(pendingShowIntent)
			.setSmallIcon(R.drawable.devfest_small_icon)
			.setAutoCancel(true)
			.setDefaults(vibrate ? Notification.DEFAULT_VIBRATE | Notification.DEFAULT_SOUND 
								: Notification.DEFAULT_SOUND)
			.build();
			NotificationManager nm = (NotificationManager) context
					.getSystemService(Context.NOTIFICATION_SERVICE);
			nm.notify(0, noti);
		} catch (Exception e) {
			Log.e(TAG, "There was an error while creating notification. Trying a Toast.");
			e.printStackTrace();
			
			try {
				Toast.makeText(context, context.getText(R.string.notificationMessage), Toast.LENGTH_LONG).show();
			} catch (Exception e2) {
				Log.e(TAG, "There was an error while creating a Toast. Failed to update user on the new message.");
				e2.printStackTrace();
			}
		}
	}
	
	public static void clearNotification(Context context) {
		((NotificationManager) context.getSystemService(Context.NOTIFICATION_SERVICE)).cancel(0);
	}
}
