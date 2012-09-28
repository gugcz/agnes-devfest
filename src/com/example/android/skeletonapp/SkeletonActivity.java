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
import android.media.MediaPlayer.OnPreparedListener;
import android.net.Uri;
import android.os.Bundle;
import android.view.Display;
import android.view.View.MeasureSpec;
import android.widget.TextView;
import android.widget.VideoView;

/**
 * This class provides a basic demonstration of how to write an Android
 * activity. Inside of its window, it places a single view: an EditText that
 * displays and edits some internal text.
 */
@TargetApi(3)
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

    /**
     * Called when the activity is about to start interacting with the user.
     */
    @Override
    protected void onResume() {
        super.onResume();
        
        showCurrentMessage();
        
        //getWindow().setFormat(PixelFormat.TRANSLUCENT);
        VideoView vv = (VideoView) findViewById(R.id.videoView1);
        Uri video = Uri.parse("android.resource://" + getPackageName() + "/" 
        		+ R.raw.blueabstract_high);
        vv.setVideoURI(video);
        vv.setOnPreparedListener(new OnPreparedListener() {
            @Override
            public void onPrepared(MediaPlayer mp) {
                mp.setLooping(true);
            }
        });
        vv.start();
        
        Display display = getWindowManager().getDefaultDisplay(); 
        int childWidthMeasureSpec = MeasureSpec.makeMeasureSpec(display.getWidth(),
                MeasureSpec.UNSPECIFIED);
        int childHeightMeasureSpec = MeasureSpec.makeMeasureSpec(display.getHeight(),
                MeasureSpec.UNSPECIFIED);
        vv.measure(childWidthMeasureSpec, childHeightMeasureSpec);
        
        updateReceiver = new UpdateReceiver(this);
        registerReceiver(updateReceiver, new IntentFilter("NewMothershipMessage"));
    }
    
    @Override
    protected void onPause() {
    	super.onPause();
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
    
    public void showCurrentMessage() {
    	// TODO: use persistence to offer random messages
    	Message currentMessage = Schedule.getCurrentMessage();
    	if (currentMessage != null) {
    		TextView t = (TextView) findViewById(R.id.textView);
    		t.setText(currentMessage.text);
    	}
    }


}
