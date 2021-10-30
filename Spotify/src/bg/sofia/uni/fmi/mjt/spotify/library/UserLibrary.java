package bg.sofia.uni.fmi.mjt.spotify.library;

import bg.sofia.uni.fmi.mjt.spotify.exceptions.EmptyLibraryException;
import bg.sofia.uni.fmi.mjt.spotify.exceptions.LibraryCapacityExceededException;
import bg.sofia.uni.fmi.mjt.spotify.playlist.Playlist;
import bg.sofia.uni.fmi.mjt.spotify.exceptions.PlaylistNotFoundException;
import bg.sofia.uni.fmi.mjt.spotify.playlist.UserPlaylist;

import java.util.Collections;

public class UserLibrary implements Library {
    private Playlist[] playlistsInLibrary;
    private int currentSize;

    private final int MAX_SIZE = 21;

    public UserLibrary() {
        currentSize = 1;
        playlistsInLibrary[0] = new UserPlaylist("Liked");
    }

    @Override
    public void add(Playlist playlist) throws LibraryCapacityExceededException {
        if(playlist == null) {
            throw new IllegalArgumentException("Cannot add NULL as value in Library.");
        }

        if (!playlist.getName().equals("Liked")) {
            if (currentSize >= playlistsInLibrary.length) {
                throw new LibraryCapacityExceededException("Capacity Exceeded. Cannot add more playlists. Max size is " + MAX_SIZE);
            }

            this.playlistsInLibrary[currentSize] = playlist;
            currentSize++;
        }
    }

    @Override
    public void remove(String name) throws EmptyLibraryException, PlaylistNotFoundException {
        if(name == null) {
            throw new IllegalArgumentException("Name cannot be null.");
        }

        if(!name.equals("Liked")) {
            if (this.playlistsInLibrary.length == 1) {
                throw new EmptyLibraryException("Library is empty. Cannot remove more playlists.");
            }

            boolean isFound = false;
            for (int i = 1; i < currentSize; i++) {
                if(this.playlistsInLibrary[i].getName().equals(name)) {

                    if(i != currentSize-1) {
                        Playlist temp = this.playlistsInLibrary[i];
                        this.playlistsInLibrary[i] = this.playlistsInLibrary[currentSize-1];
                        this.playlistsInLibrary[currentSize-1] = temp;
                    }
                    removeLastPlaylist();
                    isFound = true;
                    break;
                }
            }

            if(!isFound) {
                throw new PlaylistNotFoundException("Playlist with name: " + name + "was not found");
            }
        }
    }

    private void removeLastPlaylist() {
        this.playlistsInLibrary[currentSize-1] = null;
        currentSize--;
    }
    @Override
    public Playlist getLiked() {
        return this.playlistsInLibrary[0];
    }
}
