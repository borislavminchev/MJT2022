package bg.sofia.uni.fmi.mjt.cache;

import bg.sofia.uni.fmi.mjt.cache.factory.CacheFactory;
import bg.sofia.uni.fmi.mjt.cache.factory.EvictionPolicy;
import bg.sofia.uni.fmi.mjt.cache.storage.Storage;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;

@ExtendWith(MockitoExtension.class)
class CacheTest {

    private Storage<String, String> storage = null;
    private Cache<String, String> lfuCache = CacheFactory.getInstance(storage, EvictionPolicy.LEAST_FREQUENTLY_USED);
    private LeastRecentlyUsedCache<String, String> lruCache;

    @Test
    public void testCorrectSizeOne() {
  
    }
}