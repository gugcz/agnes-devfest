package com.example.android.skeletonapp;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.util.Log;

public class UpdateReceiver extends BroadcastReceiver {

    private SkeletonActivity activity;

    UpdateReceiver(SkeletonActivity activity) {
        this.activity = activity;
    }

    @Override
    public void onReceive(Context context, Intent intent) {
        //Log.i("dbg","onRecieve() called!");
        if (activity != null) {
        	// TODO
        	activity.showCurrentMessage(SkeletonActivity.TYPING_DEFAULT);
        }
    }
}
