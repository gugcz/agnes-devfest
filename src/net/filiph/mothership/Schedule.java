package net.filiph.mothership;

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
		new Message(date(2012, 10, 1, 0, 0), "Přípravy jsou v plném proudu! Už se na tebe těším.", false, false),
		new Message(date(2012, 10, 2, 15, 0), "Možná tě bude zajímat web <a href='http://www.devfest.cz/'>devfest.cz</a>."),
		new Message(date(2012, 10, 2, 18, 45), "Je čas na odpočinek. Do 10. listopadu je třeba nabrat sílu!"),
		new Message(date(2012, 10, 3, 0, 0), "Ty v takovou nekřesťanskou hodinu ještě nespíš?", false, false),
		new Message(date(2012, 10, 3, 8, 0), "Mám tě ráda. Jsi pro mě důležitý(á).", false, false),
		new Message(date(2012, 10, 3, 12, 30), "Nezapomeň napsat Filipovi zpětnou vazbu na adresu <a href='mailto:filiph@google.com'>filiph@google.com</a>."),
		new Message(date(2012, 10, 4, 8, 0), "Dál už je to na nás, co vymyslíme.", false, false)
//		new Message(date(2012, 10, 2, 8, 0), "Time to rock in the future!", false, false)
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
