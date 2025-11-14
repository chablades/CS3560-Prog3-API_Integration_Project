package com.example.musicapp.api;

import se.michaelthelin.spotify.SpotifyApi;
import se.michaelthelin.spotify.model_objects.specification.Paging;
import se.michaelthelin.spotify.model_objects.specification.Track;
import java.io.ObjectInputFilter.Config;
import java.net.http.*;
import com.example.musicapp.utils.ConfigLoader;

// Spotify API Client to handle authentication and requests
public class SpotifyAPIClient {
    
    private SpotifyApi spotifyApi;
    private String accessToken;
    private long tokenExpirationTime;

    public SpotifyAPIClient() {
        spotifyApi = new SpotifyApi.Builder()
            .setClientId(ConfigLoader.getProperty("spotify.client.id"))
            .setClientSecret(ConfigLoader.getProperty("spotify.client.secret"))
            .build();
    }

    public Paging<Track> searchTracks(String query, int limit, int offset) {
        refreshAccessToken();
        try {
            var searchRequest = spotifyApi.searchTracks(query)
                .limit(limit)
                .offset(offset)
                .build();
            return searchRequest.execute();
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }

    private void refreshAccessToken() {
        if (accessToken == null || System.currentTimeMillis() >= tokenExpirationTime) {
            createAccessToken();
        }
    }

    private void createAccessToken() {
        try {
            var clientCredentialsRequest = spotifyApi.clientCredentials().build();
            var clientCredentials = clientCredentialsRequest.execute();
            accessToken = clientCredentials.getAccessToken();
            spotifyApi.setAccessToken(accessToken);
            tokenExpirationTime = System.currentTimeMillis() + 3600 * 1000;
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
