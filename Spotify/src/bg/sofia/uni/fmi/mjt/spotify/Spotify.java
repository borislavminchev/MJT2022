package bg.sofia.uni.fmi.mjt.spotify;

import bg.sofia.uni.fmi.mjt.spotify.account.Account;
import bg.sofia.uni.fmi.mjt.spotify.exceptions.AccountNotFoundException;
import bg.sofia.uni.fmi.mjt.spotify.exceptions.PlayableNotFoundException;
import bg.sofia.uni.fmi.mjt.spotify.exceptions.StreamingServiceException;
import bg.sofia.uni.fmi.mjt.spotify.playable.Playable;

public class Spotify implements StreamingService{
    public Spotify(Account[] accounts, Playable[] playableContent){}

    @Override
    public void play(Account account, String title) throws AccountNotFoundException, PlayableNotFoundException {

    }

    @Override
    public void like(Account account, String title) throws AccountNotFoundException, PlayableNotFoundException, StreamingServiceException {

    }

    @Override
    public Playable findByTitle(String title) throws PlayableNotFoundException {
        return null;
    }

    @Override
    public Playable getMostPlayed() {
        return null;
    }

    @Override
    public double getTotalListenTime() {
        return 0;
    }

    @Override
    public double getTotalPlatformRevenue() {
        return 0;
    }
}
