package net.filiph.mothership;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;

public class UpdateReceiver extends BroadcastReceiver {

    private MainActivity activity;

    UpdateReceiver(MainActivity activity) {
        this.activity = activity;
    }

    @Override
    public void onReceive(Context context, Intent intent) {
        //Log.i("dbg","onRecieve() called!");
        if (activity != null) {
        	// TODO
        	activity.showCurrentMessage(MainActivity.TYPING_DEFAULT);
        }
    }
}
