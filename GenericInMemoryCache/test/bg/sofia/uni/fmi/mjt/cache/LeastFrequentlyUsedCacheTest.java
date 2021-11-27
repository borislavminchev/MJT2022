package bg.sofia.uni.fmi.mjt.cache;

import  bg.sofia.uni.fmi.mjt.cache.factory.CacheFactory;
import bg.sofia.uni.fmi.mjt.cache.factory.EvictionPolicy;
import bg.sofia.uni.fmi.mjt.cache.storage.Storage;
import org.junit.jupiter.api.Test;
import org.mockito.Mock;

import static org.junit.jupiter.api.Assertions.*;

class LeastFrequentlyUsedCacheTest {

    @Mock
    private Storage<Integer, Integer> storage;

//    private LeastRecentlyUsedCache<Integer, Integer> lru;

    @Test
    void get() {
        LeastRecentlyUsedCache<Integer, Integer> lru = new LeastRecentlyUsedCache<>(storage, 10);
    }

    @Test
    void getFromCache() {
    }

    @Test
    void put() {
    }

    @Test
    void containsKey() {
    }

    @Test
    void evictFromCache() {
    }
}