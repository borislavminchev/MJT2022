package bg.sofia.uni.fmi.mjt.investment.wallet;

import bg.sofia.uni.fmi.mjt.investment.wallet.acquisition.Acquisition;
import bg.sofia.uni.fmi.mjt.investment.wallet.acquisition.DefaultAcquisition;
import bg.sofia.uni.fmi.mjt.investment.wallet.asset.Asset;
import bg.sofia.uni.fmi.mjt.investment.wallet.asset.AssetType;
import bg.sofia.uni.fmi.mjt.investment.wallet.exception.InsufficientResourcesException;
import bg.sofia.uni.fmi.mjt.investment.wallet.exception.OfferPriceException;
import bg.sofia.uni.fmi.mjt.investment.wallet.exception.UnknownAssetException;
import bg.sofia.uni.fmi.mjt.investment.wallet.exception.WalletException;
import bg.sofia.uni.fmi.mjt.investment.wallet.quote.Quote;
import bg.sofia.uni.fmi.mjt.investment.wallet.quote.QuoteService;

import java.time.LocalDateTime;
import java.util.*;

public class InvestmentWallet implements Wallet {
    private final QuoteService quoteService;
    private final Map<Asset, Integer> ownedAssets;
    private final Set<Acquisition> acquisitions;
    private double cashInWallet;

    public InvestmentWallet(QuoteService quoteService) {
        this.quoteService = quoteService;
        this.ownedAssets = new HashMap<>();
        this.acquisitions = new TreeSet<>(new Comparator<Acquisition>() {
            @Override
            public int compare(Acquisition o1, Acquisition o2) {
                return -Double.compare(o1.getPrice(), o2.getPrice());
            }
        });
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
        Quote quote = quoteService.getQuote(asset);
        AssetType type = asset.getType();

        if (asset == null || quantity < 0 || maxPrice < 0) {
            throw new IllegalArgumentException("Invalid argument passed to method buy");
        }

        if (quote == null) {
            throw new UnknownAssetException("Unknown asset: " + asset.getName());
        }

        if (maxPrice < quote.askPrice()) {
            throw new OfferPriceException("Ask price is higher than max price");
        }

        if (quantity * maxPrice > this.cashInWallet) {
            throw new InsufficientResourcesException("Not enough balance for the transaction");
        }

        if (!this.ownedAssets.containsKey(asset)) {
            this.ownedAssets.put(asset, quantity);
        } else {
            this.ownedAssets.put(asset, this.ownedAssets.get(asset) + quantity);
        }
        this.cashInWallet -= quantity * quote.askPrice();

        Acquisition currentAcquisition = new DefaultAcquisition(quote.askPrice(), LocalDateTime.now(), quantity, asset);
        this.acquisitions.add(currentAcquisition);
        return currentAcquisition;
    }

    @Override
    public double sell(Asset asset, int quantity, double minPrice) throws WalletException {
        Quote quote = quoteService.getQuote(asset);
        AssetType type = asset.getType();

        if (asset == null || quantity < 0 || minPrice < 0) {
            throw new IllegalArgumentException("Invalid argument passed to method buy");
        }

        if (quote == null) {
            throw new UnknownAssetException("No defined quote for asset: " + asset.getName());
        }

        if (this.ownedAssets.get(asset) < quantity) {
            throw new InsufficientResourcesException("Not enough quantity of asset: " + asset.getType().name());
        }

        if (quote.bidPrice() < minPrice) {
            throw new OfferPriceException("Bid price of the asset is lower than min price");
        }

        this.ownedAssets.put(asset, this.ownedAssets.get(asset) - quantity);
        this.cashInWallet += quantity * quote.bidPrice();

        return this.cashInWallet;
    }

    @Override
    public double getValuation() {
        double total = 0;
        for (Map.Entry<Asset, Integer> entry : this.ownedAssets.entrySet()) {
            double price = quoteService.getQuote(entry.getKey()).bidPrice();
            total += price * entry.getValue();
        }
        return total;
    }

    @Override
    public double getValuation(Asset asset) throws UnknownAssetException {
        double price = quoteService.getQuote(asset).bidPrice();

        if (this.ownedAssets.get(asset) == null) {
            throw new UnknownAssetException("Unknown asset: " + asset.getName());
        }
        int quantity = this.ownedAssets.get(asset);
        return price * quantity;
    }

    @Override
    public Asset getMostValuableAsset() {
        Asset mostValuable = null;
        double highestValue = -1;
        for (Map.Entry<Asset, Integer> entry : this.ownedAssets.entrySet()) {
            double currentValue = quoteService.getQuote(entry.getKey()).bidPrice() * entry.getValue();

            if (highestValue < currentValue) {
                highestValue = currentValue;
                mostValuable = entry.getKey();
            }
        }
        return mostValuable;
    }

    @Override
    public Collection<Acquisition> getAllAcquisitions() {
        return Collections.unmodifiableSet(this.acquisitions);
    }

    @Override
    public Set<Acquisition> getLastNAcquisitions(int n) {
        if (this.acquisitions.size() <= n) {
            return Collections.unmodifiableSet(this.acquisitions);
        }
        Set<Acquisition> topN = new TreeSet<>();
        for (int i = 0; i < n; i++) {
            topN.add((Acquisition) this.acquisitions.toArray()[i]);
        }
        return Collections.unmodifiableSet(topN);
    }
}
