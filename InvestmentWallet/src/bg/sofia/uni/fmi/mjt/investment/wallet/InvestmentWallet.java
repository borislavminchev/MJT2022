package bg.sofia.uni.fmi.mjt.investment.wallet;

import bg.sofia.uni.fmi.mjt.investment.wallet.acquisition.Acquisition;
import bg.sofia.uni.fmi.mjt.investment.wallet.asset.Asset;
import bg.sofia.uni.fmi.mjt.investment.wallet.asset.AssetType;
import bg.sofia.uni.fmi.mjt.investment.wallet.exception.InsufficientResourcesException;
import bg.sofia.uni.fmi.mjt.investment.wallet.exception.UnknownAssetException;
import bg.sofia.uni.fmi.mjt.investment.wallet.exception.WalletException;
import bg.sofia.uni.fmi.mjt.investment.wallet.quote.QuoteService;

import java.util.Collection;
import java.util.HashMap;
import java.util.Map;
import java.util.Set;

public class InvestmentWallet implements Wallet {
    private final QuoteService quoteService;
    private double cashInWallet;
    private final Map<AssetType, Integer> ownedAssets;

    public InvestmentWallet(QuoteService quoteService) {
        this.quoteService = quoteService;
        this.ownedAssets = new HashMap<>();
        this.cashInWallet = 0;
    }

    @Override
    public double deposit(double cash) {
        if (cash < 0) {
            throw new IllegalArgumentException("Cannot deposit negative amount of cash");
        }
        this.cashInWallet += cash;
        return this.cashInWallet;
    }

    @Override
    public double withdraw(double cash) throws InsufficientResourcesException {
        if (cash < 0) {
            throw new IllegalArgumentException("Cannot withdraw negative amount of cash");
        }
        if (this.cashInWallet < cash) {
            throw new InsufficientResourcesException("The cash balance is " +
                    "insufficient to proceed with the withdrawal");
        }
        this.cashInWallet -= cash;
        return this.cashInWallet;
    }

    @Override
    public Acquisition buy(Asset asset, int quantity, double maxPrice) throws WalletException {
        AssetType type = asset.getType();
        if (!this.ownedAssets.containsKey(type)) {
            this.ownedAssets.put(type, quantity);
        } else {
            this.ownedAssets.put(type, this.ownedAssets.get(type) + quantity);
        }
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
