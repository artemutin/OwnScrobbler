package com.example.android.ownscrobbler;

import android.app.Application;
import android.util.Log;


public class MyApplication extends Application {
    private static boolean isMainActivityInForeground = false;
    private static Track track;

    public static boolean isMainActivityInForeground() {
        Log.d("intent", "Check for an activity");
        return isMainActivityInForeground;
    }

    public static void setIsMainActivityInForeground(boolean isMainActivityInForeground) {
        MyApplication.isMainActivityInForeground = isMainActivityInForeground;
    }

    public static Track getTrack() {
        return track;
    }

    public static void setTrack(Track track) {
        MyApplication.track = track;
    }
}
