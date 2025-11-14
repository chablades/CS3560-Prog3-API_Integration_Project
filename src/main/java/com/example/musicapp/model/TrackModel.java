package com.example.musicapp.model;

public class TrackModel {
    private String id;
    private String name;
    private String artist;
    private String album;

    public TrackModel(String id, String name, String artist, String album) {
        this.id = id;
        this.name = name;
        this.artist = artist;
        this.album = album;
    }

    public String getId() { return id; }
    public String getName() { return name; }
    public String getArtist() { return artist; }
    public String getAlbum() { return album; }

}