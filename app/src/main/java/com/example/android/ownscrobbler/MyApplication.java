package com.example.android.ownscrobbler;

import android.app.Application;
import android.util.Log;

import com.firebase.client.DataSnapshot;
import com.firebase.client.Firebase;
import com.firebase.client.FirebaseError;
import com.firebase.client.ValueEventListener;


public class MyApplication extends Application {
    public static String FIREBASE_URL = "https://vivid-fire-5367.firebaseio.com/";
    private static boolean isMainActivityInForeground = false;
    private static Track track;
    private static Firebase firebase;

    public static Firebase getFirebase() {
        return firebase;
    }

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

    @Override
    public void onCreate() {
        super.onCreate();
        Firebase.setAndroidContext(this);
        MyApplication.firebase = new Firebase(MyApplication.FIREBASE_URL);
        //get last played track
        MyApplication.firebase.child("tracks").limitToLast(1).addListenerForSingleValueEvent(
                new ValueEventListener() {
                    @Override
                    public void onDataChange(DataSnapshot dataSnapshot) {
                        MyApplication.track = (Track) dataSnapshot.getValue();
                    }

                    @Override
                    public void onCancelled(FirebaseError firebaseError) {
                        Log.d("OwnScrobbler:", "Error in getting last track from FB");
                    }
                }
        );

    }
}
