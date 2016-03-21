package com.example.android.ownscrobbler;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.widget.ListView;
import android.widget.TextView;

public class MainActivity extends AppCompatActivity {

    private ListView ListView;
    private TextView songTextView;
    private BroadcastReceiver trackReceiver;

    private void updateNowPlaying() {
        Track track = MyApplication.getTrack();
        if (track != null) {
            this.songTextView.setText(track.artist + " - " + track.track);
        } else {
            this.songTextView.setText("Nothing");
        }
    }
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        this.songTextView = (TextView) findViewById(R.id.songTextView);
        MyApplication.setIsMainActivityInForeground(true);
        updateNowPlaying();

        this.trackReceiver = new BroadcastReceiver() {
            @Override
            public void onReceive(Context context, Intent intent) {
                updateNowPlaying();
            }
        };
        this.registerReceiver(this.trackReceiver,
                new IntentFilter(PlayerIntentsReceiver.TRACK_CHANGED_ACTION));
    }

    @Override
    protected void onPause() {
        super.onPause();
        MyApplication.setIsMainActivityInForeground(false);
        //this.unregisterReceiver(this.trackReceiver);
    }
}
