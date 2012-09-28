package com.example.android.skeletonapp;

import java.util.Date;

public class Message {
	public final Date time;
	public final String text;
	public boolean notify = true;
	public boolean vibrate = false;
	public boolean forceShowActivity = false;
	
	Message(Date t, String s) {
		time = t;
		text = s;
	}
	
	Message(Date t, String s, boolean _notify, boolean _vibrate) {
		time = t;
		text = s;
		notify = _notify;
		vibrate = _vibrate;
	}
}
