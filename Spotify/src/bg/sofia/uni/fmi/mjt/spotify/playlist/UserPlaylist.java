package bg.sofia.uni.fmi.mjt.spotify.playlist;

import bg.sofia.uni.fmi.mjt.spotify.exceptions.PlaylistCapacityExceededException;
import bg.sofia.uni.fmi.mjt.spotify.playable.Playable;

public class UserPlaylist implements Playlist {

    public UserPlaylist(String name) {}

    @Override
    public void add(Playable playable) throws PlaylistCapacityExceededException {

    }

    @Override
    public String getName() {
        return null;
    }
}
