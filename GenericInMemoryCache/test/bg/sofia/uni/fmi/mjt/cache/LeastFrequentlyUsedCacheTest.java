package bg.sofia.uni.fmi.mjt.cache;

import bg.sofia.uni.fmi.mjt.cache.exception.ItemNotFound;
import bg.sofia.uni.fmi.mjt.cache.factory.CacheFactory;
import bg.sofia.uni.fmi.mjt.cache.factory.EvictionPolicy;
import bg.sofia.uni.fmi.mjt.cache.storage.Storage;
import org.junit.jupiter.api.Test;
import org.mockito.Mock;
import org.mockito.Mockito;

import java.util.HashMap;
import java.util.Map;

import static org.junit.jupiter.api.Assertions.*;

class LeastFrequentlyUsedCacheTest {

    @Mock
    private Storage<String, String> storage = Mockito.mock(Storage.class);

    private final LeastFrequentlyUsedCache<String, String> lfu = new LeastFrequentlyUsedCache<>(storage, 3);

    @Test
    public void defaultTests() {
        Cache testCache = CacheFactory.getInstance(storage, EvictionPolicy.LEAST_FREQUENTLY_USED);
        assertEquals(LeastFrequentlyUsedCache.class, testCache.getClass());

        assertThrows(IllegalArgumentException.class, () -> CacheFactory.getInstance(storage,
                -2, EvictionPolicy.LEAST_FREQUENTLY_USED));
        assertEquals(LeastFrequentlyUsedCache.class, CacheFactory.getInstance(storage,
                2, EvictionPolicy.LEAST_FREQUENTLY_USED).getClass());

    }

    @Test
    public void testValues() throws ItemNotFound
    {
        Mockito.when(storage.retrieve("abc")).thenReturn("def");
        lfu.get("abc");

        assertEquals(1, lfu.values().size());

    }
    @Test
    public void testGetZero() throws ItemNotFound {
        Mockito.when(storage.retrieve("abc")).thenReturn("def");
        assertEquals("def", lfu.get("abc"));
        assertEquals(0.0, lfu.getHitRate());
    }

    @Test
    public void testGetOne() throws ItemNotFound {
        Mockito.when(storage.retrieve("abc")).thenReturn("def");
        Mockito.when(storage.retrieve("a")).thenReturn(null);
        lfu.put("abc", "def");

        assertEquals("def", lfu.get("abc"));
        assertThrows(IllegalArgumentException.class, () -> lfu.get(null));
        assertEquals(1.0, lfu.getHitRate());
        assertThrows(ItemNotFound.class, () -> lfu.get("a"));
    }

    @Test
    public void testGetHalf() throws ItemNotFound {
        Mockito.when(storage.retrieve("abc")).thenReturn("def");
        Mockito.when(storage.retrieve("aaa")).thenReturn("bbb");

        lfu.put("abc", "def");

        assertEquals("def", lfu.get("abc"));
        assertEquals("bbb", lfu.get("aaa"));
        assertEquals(0.5, lfu.getHitRate());
    }

    @Test
    public void testGetThird() throws ItemNotFound {
        Mockito.when(storage.retrieve("abc")).thenReturn("def");
        Mockito.when(storage.retrieve("aaa")).thenReturn("bbb");
        Mockito.when(storage.retrieve("a")).thenReturn("b");

        lfu.put("abc", "def");

        assertEquals("def", lfu.get("abc"));
        assertEquals("bbb", lfu.get("aaa"));
        assertEquals("b", lfu.get("a"));
        assertEquals(1.0 / 3, lfu.getHitRate());
    }

    @Test
    public void testResetHitRateZero() throws ItemNotFound {
        Mockito.when(storage.retrieve("abc")).thenReturn("def");
        lfu.get("abc");

        assertEquals(0, lfu.getHitRate());
        lfu.resetHitRate();
        assertEquals(0, lfu.getHitRate());
    }

    @Test
    public void testResetHitRateOne() throws ItemNotFound {
        Mockito.when(storage.retrieve("abc")).thenReturn("def");
        lfu.put("abc", "def");
        lfu.get("abc");

        assertEquals(1.0, lfu.getHitRate());
        lfu.resetHitRate();
        assertEquals(0, lfu.getHitRate());
    }

    @Test
    public void testResetHitRateHalf() throws ItemNotFound {
        Mockito.when(storage.retrieve("abc")).thenReturn("def");
        Mockito.when(storage.retrieve("aaa")).thenReturn("bbb");

        lfu.put("abc", "def");
        lfu.get("abc");
        lfu.get("aaa");

        assertEquals(0.5, lfu.getHitRate());
        lfu.resetHitRate();
        assertEquals(0, lfu.getHitRate());
    }

    @Test
    public void testResetHitRateHalfToOne() throws ItemNotFound {
        Mockito.when(storage.retrieve("abc")).thenReturn("def");
        Mockito.when(storage.retrieve("aaa")).thenReturn("bbb");

        lfu.put("abc", "def");
        lfu.get("abc");
        lfu.get("aaa");

        assertEquals(0.5, lfu.getHitRate());
        lfu.resetHitRate();
        assertEquals(0, lfu.getHitRate());
        lfu.get("abc");
        lfu.get("aaa");
        assertEquals(1, lfu.getHitRate());
    }

    @Test
    public void testResetHitRateHalfToTwoThirds() throws ItemNotFound {
        Mockito.when(storage.retrieve("abc")).thenReturn("def");
        Mockito.when(storage.retrieve("aaa")).thenReturn("bbb");
        Mockito.when(storage.retrieve("a")).thenReturn("b");

        lfu.put("abc", "def");
        lfu.get("abc");
        lfu.get("aaa");

        assertEquals(0.5, lfu.getHitRate());
        lfu.resetHitRate();
        assertEquals(0, lfu.getHitRate());
        lfu.get("abc");
        lfu.get("aaa");
        lfu.get("a");
        assertEquals(2.0 / 3, lfu.getHitRate());
    }

    @Test
    public void testGetFromCacheEmpty() {
        Mockito.when(storage.retrieve("abc")).thenReturn("def");

        assertNull(lfu.getFromCache("abc"));
    }

    @Test
    public void testGetFromCacheOne() throws ItemNotFound {
        Mockito.when(storage.retrieve("abc")).thenReturn("def");
        lfu.get("abc");

        assertEquals("def", lfu.getFromCache("abc"));
    }

    @Test
    public void testGetFromCacheTwo() throws ItemNotFound {
        Mockito.when(storage.retrieve("abc")).thenReturn("def");
        lfu.put("a", "b");
        lfu.get("abc");

        assertEquals("def", lfu.getFromCache("abc"));
        assertEquals("b", lfu.getFromCache("a"));
    }

    @Test
    public void testClearZero() throws ItemNotFound {
        Mockito.when(storage.retrieve("abc")).thenReturn("def");
        lfu.get("abc");

        assertEquals(0, lfu.getHitRate());
        lfu.clear();

        assertNull(lfu.getFromCache("abc"));
        assertEquals(0, lfu.getHitRate());
    }

    @Test
    public void testClearOne() throws ItemNotFound {
        Mockito.when(storage.retrieve("abc")).thenReturn("def");
        lfu.put("abc", "def");
        lfu.get("abc");

        assertEquals(1.0, lfu.getHitRate());
        lfu.clear();
        assertNull(lfu.getFromCache("abc"));
        assertEquals(0, lfu.getHitRate());
    }

    @Test
    public void testClearHalf() throws ItemNotFound {
        Mockito.when(storage.retrieve("abc")).thenReturn("def");
        Mockito.when(storage.retrieve("aaa")).thenReturn("bbb");

        lfu.put("abc", "def");
        lfu.get("abc");
        lfu.get("aaa");

        assertEquals(0.5, lfu.getHitRate());
        lfu.clear();
        assertNull(lfu.getFromCache("abc"));
        assertNull(lfu.getFromCache("aaa"));
        assertEquals(0, lfu.getHitRate());
    }

    @Test
    public void testClearHalfToOne() throws ItemNotFound {
        Mockito.when(storage.retrieve("abc")).thenReturn("def");
        Mockito.when(storage.retrieve("aaa")).thenReturn("bbb");

        lfu.put("abc", "def");
        lfu.get("abc");
        lfu.get("aaa");

        assertEquals(0.5, lfu.getHitRate());
        lfu.clear();
        assertNull(lfu.getFromCache("abc"));
        assertNull(lfu.getFromCache("aaa"));
        assertEquals(0, lfu.getHitRate());
        lfu.get("abc");
        lfu.get("aaa");
        assertEquals("def", lfu.getFromCache("abc"));
        assertEquals("bbb", lfu.getFromCache("aaa"));
        assertEquals(0, lfu.getHitRate());
    }

    @Test
    public void testClearHalfToTwoThirds() throws ItemNotFound {
        Mockito.when(storage.retrieve("abc")).thenReturn("def");
        Mockito.when(storage.retrieve("aaa")).thenReturn("bbb");
        Mockito.when(storage.retrieve("a")).thenReturn("b");

        lfu.put("abc", "def");
        lfu.get("abc");
        lfu.get("aaa");

        assertEquals(0.5, lfu.getHitRate());
        lfu.clear();
        assertNull(lfu.getFromCache("abc"));
        assertNull(lfu.getFromCache("aaa"));
        assertEquals(0, lfu.getHitRate());
        lfu.get("abc");
        lfu.get("aaa");
        lfu.get("a");
        assertEquals("def", lfu.getFromCache("abc"));
        assertEquals("bbb", lfu.getFromCache("aaa"));
        assertEquals("b", lfu.getFromCache("a"));
        assertEquals(0, lfu.getHitRate());
    }

    @Test
    public void testEvictFromCacheEmpty() {
        assertNull(lfu.getFromCache("a"));
        lfu.evictFromCache();
        assertNull(lfu.getFromCache("a"));
    }

    @Test
    public void testEvictFromCacheOne() {
        lfu.put("a", "b");
        assertTrue(lfu.containsKey("a"));
        lfu.evictFromCache();
        assertFalse(lfu.containsKey("a"));
    }

//    @Test
//    public void testEvictFromCacheTwoEqual() {
//        lfu.put("a", "b");
//        lfu.put("aaa", "bbb");
//        assertTrue(lfu.containsKey("a"));
//        assertTrue(lfu.containsKey("aaa"));
//        lfu.evictFromCache();
//        assertFalse(lfu.containsKey("aaa"));
//    }

    @Test
    public void testEvictFromCacheTwo() {
        lfu.put("a", "b");
        lfu.put("aaa", "bbb");
        lfu.put("aaa", "bbb");
        assertTrue(lfu.containsKey("a"));
        assertTrue(lfu.containsKey("aaa"));
        lfu.evictFromCache();
        assertFalse(lfu.containsKey("a"));
    }

    @Test
    public void testEvictFromCacheTwoLargerUse() {
        lfu.put("a", "b");
        lfu.put("a", "b");
        lfu.put("aaa", "bbb");
        lfu.put("a", "b");
        lfu.put("aaa", "bbb");
        lfu.put("a", "b");
        lfu.put("a", "b");
        assertTrue(lfu.containsKey("a"));
        assertTrue(lfu.containsKey("aaa"));
        lfu.evictFromCache();
        assertFalse(lfu.containsKey("aaa"));
    }

    @Test
    public void testGetExceedingCapacity() throws ItemNotFound {
        Mockito.when(storage.retrieve("abc")).thenReturn("def");
        Mockito.when(storage.retrieve("aaa")).thenReturn("bbb");
        Mockito.when(storage.retrieve("a")).thenReturn("b");
        Mockito.when(storage.retrieve("test")).thenReturn("test123");

        lfu.get("a");
        lfu.get("abc");
        lfu.get("a");
        lfu.get("aaa");
        lfu.get("a");
        lfu.get("aaa");
        lfu.get("abc");
        lfu.get("aaa");
        lfu.get("aaa");
        lfu.get("aaa");
        assertTrue(lfu.containsKey("a"));
        assertTrue(lfu.containsKey("aaa"));
        assertTrue(lfu.containsKey("abc"));
        lfu.get("test");
        assertTrue(lfu.containsKey("a"));
        assertTrue(lfu.containsKey("aaa"));
        assertTrue(lfu.containsKey("test"));
        assertFalse(lfu.containsKey("abc"));

    }

}