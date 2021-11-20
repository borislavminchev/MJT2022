package bg.sofia.uni.fmi.mjt.cache;

import bg.sofia.uni.fmi.mjt.cache.storage.Storage;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;

@ExtendWith(MockitoExtension.class)
class CacheTest <K,V>{

    private Storage storage;
    private LeastFrequentlyUsedCache<K,V> lfuCache;
    private LeastRecentlyUsedCache<K,V> lruCache;

    @Test
    public void testCorrectSizeOne() {
        Mockito.when(storage.store("abc", "def")).thenReturn(null);
    }
}