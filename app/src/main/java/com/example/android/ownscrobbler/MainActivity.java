package com.example.android.ownscrobbler;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.CompoundButton;
import android.widget.ListView;
import android.widget.Switch;
import android.widget.TextView;

import com.firebase.client.Firebase;
import com.firebase.client.Query;
import com.firebase.ui.FirebaseListAdapter;

import java.util.Date;

public class MainActivity extends AppCompatActivity {

    private ListView trackView;
    private TextView songTextView;
    private Switch switcher;
    private BroadcastReceiver trackReceiver;
    private FirebaseListAdapter<Track> listAdapter;
    private Query last10Songs;

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

        updateNowPlaying();

        this.trackReceiver = new BroadcastReceiver() {
            @Override
            public void onReceive(Context context, Intent intent) {
                updateNowPlaying();
            }
        };
        this.registerReceiver(this.trackReceiver,
                new IntentFilter(PlayerIntentsReceiver.TRACK_CHANGED_ACTION));

        this.trackView = (ListView) findViewById(R.id.logList);
        Firebase.setAndroidContext(this);
        this.last10Songs = new Firebase(MyApplication.FIREBASE_URL).child("tracks").limitToLast(10);
        this.listAdapter = new FirebaseListAdapter<Track>(this, Track.class,
                R.layout.log_item, last10Songs) {
            @Override
            protected void populateView(View view, Track track, int i) {
                ((TextView) view.findViewById(R.id.track)).setText(track.track);
                ((TextView) view.findViewById(R.id.artist)).setText(track.artist);
                //((TextView) view.findViewById(R.id.album)).setText(track.album);
                ((TextView) view.findViewById(R.id.status)).setText("Played");
                ((TextView) view.findViewById(R.id.datetime)).setText(new Date(track.datetime).toString());
            }
        };
        trackView.setAdapter(listAdapter);

        this.switcher = (Switch) findViewById(R.id.logSwitcher);
        this.switcher.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                MyApplication.isLoggingEnabled = isChecked;
            }
        });
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        this.listAdapter.cleanup();
        unregisterReceiver(this.trackReceiver);
    }

    @Override
    protected void onPause() {
        super.onPause();

    }
}
