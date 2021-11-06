package bg.sofia.uni.fmi.mjt.investment.wallet;

import bg.sofia.uni.fmi.mjt.investment.wallet.acquisition.Acquisition;
import bg.sofia.uni.fmi.mjt.investment.wallet.asset.Asset;
import bg.sofia.uni.fmi.mjt.investment.wallet.exception.InsufficientResourcesException;
import bg.sofia.uni.fmi.mjt.investment.wallet.exception.UnknownAssetException;
import bg.sofia.uni.fmi.mjt.investment.wallet.exception.WalletException;
import bg.sofia.uni.fmi.mjt.investment.wallet.quote.QuoteService;

import java.util.Collection;
import java.util.Set;

public class InvestmentWallet implements Wallet {

    public InvestmentWallet(QuoteService quoteService) {
    }

    @Override
    public double deposit(double cash) {
        return 0;
    }

    @Override
    public double withdraw(double cash) throws InsufficientResourcesException {
        return 0;
    }

    @Override
    public Acquisition buy(Asset asset, int quantity, double maxPrice) throws WalletException {
        return null;
    }

    @Override
    public double sell(Asset asset, int quantity, double minPrice) throws WalletException {
        return 0;
    }

    @Override
    public double getValuation() {
        return 0;
    }

    @Override
    public double getValuation(Asset asset) throws UnknownAssetException {
        return 0;
    }

    @Override
    public Asset getMostValuableAsset() {
        return null;
    }

    @Override
    public Collection<Acquisition> getAllAcquisitions() {
        return null;
    }

    @Override
    public Set<Acquisition> getLastNAcquisitions(int n) {
        return null;
    }
}
