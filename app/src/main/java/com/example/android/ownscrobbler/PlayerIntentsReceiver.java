package com.example.android.ownscrobbler;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.util.Log;

public class PlayerIntentsReceiver extends BroadcastReceiver {
    public PlayerIntentsReceiver() {
    }

    @Override
    public void onReceive(Context context, Intent intent) {
        // TODO: This method is called when the BroadcastReceiver is receiving
        // an Intent broadcast.
        Log.d("intent", "Was received:" + intent.toString());
    }
}
