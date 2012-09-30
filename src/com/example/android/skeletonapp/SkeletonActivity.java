/*
 * Copyright (C) 2007 The Android Open Source Project
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *      http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

package com.example.android.skeletonapp;

import android.annotation.TargetApi;
import android.app.Activity;
import android.content.IntentFilter;
import android.graphics.PixelFormat;
import android.media.MediaPlayer;
import android.media.MediaPlayer.OnErrorListener;
import android.media.MediaPlayer.OnPreparedListener;
import android.net.Uri;
import android.os.Bundle;
import android.text.Html;
import android.text.method.LinkMovementMethod;
import android.util.Log;
import android.view.Display;
import android.view.View.MeasureSpec;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.ScrollView;
import android.widget.TextView;
import android.widget.TextView.BufferType;
import android.widget.VideoView;

/**
 * This class provides a basic demonstration of how to write an Android
 * activity. Inside of its window, it places a single view: an EditText that
 * displays and edits some internal text.
 */
@TargetApi(8)
public class SkeletonActivity extends Activity {
    
	@SuppressWarnings("unused")
	private static final String TAG = "motherShip";
	
	private UpdateReceiver updateReceiver;
	
    public SkeletonActivity() {
    }

    /** Called with the activity is first created. */
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        // Inflate our UI from its XML layout description.
        setContentView(R.layout.skeleton_activity);

        // TODO: only do if there is no alarm set
        AlarmReceiver.setAlarmForNextMessage(getBaseContext());
    }
    
    VideoView vv = null;

    /**
     * Called when the activity is about to start interacting with the user.
     */
    @Override
    protected void onResume() {
        super.onResume();
        
        showCurrentMessage();
        
        updateReceiver = new UpdateReceiver(this);
        registerReceiver(updateReceiver, new IntentFilter("NewMothershipMessage"));
        
        if (vv == null) {
	        //getWindow().setFormat(PixelFormat.TRANSLUCENT);
	        vv = (VideoView) findViewById(R.id.videoView1);
	        Uri video = Uri.parse("android.resource://" + getPackageName() + "/" 
	        		+ R.raw.blueabstract_high_h264);
	        vv.setVideoURI(video);
	        vv.setOnPreparedListener(new OnPreparedListener() {
	            @Override
	            public void onPrepared(MediaPlayer mp) {
	            	mp.setLooping(true);
	            	ScrollView sv = (ScrollView) findViewById(R.id.scrollView);
	            	sv.setBackgroundResource(0);
	            }
	        });
	        
	        vv.setOnErrorListener(new OnErrorListener() {
	        	@Override
	        	public boolean onError(MediaPlayer mp, int what, int extra) {
	        		Log.e(TAG, "Error with video.");
					return false;
	        		
	        	}
	        });
	        
	        Display display = getWindowManager().getDefaultDisplay(); 
	        int childWidthMeasureSpec = MeasureSpec.makeMeasureSpec(display.getWidth(),
	                MeasureSpec.UNSPECIFIED);
	        int childHeightMeasureSpec = MeasureSpec.makeMeasureSpec(display.getHeight(),
	                MeasureSpec.UNSPECIFIED);
	        vv.measure(childWidthMeasureSpec, childHeightMeasureSpec);
        }
       
        

        
        vv.start();
    }
    
    @Override
    protected void onPause() {
    	super.onPause();
    	
    	if (vv != null) {
    		vv.pause();
    		//vv.suspend();
    	}
    	
    	unregisterReceiver(updateReceiver); // don't update the activity when paused
    }
    
    /**
     * This is called for activities that set launchMode to "singleTop" in their package.
     * This is called by AlarmReceiver.
     */
//    @Override
//    protected void onNewIntent(Intent intent) {
//        super.onNewIntent(intent);
//        
//        showCurrentMessage();
//    }
    
    public static final String PREFS_NAME = "MothershipPrefs";
    
    public void showCurrentMessage() {
    	int currentUid = getSharedPreferences(PREFS_NAME, 0).getInt("currentUid", 0);
    	Message currentMessage = Schedule.getCurrentMessage(currentUid);
    	
    	if (currentMessage != null) {
    		boolean newMessage = currentMessage.uid != currentUid;
    		Log.v(TAG, "Saving new currentUid = " + currentMessage.uid);
    		getSharedPreferences(PREFS_NAME, 0).edit().putInt("currentUid", currentMessage.uid).commit();
    		
    		TextView t = (TextView) findViewById(R.id.textView);
    		t.setText(Html.fromHtml(currentMessage.text));
    		t.setMovementMethod(LinkMovementMethod.getInstance());
    		
    		Animation fadeinAni = AnimationUtils.loadAnimation(this, R.anim.fade_in);
    		t.startAnimation(fadeinAni);
    		
    		TextView signature = (TextView) findViewById(R.id.signature);
    		signature.setText(currentMessage.time.toString()); // TODO
    		
    		Animation fadeinAniDelayed = AnimationUtils.loadAnimation(this, R.anim.fade_in_delayed);
    		signature.startAnimation(fadeinAniDelayed);
    	}
    }


}
