package com.example.android.skeletonapp;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.GregorianCalendar;
import java.util.Random;
import java.util.TimeZone;

import android.util.Log;

public class Schedule {
	private static final String TAG = "schedule";

	/**
	 * Change these to change the schedule.
	 */
	public static final Message[] messages = {
		new Message(date(2012, 9, 30, 8, 0), "Zatím se nic neděje ani nepřipravuje. Nepanikař!"),
		new Message(date(2012, 10, 1, 1, 11), "Připravuj se a čekej na další pokyny. ", false, false),
		new Message(date(2012, 10, 1, 1, 12), "Začíná se blížit den akce. Připrav sebe i své bližní. <a href=\"http://devfest.cz/\">df</a>"),
		new Message(date(2012, 10, 1, 1, 12), "Toto je můj <a href='mailto:filiph@google.com'>email</a>. Honem mi teda napište!"),
		new Message(date(2012, 10, 2, 8, 0), "Time to rock in the future!", false, false)
	};
	
	/**
	 * Change the time zone here.
	 */
	final static String TIMEZONE_ID = "Europe/Prague";

	Schedule() {
		
	}
	
	/**
	 * Change the schedule here.
	 */
	public static Message[] getSchedule() {

		return messages;
	}
	
	public static Date date(int year, int month, int day, int hour, int minute) {
		Calendar working = GregorianCalendar.getInstance(TimeZone.getTimeZone(TIMEZONE_ID));
		working.set(year, month - 1, day, hour, minute, 1);
		return working.getTime();
	}

	public static Message getCurrentMessage(int currentUid) {
		Date now = GregorianCalendar.getInstance(TimeZone.getTimeZone(TIMEZONE_ID)).getTime();
		ArrayList<Message> currentMessages = new ArrayList<Message>();

		long lastTimestamp = 0;
		for (int i = 0; i < messages.length; i++) {
			if (messages[i].time.before(now)) {
				if (Math.abs(lastTimestamp - messages[i].time.getTime()) > 1000) {
					Log.v(TAG, "Clearing messages.");
					currentMessages.clear();
					lastTimestamp = messages[i].time.getTime();
				}
				Log.v(TAG, "Adding message: " + messages[i].text + "\ntimestamp: " + messages[i].time.getTime());
				currentMessages.add(messages[i]);
			} else if (messages[i].time.after(now)) {
				break;
			}
		}
		
		if (currentMessages.isEmpty()) {
			return null;
		}
		
		Log.v(TAG, "Looking for a message with same UID ("+currentUid+")");
		for (int i = 0; i < currentMessages.size(); i++) {
			Log.v(TAG, "Considering message: " + currentMessages.get(i).text);
			if (currentMessages.get(i).uid == currentUid) {
				Log.v(TAG, "- message has same currentUid");
				return currentMessages.get(i);
			}
		}
		
		return currentMessages.get(new Random().nextInt(currentMessages.size())); // get random
	}

	public static Message getNextMessage() {
		Date now = GregorianCalendar.getInstance(TimeZone.getTimeZone(TIMEZONE_ID)).getTime();
		Message nextMessage = null;

		for (int i = 0; i < messages.length; i++) {
			if (messages[i].time.after(now)) {
				nextMessage = messages[i];
				break;
			}
		}

		return nextMessage;
	}
}
