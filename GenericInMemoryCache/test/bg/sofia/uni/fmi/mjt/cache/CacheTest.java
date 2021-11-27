package bg.sofia.uni.fmi.mjt.cache;

import bg.sofia.uni.fmi.mjt.cache.exception.ItemNotFound;
import bg.sofia.uni.fmi.mjt.cache.factory.CacheFactory;
import bg.sofia.uni.fmi.mjt.cache.factory.EvictionPolicy;
import bg.sofia.uni.fmi.mjt.cache.storage.Storage;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.Collections;
import java.util.List;

@ExtendWith(MockitoExtension.class)
class CacheTest {

    @Mock
    Cache<String, String> cache;

    @InjectMocks
    private LeastRecentlyUsedCache<String, String> lru;

    @Test
    public void testCorrectSizeOne() throws ItemNotFound {
//        Mockito.when(cache.values()).thenReturn(List.of(""))
        Mockito.when(cache.get("abc")).thenReturn("123");

    }
}