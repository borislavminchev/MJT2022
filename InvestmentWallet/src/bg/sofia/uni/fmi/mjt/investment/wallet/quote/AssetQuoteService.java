package bg.sofia.uni.fmi.mjt.investment.wallet.quote;

import bg.sofia.uni.fmi.mjt.investment.wallet.asset.Asset;

public class AssetQuoteService implements QuoteService {
    //private static final int MIN_
    @Override
    public Quote getQuote(Asset asset) {
        return switch (asset.getType()) {
            case CRYPTO -> new Quote(105, 100);
            case FIAT -> new Quote(1.08, 1);
            case GOLD -> new Quote(506, 500);
            case STOCK -> new Quote(42, 38);
        };
    }
}
