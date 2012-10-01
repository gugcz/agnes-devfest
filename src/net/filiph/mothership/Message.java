package net.filiph.mothership;

import java.util.Date;

import android.text.format.DateFormat;

public class Message {
	public final Date time;
	public final String text;
	public boolean notify = true;
	public boolean vibrate = false;
	public boolean forceShowActivity = false;
	public final int uid;
	
	Message(Date t, String s) {
		time = t;
		text = s;
		uid = getHashFromString(s + getTimeString());
	}
	
	Message(Date t, String s, boolean _notify, boolean _vibrate) {
		time = t;
		text = s;
		notify = _notify;
		vibrate = _vibrate;
		uid = getHashFromString(s + getTimeString());
	}
	
	public static int getHashFromString(String s) {
		int hash = 7;
		for (int i = 0; i < s.length(); i++) {
		    hash = hash * 31 + s.charAt(i);
		}
		return hash;
	}
	
	public String getTimeString() {
		return (String) DateFormat.format("MM/dd/yy h:mmaa", time);
	}
}
