package com.example.android.ownscrobbler;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;

import com.firebase.client.ChildEventListener;
import com.firebase.client.DataSnapshot;
import com.firebase.client.Firebase;
import com.firebase.client.FirebaseError;


public class PlayerIntentsReceiver extends BroadcastReceiver {
    public static String TRACK_CHANGED_ACTION = "com.example.android.trackchanged";
    protected boolean doOnce = false;
    Track track;
    private Firebase tracks_reference = MyApplication.getFirebase().child("tracks");
    private UpdateListener updateListener;
    public PlayerIntentsReceiver() {
    }

    private void sendNewTrack(Track track) {
        //sending new track to backend
        if (MyApplication.isLoggingEnabled) {
            Log.d("intent", "Sending track to backend.");
            tracks_reference.push().setValue(track);
        }
        MyApplication.setTrack(track);
    }

    private void updatePreviousTrack(final Track track) {
        //update previous track record
        if (MyApplication.isLoggingEnabled) {
            this.track = track;
            tracks_reference.orderByChild("datetime").limitToLast(1).addChildEventListener(
                    this.updateListener
            );
        }


    }

    @Override
    public void onReceive(Context context, Intent intent) {
        Log.d("intent", "Was received:" + intent.toString());

        boolean isPlaying = intent.getBooleanExtra("playing", false);
        Track previousTrack = MyApplication.getTrack();
        if (this.updateListener != null) {
            this.tracks_reference.removeEventListener(updateListener);
        } else {
            this.updateListener = new UpdateListener();
        }


        if (isPlaying) {
            Bundle extras = intent.getExtras();
            String track = extras.getString("track");
            String album = extras.getString("album");
            String artist = extras.getString("artist");
            Long duration = extras.getLong("duration");
            Log.d("intent", track + " " + album + " " + artist + " " + duration);
            Track nowPlaying = new Track(track, artist, album, Track.PLAYING, System.currentTimeMillis() / 1000L, duration / 1000L);
            if (previousTrack == null) {
                sendNewTrack(nowPlaying);
                return;
            }
            if (!nowPlaying.equals(previousTrack)) {
                if (previousTrack.status == Track.PLAYING && //if last time track was played
                        (nowPlaying.datetime - previousTrack.datetime) < 0.4 * previousTrack.duration) {//skipped, if played less than 40% of length
                    //track was skipped
                    previousTrack.setStatus(Track.SKIPPED);
                }//otherwise assuming track was played normally
                previousTrack.setDatetime(nowPlaying.datetime);

                updatePreviousTrack(previousTrack);

                sendNewTrack(nowPlaying);
            } else {
                //track was paused but now resumed
                updatePreviousTrack(nowPlaying);
            }
        } else {
            if (previousTrack != null && previousTrack.getStatus() == Track.PLAYING) {
                previousTrack.setStatus(Track.PAUSED);
                updatePreviousTrack(previousTrack);
            }
        }


        Intent trackNotifier = new Intent(TRACK_CHANGED_ACTION);
        context.sendBroadcast(trackNotifier);
    }

    private class UpdateListener implements ChildEventListener {

        @Override
        public void onChildAdded(DataSnapshot dataSnapshot, String s) {
            if (!doOnce) {
                Log.d("backend", dataSnapshot.getKey() + ":" + dataSnapshot.getValue());
                dataSnapshot.getRef().setValue(track);
                doOnce = true;
            }
        }

        @Override
        public void onChildChanged(DataSnapshot dataSnapshot, String s) {

        }

        @Override
        public void onChildRemoved(DataSnapshot dataSnapshot) {

        }

        @Override
        public void onChildMoved(DataSnapshot dataSnapshot, String s) {

        }

        @Override
        public void onCancelled(FirebaseError firebaseError) {

        }
    }
}
