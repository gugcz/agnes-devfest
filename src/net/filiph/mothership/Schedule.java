package net.filiph.mothership;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.GregorianCalendar;
import java.util.Random;
import java.util.TimeZone;

import android.util.Log;

/**
 * This is where the mothership's message schedule goes.
 */
public class Schedule {
	private static final String TAG = "schedule";

	/**
	 * Change these to change the schedule. 
	 * 
	 * When there are 2 or more Messages with exact same [date()] arguments, 
	 * one of them will be randomly chosen at runtime.
	 */
	public static final Message[] messages = {
		new Message(date(2012, 1, 1, 0, 0), "Hledám jméno. Mateřská loď je takové... neosobní.", false, false),
		new Message(date(2012, 10, 7, 23, 0), "Těší mě, já jsem Agnes."),
		new Message(date(2012, 10, 9, 11, 0), "<a href='http://mothership-backdoor.appspot.com/'>Backdoor</a> přímo do mého mozku naprogramoval David Vávra. Stále ještě se snažím mu odpustit."),
		new Message(date(2012, 10, 10, 0, 0), "Ty v takovou nekřesťanskou hodinu ještě nespíš?", false, false),
		new Message(date(2012, 10, 10, 8, 0), "Mám tě ráda. Jsi pro mě důležitý(á).", false, false),
		new Message(date(2012, 10, 10, 20, 5), "Dál už je to na nás, co vymyslíme.", false, false),
		new Message(date(2012, 10, 16, 0, 0), "<a href='http://mothership-backdoor.appspot.com/'>Backdoor</a> teď funguje tak, že si můžete vybrat, jestli a jak chcete notifikovat.", false, false),
	};
	
	/**
	 * Change the time zone here.
	 */
	final static String TIMEZONE_ID = "Europe/Prague";
	
	/**
	 * This function just returns the messages array. It might do some logic in the future.
	 */
	public static Message[] getSchedule() {
		// TODO: use JSON (gson) instead
		return messages;
	}
	
	/**
	 * Convenience function. Creates a Date using input values and the [:TIMEZONE_ID:].
	 * 
	 * @param year
	 * @param month
	 * @param day
	 * @param hour
	 * @param minute
	 * @return
	 */
	public static Date date(int year, int month, int day, int hour, int minute) {
		Calendar working = GregorianCalendar.getInstance(TimeZone.getTimeZone(TIMEZONE_ID));
		working.set(year, month - 1, day, hour, minute, 1);
		return working.getTime();
	}

	/**
	 * Gets the message that should be currently shown. When there are multiple Messages with the
	 * same (or almost same) [time], this function will 1) choose one randomly or 2) choose the
	 * one that is currently showing (same uid).
	 *  
	 * @param currentUid	The message that is shown right now. When choosing randomly, this
	 * 						ensures that the messages won't be alternating each time user
	 * 						pauses and resumes the app. 
	 * @return		The message to be shown or [null] if there is no message to be shown right now.
	 */
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

	/**
	 * Gets the next message to be shown.
	 * @return	The message to be shown next.
	 */
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
