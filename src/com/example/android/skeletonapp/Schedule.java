package com.example.android.skeletonapp;

import java.util.Calendar;
import java.util.Date;
import java.util.GregorianCalendar;
import java.util.TimeZone;

public class Schedule {

	/**
	 * Change the timezone here.
	 */
	final static String TIMEZONE_ID = "Europe/Prague";

	/**
	 * Change the schedule here.
	 */
	public static Message[] getSchedule() {
		Message[] messages = {

				new Message(date(2012, 8, 27, 8, 0), "Zatím se nic neděje ani nepřipravuje. Nepanikař!"),
				new Message(date(2012, 8, 28, 2, 23), "Připravuj se a čekej na další pokyny.", false, false),
				new Message(date(2012, 8, 28, 2, 24), "Začíná se blížit den akce. Připrav sebe i své bližní."),
				new Message(date(2012, 8, 28, 8, 0), "Time to rock in the future!")

		};
		return messages;
	}
	
	public static Date date(int year, int month, int day, int hour, int minute) {
		Calendar working = GregorianCalendar.getInstance(TimeZone.getTimeZone(TIMEZONE_ID));
		working.set(year, month, day, hour, minute, 1);
		return working.getTime();
	}

	public static Message getCurrentMessage() {
		Message[] msgs = Schedule.getSchedule();
		Date now = GregorianCalendar.getInstance(TimeZone.getTimeZone(TIMEZONE_ID)).getTime();
		Message currentMessage = null;

		for (int i = 0; i < msgs.length; i++) {
			if (msgs[i].time.before(now)) {
				currentMessage = msgs[i];
				continue;
			}
		}

		return currentMessage;
	}

	public static Message getNextMessage() {
		Message[] msgs = Schedule.getSchedule();
		Date now = GregorianCalendar.getInstance(TimeZone.getTimeZone(TIMEZONE_ID)).getTime();
		Message nextMessage = null;

		for (int i = 0; i < msgs.length; i++) {
			if (msgs[i].time.after(now)) {
				nextMessage = msgs[i];
				break;
			}
		}

		return nextMessage;
	}
}
