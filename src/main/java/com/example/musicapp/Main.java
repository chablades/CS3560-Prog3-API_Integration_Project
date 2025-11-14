package com.example.musicapp;

import com.example.musicapp.service.MusicService;
import java.util.Scanner;

public class Main {
    public static void main(String[] args) {
        MusicService musicService = new MusicService();

        // Ask user to input query
        Scanner scanner = new Scanner(System.in);
        System.out.print("Enter search query: ");
        String query = scanner.nextLine();

        // Ask user to input limit
        System.out.print("Enter limit: ");
        int limit = scanner.nextInt();

        // Ask user to input offset
        System.out.print("Enter offset: ");
        int offset = scanner.nextInt();

        var tracks = musicService.searchTracks(query, limit, offset);
        for (var track : tracks) {
            System.out.println(track.getName() + " by " + track.getArtist());
        }
        scanner.close();
    }
}
