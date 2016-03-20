package com.example.android.ownscrobbler;

public class Track {
    String track, artist, album;
    int status;
    long datetime;
    long duration;

    public Track() {

    }

    public Track(String track, String artist, String album, int status, long datetime, long duration) {
        this.track = track;
        this.artist = artist;
        this.album = album;
        this.status = status;
        this.datetime = datetime;
        this.duration = duration;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        Track track1 = (Track) o;

        if (duration != track1.duration) return false;
        if (!track.equals(track1.track)) return false;
        if (artist != null ? !artist.equals(track1.artist) : track1.artist != null) return false;
        return !(album != null ? !album.equals(track1.album) : track1.album != null);

    }

    @Override
    public int hashCode() {
        int result = track.hashCode();
        result = 31 * result + (artist != null ? artist.hashCode() : 0);
        result = 31 * result + (album != null ? album.hashCode() : 0);
        return result;
    }

    public String getTrack() {
        return track;
    }

    public void setTrack(String track) {
        this.track = track;
    }

    public String getArtist() {
        return artist;
    }

    public void setArtist(String artist) {
        this.artist = artist;
    }

    public String getAlbum() {
        return album;
    }

    public void setAlbum(String album) {
        this.album = album;
    }

    public int getStatus() {
        return status;
    }

    public void setStatus(int status) {
        this.status = status;
    }

    public long getDatetime() {
        return datetime;
    }

    public void setDatetime(long datetime) {
        this.datetime = datetime;
    }

    public long getDuration() {
        return duration;
    }

    public void setDuration(long duration) {
        this.duration = duration;
    }
}
