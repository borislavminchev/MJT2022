package bg.sofia.uni.fmi.mjt.spotify.playlist;

import bg.sofia.uni.fmi.mjt.spotify.exceptions.PlaylistCapacityExceededException;
import bg.sofia.uni.fmi.mjt.spotify.playable.Playable;

public class UserPlaylist implements Playlist {

    private final int MAX_SIZE = 20;
    private final String name;
    private final Playable[] contentInPlaylist;
    private int currentSize;


    public UserPlaylist(String name) {
        this.name = name;
        contentInPlaylist = new Playable[MAX_SIZE];
        this.currentSize = 0;
    }

    @Override
    public void add(Playable playable) throws PlaylistCapacityExceededException {
        if (playable == null) {
            throw new IllegalArgumentException("Cannot add null as content in playlist");
        }

        if (currentSize >= this.contentInPlaylist.length) {
            throw new PlaylistCapacityExceededException("Capacity Exceeded. Max capacity is " + MAX_SIZE);
        }

        this.contentInPlaylist[currentSize] = playable;
        this.currentSize++;
    }

    @Override
    public String getName() {
        return this.name;
    }
}
