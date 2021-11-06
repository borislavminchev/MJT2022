package bg.sofia.uni.fmi.mjt.investment.wallet.asset;

public class Gold implements Asset {
    private final String id;
    private final String name;

    public Gold(String id, String name) {
        this.id = id;
        this.name = name;
    }

    @Override
    public String getId() {
        return this.id;
    }

    @Override
    public String getName() {
        return this.name;
    }

    @Override
    public AssetType getType() {
        return AssetType.GOLD;
    }
}
