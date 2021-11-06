package bg.sofia.uni.fmi.mjt.investment.wallet.asset;

public abstract class AbstractAsset implements Asset {
    private final String id;
    private final String name;

    public AbstractAsset(String id, String name) {
        this.id = id;
        this.name = name;
    }

    @Override
    public String getId() {
        return null;
    }

    @Override
    public String getName() {
        return null;
    }
}
