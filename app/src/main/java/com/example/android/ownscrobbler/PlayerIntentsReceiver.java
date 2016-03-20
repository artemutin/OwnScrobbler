package com.example.android.ownscrobbler;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;


public class PlayerIntentsReceiver extends BroadcastReceiver {
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
            MyApplication.setTrack(
                    new Track(track, artist, album, 0, System.currentTimeMillis() / 1000L, duration)
            );
        }

        if (MyApplication.isMainActivityInForeground()) {
            Intent trackNotifier = new Intent("com.example.android.trackchanged");
            context.sendBroadcast(trackNotifier);
        }
    }
}
