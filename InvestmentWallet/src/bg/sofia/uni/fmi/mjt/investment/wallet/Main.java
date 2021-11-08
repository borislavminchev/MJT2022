package bg.sofia.uni.fmi.mjt.investment.wallet;

import java.util.HashMap;
import java.util.Map;

public class Main {

    public static void main(String[] args) {
        Map<String, Integer> map = new HashMap<>();
        map.put("aaa", 1);
        map.put("bbb", 2);
        map.put("ccc", 3);
        System.out.println(map.get("ddd"));
    }
}
