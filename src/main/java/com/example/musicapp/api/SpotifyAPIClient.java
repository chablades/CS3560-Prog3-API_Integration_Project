package com.example.musicapp.api;

import se.michaelthelin.spotify.SpotifyApi;
import java.io.ObjectInputFilter.Config;
import java.net.http.*;
import com.example.musicapp.utils.ConfigLoader;

// Spotify API Client to handle authentication and requests
public class SpotifyAPIClient {
    
    private String clientId;
    private String clientSecret;

    public SpotifyAPIClient() {
        this.clientId = ConfigLoader.getProperty("spotify.clientId");
        this.clientSecret = ConfigLoader.getProperty("spotify.clientSecret");
    }

}
