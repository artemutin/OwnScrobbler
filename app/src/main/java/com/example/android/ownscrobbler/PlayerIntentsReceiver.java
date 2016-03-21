package com.example.android.ownscrobbler;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;

import com.firebase.client.Firebase;


public class PlayerIntentsReceiver extends BroadcastReceiver {
    public static String TRACK_CHANGED_ACTION = "com.example.android.trackchanged";
    public PlayerIntentsReceiver() {
    }

    @Override
    public void onReceive(Context context, Intent intent) {
        Log.d("intent", "Was received:" + intent.toString());
        boolean isPlaying = intent.getBooleanExtra("playing", false);

        if (isPlaying) {
            Bundle extras = intent.getExtras();
            String track = extras.getString("track");
            String album = extras.getString("album");
            String artist = extras.getString("artist");
            Long duration = extras.getLong("duration");
            Track nowPlaying = new Track(track, artist, album, 0, System.currentTimeMillis() / 1000L, duration);
            if (nowPlaying != MyApplication.getTrack()) {
                MyApplication.setTrack(
                        nowPlaying
                );
                //send track to backend
                Log.d("intent", "Sending track to backend.");
                Firebase.setAndroidContext(context);
                Firebase firebase = new Firebase(MyApplication.FIREBASE_URL);
                firebase.child("tracks").push().setValue(nowPlaying);
            }
        }

        //if (MyApplication.isMainActivityInForeground()) {
        Intent trackNotifier = new Intent(TRACK_CHANGED_ACTION);
        //trackNotifier.addFlags(Intent.FLAG_INCLUDE_STOPPED_PACKAGES);
            context.sendBroadcast(trackNotifier);
        //}
    }
}
