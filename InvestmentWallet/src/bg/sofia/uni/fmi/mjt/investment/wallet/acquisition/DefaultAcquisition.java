package bg.sofia.uni.fmi.mjt.investment.wallet.acquisition;

import bg.sofia.uni.fmi.mjt.investment.wallet.asset.Asset;

import java.time.LocalDateTime;

public class DefaultAcquisition implements Acquisition {
    private final double price;
    private final LocalDateTime timestamp;
    private final int quantity;
    private final Asset asset;

    public DefaultAcquisition(double price, LocalDateTime timestamp, int quantity, Asset asset) {
        this.price = price;
        this.timestamp = timestamp;
        this.quantity = quantity;
        this.asset = asset;
    }

    @Override
    public double getPrice() {
        return this.price;
    }

    @Override
    public LocalDateTime getTimestamp() {
        return this.timestamp;
    }

    @Override
    public int getQuantity() {
        return this.quantity;
    }

    @Override
    public Asset getAsset() {
        return this.asset;
    }
}
