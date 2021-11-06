package bg.sofia.uni.fmi.mjt.investment.wallet.asset;

public class Fiat extends AbstractAsset {

    public Fiat(String id, String name) {
        super(id, name);
    }

    @Override
    public AssetType getType() {
        return AssetType.FIAT;
    }

}
