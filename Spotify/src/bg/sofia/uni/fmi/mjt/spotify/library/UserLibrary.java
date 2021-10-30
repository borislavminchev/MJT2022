package bg.sofia.uni.fmi.mjt.spotify.library;

import bg.sofia.uni.fmi.mjt.spotify.exceptions.EmptyLibraryException;
import bg.sofia.uni.fmi.mjt.spotify.exceptions.LibraryCapacityExceededException;
import bg.sofia.uni.fmi.mjt.spotify.playlist.Playlist;
import bg.sofia.uni.fmi.mjt.spotify.exceptions.PlaylistNotFoundException;

public class UserLibrary implements Library {


    @Override
    public void add(Playlist playlist) throws LibraryCapacityExceededException {

    }

    @Override
    public void remove(String name) throws EmptyLibraryException, PlaylistNotFoundException {

    }

    @Override
    public Playlist getLiked() {
        return null;
    }
}
