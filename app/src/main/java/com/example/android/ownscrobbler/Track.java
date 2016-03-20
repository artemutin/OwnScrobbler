package com.example.android.ownscrobbler;

public class Track {
    String track, artist, album;
    int status;
    long datetime;
    long duration;

    public Track(String track, String artist, String album, int status, long datetime, long duration) {
        this.track = track;
        this.artist = artist;
        this.album = album;
        this.status = status;
        this.datetime = datetime;
        this.duration = duration;
    }
}
