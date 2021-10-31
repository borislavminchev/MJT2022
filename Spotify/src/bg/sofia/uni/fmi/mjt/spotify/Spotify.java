package bg.sofia.uni.fmi.mjt.spotify;

import bg.sofia.uni.fmi.mjt.spotify.account.Account;
import bg.sofia.uni.fmi.mjt.spotify.account.AccountType;
import bg.sofia.uni.fmi.mjt.spotify.exceptions.AccountNotFoundException;
import bg.sofia.uni.fmi.mjt.spotify.exceptions.PlayableNotFoundException;
import bg.sofia.uni.fmi.mjt.spotify.exceptions.PlaylistCapacityExceededException;
import bg.sofia.uni.fmi.mjt.spotify.exceptions.StreamingServiceException;
import bg.sofia.uni.fmi.mjt.spotify.playable.Playable;

public class Spotify implements StreamingService{

    private Account[] registeredAccounts;
    private Playable[] playableContent;

    public Spotify(Account[] accounts, Playable[] playableContent){
        this.registeredAccounts = accounts;
        this.playableContent = playableContent;
    }

    @Override
    public void play(Account account, String title) throws AccountNotFoundException, PlayableNotFoundException {
        if (account == null || title == null || title.isEmpty()) {
            throw new IllegalArgumentException("Invalid argument was passed to method play");
        }

        if(!isAccountInPlatform(account)) {
            throw new AccountNotFoundException("Account with username: " + account.getEmail() + "was not registered in the platform");
        }

        account.listen(findByTitle(title));
    }

    @Override
    public void like(Account account, String title) throws AccountNotFoundException, PlayableNotFoundException, StreamingServiceException {
        if (account == null || title == null || title.isEmpty()) {
            throw new IllegalArgumentException("Invalid argument was passed to method like");
        }
        if(!isAccountInPlatform(account)) {
            throw new AccountNotFoundException("Account with username: " + account.getEmail() + "was not registered in the platform");
        }

        try {
            account.getLibrary().getLiked().add(findByTitle(title));
        } catch (PlaylistCapacityExceededException e) {
            throw new StreamingServiceException("Couldn't add " + title + "to account's liked playlist." + e.getMessage());
        }
    }

    @Override
    public Playable findByTitle(String title) throws PlayableNotFoundException {
        for (int i = 0; i < this.playableContent.length; i++) {
            if(this.playableContent[i].getTitle().equals(title)) {
                return this.playableContent[i];
            }
        }

       throw new PlayableNotFoundException("Playable with title: " + title + " was not registered in the platform");
    }

    @Override
    public Playable getMostPlayed() {
        Playable mostPlayed = this.playableContent[0];
        for (int i = 1; i < this.playableContent.length; i++) {
           if(mostPlayed.getTotalPlays() < this.playableContent[i].getTotalPlays()) {
               mostPlayed = this.playableContent[i];
           }
        }
        return mostPlayed;
    }

    @Override
    public double getTotalListenTime() {
        double total = 0;
        for (int i = 0; i < this.playableContent.length; i++) {
            total += this.playableContent[i].getDuration();
        }
        return total;
    }

    @Override
    public double getTotalPlatformRevenue() {
        double totalRevenue = 0;
        for (int i = 0; i < this.registeredAccounts.length; i++) {
            if(this.registeredAccounts[i].getType() == AccountType.FREE) {
                totalRevenue += this.registeredAccounts[i].getAdsListenedTo() * 0.1;
            }else if (this.registeredAccounts[i].getType() == AccountType.PREMIUM) {
                totalRevenue += 25.0;
            }
        }
        return totalRevenue;
    }

    public boolean isAccountInPlatform(Account account) {
        for (int i = 0; i < this.registeredAccounts.length; i++) {
            if(this.registeredAccounts[i].equals(account)){
               return true;
            }
        }

        return false;
    }
}
