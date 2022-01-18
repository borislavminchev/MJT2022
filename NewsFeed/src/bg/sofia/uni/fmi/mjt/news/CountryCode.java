package bg.sofia.uni.fmi.mjt.news;

public enum CountryCode {
    AE("ae"),AR("ar"),AT("at"),AU("au"),BE("be"),BG("bg"),
    BR("br"),CA("ca"),CH("ch"),CN("cn"),CO("co"),CU("cu"),
    CZ("cz"),DE("de"),EG("eg"),FR("fr"),GB("gb"),GR("gr"),
    HK("hk"),HU("hu"),ID("id"),IE("ie"),IL("il"),IN("in"),
    IT("it"),JP("jp"),KR("kr"),LT("lt"),LV("lv"),MA("ma"),
    MX("mx"),MY("my"),NG("ng"),NL("nl"),NO("no"),NZ("nz"),
    PH("ph"),PL("pl"),PT("pt"),RO("ro"),RS("rs"),RU("ru"),
    SA("sa"),SE("se"),SG("sg"),SI("si"),SK("sk"),TH("th"),
    TR("tr"),TW("tw"),UA("ua"),US("us"),VE("ve"),ZA("za");

    private final String value;

    CountryCode(String value) {
        this.value = value;
    }

    public String getValue() {
        return value;
    }
}
