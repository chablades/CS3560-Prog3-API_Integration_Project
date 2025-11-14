package com.example.musicapp.service;

import com.example.musicapp.api.SpotifyAPIClient;
import com.example.musicapp.model.TrackModel;
import se.michaelthelin.spotify.model_objects.specification.Track;
import se.michaelthelin.spotify.model_objects.specification.Paging;
import java.util.ArrayList;
import java.util.List;

public class MusicService {
    private SpotifyAPIClient spotifyClient;

    public MusicService() {
        spotifyClient = new SpotifyAPIClient();
    }

    public List<TrackModel> searchTracks(String query, int limit, int offset) {
        Paging<Track> paging = spotifyClient.searchTracks(query, limit, offset);
        List<TrackModel> trackModels = new ArrayList<>();
        if (paging != null) {
            for (Track track : paging.getItems()) {
                TrackModel model = new TrackModel(
                    track.getId(),
                    track.getName(),
                    track.getArtists()[0].getName(),
                    track.getAlbum().getName()
                );
                trackModels.add(model);
            }
        }
        return trackModels;
    }

    public SpotifyAPIClient getSpotifyClient() {
        return spotifyClient;
    }
}
