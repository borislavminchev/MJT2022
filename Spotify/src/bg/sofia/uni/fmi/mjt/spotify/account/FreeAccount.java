package bg.sofia.uni.fmi.mjt.spotify.account;

import bg.sofia.uni.fmi.mjt.spotify.account.Account;
import bg.sofia.uni.fmi.mjt.spotify.account.AccountType;

public class FreeAccount extends Account {
    @Override
    public int getAdsListenedTo() {
        return 0;
    }

    @Override
    public AccountType getType() {
        return null;
    }
}
