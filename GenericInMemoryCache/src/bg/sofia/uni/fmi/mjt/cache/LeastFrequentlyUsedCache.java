package bg.sofia.uni.fmi.mjt.cache;

import bg.sofia.uni.fmi.mjt.cache.storage.Storage;

import java.util.Collection;
import java.util.HashMap;
import java.util.Map;

public class LeastFrequentlyUsedCache<K, V> extends CacheBase<K, V> {
    private final HashMap<K, V> cache;
    private final Map<K, Integer> keyUses;

    public LeastFrequentlyUsedCache(Storage<K, V> storage, int capacity) {
        super(storage, capacity);
        cache = new HashMap<>(capacity);
        keyUses = new HashMap<>();
    }

    @Override
    public int size() {
        return this.cache.size();
    }

    @Override
    public Collection<V> values() {
        return this.cache.values();
    }

    @Override
    public void clear() {
        super.resetHitRate();
        cache.clear();
        keyUses.clear();
    }

    protected V getFromCache(K key) {
        return cache.get(key);
    }

    protected V put(K key, V value) {
        if (keyUses.containsKey(key)) {
            keyUses.put(key, keyUses.get(key) + 1);
        } else {
            keyUses.put(key, 1);
        }

        return cache.put(key, value);
    }

    protected boolean containsKey(K k) {
        return this.cache.containsKey(k);
    }

    protected void evictFromCache() {
        K evictionKey = getEvictionKey();
        cache.remove(evictionKey);
        keyUses.remove(evictionKey);
    }

    private K getEvictionKey() {
        int minUsageCount = 0;
        K toBeRemoved = null;

        for (K key : keyUses.keySet()) {
            int usageCount = keyUses.get(key);
            if (minUsageCount == 0 || usageCount < minUsageCount) {
                toBeRemoved = key;
                minUsageCount = usageCount;
            }
        }

        return toBeRemoved;
    }

}