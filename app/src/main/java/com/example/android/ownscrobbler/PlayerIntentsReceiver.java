package com.example.android.ownscrobbler;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;


public class PlayerIntentsReceiver extends BroadcastReceiver {
    public static String TRACK_CHANGED_ACTION = "com.example.android.trackchanged";

    public PlayerIntentsReceiver() {
    }

    @Override
    public void onReceive(Context context, Intent intent) {
        Log.d("intent", "Was received:" + intent.toString());
        boolean isPlaying = intent.getBooleanExtra("playing", false);
        Track previousTrack = MyApplication.getTrack();

        if (isPlaying) {
            Bundle extras = intent.getExtras();
            String track = extras.getString("track");
            String album = extras.getString("album");
            String artist = extras.getString("artist");
            Long duration = extras.getLong("duration");
            Log.d("intent", track + " " + "album" + " " + "artist" + " " + "duration");
            Track nowPlaying = new Track(track, artist, album, Track.PLAYING, System.currentTimeMillis() / 1000L, duration / 1000L);

            if (nowPlaying != previousTrack) {
                if (previousTrack.status == Track.PLAYING && //if last time track was played
                        (nowPlaying.datetime - previousTrack.datetime) < 0.4 * previousTrack.duration) {//skipped, if played less than 40% of length
                    //track was skipped
                    previousTrack.setStatus(Track.SKIPPED);
                }//otherwise assuming track was played normally
                previousTrack.setDatetime(nowPlaying.datetime);
                //update previous track record
                MyApplication.getFirebase().child("tracks").limitToLast(1).getRef().setValue(previousTrack);
                MyApplication.setTrack(
                        nowPlaying
                );

                //sending new track to backend
                Log.d("intent", "Sending track to backend.");
                MyApplication.getFirebase().child("tracks").push().setValue(nowPlaying);
            }
        } else {
            if (previousTrack.getStatus() == Track.PLAYING) {
                previousTrack.setStatus(Track.PAUSED);
                MyApplication.getFirebase().child("tracks").limitToLast(1).getRef().setValue(previousTrack);
            }
        }


        Intent trackNotifier = new Intent(TRACK_CHANGED_ACTION);
        context.sendBroadcast(trackNotifier);
    }
}
