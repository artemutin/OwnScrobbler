package com.example.android.ownscrobbler;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.widget.ListView;
import android.widget.TextView;

public class MainActivity extends AppCompatActivity {

    ListView ListView;
    TextView songTextView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        this.songTextView = (TextView) findViewById(R.id.songTextView);
        MyApplication.setIsMainActivityInForeground(true);
        Track track = MyApplication.getTrack();
        if (track != null) {
            songTextView.setText(track.artist + " - " + track.track);
        }
    }

    @Override
    protected void onPause() {
        super.onPause();
        MyApplication.setIsMainActivityInForeground(false);
    }
}
