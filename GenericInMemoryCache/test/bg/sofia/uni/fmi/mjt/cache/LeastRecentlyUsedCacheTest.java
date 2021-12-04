package bg.sofia.uni.fmi.mjt.cache;

import bg.sofia.uni.fmi.mjt.cache.exception.ItemNotFound;
import bg.sofia.uni.fmi.mjt.cache.factory.CacheFactory;
import bg.sofia.uni.fmi.mjt.cache.factory.EvictionPolicy;
import bg.sofia.uni.fmi.mjt.cache.storage.Storage;
import org.junit.jupiter.api.Test;
import org.mockito.Mock;
import org.mockito.Mockito;

import static org.junit.jupiter.api.Assertions.*;

class LeastRecentlyUsedCacheTest {
    @Mock
    private Storage<String, String> storage = Mockito.mock(Storage.class);

    private final LeastRecentlyUsedCache<String, String> lru = new LeastRecentlyUsedCache<>(storage, 3);

    @Test
    public void defaultTests() {
        Cache testCache = CacheFactory.getInstance(storage, EvictionPolicy.LEAST_RECENTLY_USED);
        assertEquals(LeastRecentlyUsedCache.class, testCache.getClass());

        assertThrows(IllegalArgumentException.class, () -> CacheFactory.getInstance(storage,
                -2, EvictionPolicy.LEAST_RECENTLY_USED));
        assertEquals(LeastRecentlyUsedCache.class, CacheFactory.getInstance(storage,
                2, EvictionPolicy.LEAST_RECENTLY_USED).getClass());
    }

    @Test
    public void testValues() throws ItemNotFound
    {
        Mockito.when(storage.retrieve("abc")).thenReturn("def");
        lru.get("abc");

        assertEquals(1, lru.values().size());

    }

    @Test
    public void testGetZero() throws ItemNotFound {
        Mockito.when(storage.retrieve("abc")).thenReturn("def");
        assertEquals("def", lru.get("abc"));
        assertEquals(0.0, lru.getHitRate());
    }

    @Test
    public void testGetOne() throws ItemNotFound {
        Mockito.when(storage.retrieve("abc")).thenReturn("def");
        Mockito.when(storage.retrieve("a")).thenReturn(null);
        lru.put("abc", "def");

        assertEquals("def", lru.get("abc"));
        assertThrows(IllegalArgumentException.class, () -> lru.get(null));
        assertEquals(1.0, lru.getHitRate());
        assertThrows(ItemNotFound.class, () -> lru.get("a"));
    }

    @Test
    public void testGetHalf() throws ItemNotFound {
        Mockito.when(storage.retrieve("abc")).thenReturn("def");
        Mockito.when(storage.retrieve("aaa")).thenReturn("bbb");

        lru.put("abc", "def");

        assertEquals("def", lru.get("abc"));
        assertEquals("bbb", lru.get("aaa"));
        assertEquals(0.5, lru.getHitRate());
    }

    @Test
    public void testGetThird() throws ItemNotFound {
        Mockito.when(storage.retrieve("abc")).thenReturn("def");
        Mockito.when(storage.retrieve("aaa")).thenReturn("bbb");
        Mockito.when(storage.retrieve("a")).thenReturn("b");

        lru.put("abc", "def");

        assertEquals("def", lru.get("abc"));
        assertEquals("bbb", lru.get("aaa"));
        assertEquals("b", lru.get("a"));
        assertEquals(1.0 / 3, lru.getHitRate());
    }

    @Test
    public void testResetHitRateZero() throws ItemNotFound {
        Mockito.when(storage.retrieve("abc")).thenReturn("def");
        lru.get("abc");

        assertEquals(0, lru.getHitRate());
        lru.resetHitRate();
        assertEquals(0, lru.getHitRate());
    }

    @Test
    public void testResetHitRateOne() throws ItemNotFound {
        Mockito.when(storage.retrieve("abc")).thenReturn("def");
        lru.put("abc", "def");
        lru.get("abc");

        assertEquals(1.0, lru.getHitRate());
        lru.resetHitRate();
        assertEquals(0, lru.getHitRate());
    }

    @Test
    public void testResetHitRateHalf() throws ItemNotFound {
        Mockito.when(storage.retrieve("abc")).thenReturn("def");
        Mockito.when(storage.retrieve("aaa")).thenReturn("bbb");

        lru.put("abc", "def");
        lru.get("abc");
        lru.get("aaa");

        assertEquals(0.5, lru.getHitRate());
        lru.resetHitRate();
        assertEquals(0, lru.getHitRate());
    }

    @Test
    public void testResetHitRateHalfToOne() throws ItemNotFound {
        Mockito.when(storage.retrieve("abc")).thenReturn("def");
        Mockito.when(storage.retrieve("aaa")).thenReturn("bbb");

        lru.put("abc", "def");
        lru.get("abc");
        lru.get("aaa");

        assertEquals(0.5, lru.getHitRate());
        lru.resetHitRate();
        assertEquals(0, lru.getHitRate());
        lru.get("abc");
        lru.get("aaa");
        assertEquals(1, lru.getHitRate());
    }

    @Test
    public void testResetHitRateHalfToTwoThirds() throws ItemNotFound {
        Mockito.when(storage.retrieve("abc")).thenReturn("def");
        Mockito.when(storage.retrieve("aaa")).thenReturn("bbb");
        Mockito.when(storage.retrieve("a")).thenReturn("b");

        lru.put("abc", "def");
        lru.get("abc");
        lru.get("aaa");

        assertEquals(0.5, lru.getHitRate());
        lru.resetHitRate();
        assertEquals(0, lru.getHitRate());
        lru.get("abc");
        lru.get("aaa");
        lru.get("a");
        assertEquals(2.0 / 3, lru.getHitRate());
    }

    @Test
    public void testGetFromCacheEmpty() {
        Mockito.when(storage.retrieve("abc")).thenReturn("def");

        assertNull(lru.getFromCache("abc"));
    }

    @Test
    public void testGetFromCacheOne() throws ItemNotFound {
        Mockito.when(storage.retrieve("abc")).thenReturn("def");
        lru.get("abc");

        assertEquals("def", lru.getFromCache("abc"));
    }

    @Test
    public void testGetFromCacheTwo() throws ItemNotFound {
        Mockito.when(storage.retrieve("abc")).thenReturn("def");
        lru.put("a", "b");
        lru.get("abc");

        assertEquals("def", lru.getFromCache("abc"));
        assertEquals("b", lru.getFromCache("a"));
    }

    @Test
    public void testClearZero() throws ItemNotFound {
        Mockito.when(storage.retrieve("abc")).thenReturn("def");
        lru.get("abc");

        assertEquals(0, lru.getHitRate());
        lru.clear();
        assertNull(lru.getFromCache("abc"));
        assertEquals(0, lru.getHitRate());
    }

    @Test
    public void testClearOne() throws ItemNotFound {
        Mockito.when(storage.retrieve("abc")).thenReturn("def");
        lru.put("abc", "def");
        lru.get("abc");

        assertEquals(1.0, lru.getHitRate());
        lru.clear();
        assertNull(lru.getFromCache("abc"));
        assertEquals(0, lru.getHitRate());
    }

    @Test
    public void testClearHalf() throws ItemNotFound {
        Mockito.when(storage.retrieve("abc")).thenReturn("def");
        Mockito.when(storage.retrieve("aaa")).thenReturn("bbb");

        lru.put("abc", "def");
        lru.get("abc");
        lru.get("aaa");

        assertEquals(0.5, lru.getHitRate());
        lru.clear();
        assertNull(lru.getFromCache("abc"));
        assertNull(lru.getFromCache("aaa"));
        assertEquals(0, lru.getHitRate());
    }

    @Test
    public void testClearHalfToOne() throws ItemNotFound {
        Mockito.when(storage.retrieve("abc")).thenReturn("def");
        Mockito.when(storage.retrieve("aaa")).thenReturn("bbb");

        lru.put("abc", "def");
        lru.get("abc");
        lru.get("aaa");

        assertEquals(0.5, lru.getHitRate());
        lru.clear();
        assertNull(lru.getFromCache("abc"));
        assertNull(lru.getFromCache("aaa"));
        assertEquals(0, lru.getHitRate());
        lru.get("abc");
        lru.get("aaa");
        assertEquals("def", lru.getFromCache("abc"));
        assertEquals("bbb", lru.getFromCache("aaa"));
        assertEquals(0, lru.getHitRate());
    }

    @Test
    public void testClearHalfToTwoThirds() throws ItemNotFound {
        Mockito.when(storage.retrieve("abc")).thenReturn("def");
        Mockito.when(storage.retrieve("aaa")).thenReturn("bbb");
        Mockito.when(storage.retrieve("a")).thenReturn("b");

        lru.put("abc", "def");
        lru.get("abc");
        lru.get("aaa");

        assertEquals(0.5, lru.getHitRate());
        lru.clear();
        assertNull(lru.getFromCache("abc"));
        assertNull(lru.getFromCache("aaa"));
        assertEquals(0, lru.getHitRate());
        lru.get("abc");
        lru.get("aaa");
        lru.get("a");
        assertEquals("def", lru.getFromCache("abc"));
        assertEquals("bbb", lru.getFromCache("aaa"));
        assertEquals("b", lru.getFromCache("a"));
        assertEquals(0, lru.getHitRate());
    }

    @Test
    public void testEvictFromCacheEmpty() {
        assertNull(lru.getFromCache("a"));
        lru.evictFromCache();
        assertNull(lru.getFromCache("a"));
    }

    @Test
    public void testEvictFromCacheOne() {
        lru.put("a", "b");
        assertTrue(lru.containsKey("a"));
        lru.evictFromCache();
        assertFalse(lru.containsKey("a"));
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
        lru.put("a", "b");
        lru.put("aaa", "bbb");
        lru.put("aaa", "bbb");
        assertTrue(lru.containsKey("a"));
        assertTrue(lru.containsKey("aaa"));
        lru.evictFromCache();
        assertFalse(lru.containsKey("a"));
    }

    @Test
    public void testEvictFromCacheTwoLargerUse() {
        lru.put("a", "b");
        lru.put("a", "b");
        lru.put("aaa", "bbb");
        lru.put("a", "b");
        lru.put("aaa", "bbb");
        lru.put("a", "b");
        lru.put("a", "b");
        assertTrue(lru.containsKey("a"));
        assertTrue(lru.containsKey("aaa"));
        lru.evictFromCache();
        assertFalse(lru.containsKey("aaa"));
    }

    @Test
    public void testGetExceedingCapacitySimple() throws ItemNotFound {
        Mockito.when(storage.retrieve("abc")).thenReturn("def");
        Mockito.when(storage.retrieve("aaa")).thenReturn("bbb");
        Mockito.when(storage.retrieve("a")).thenReturn("b");
        Mockito.when(storage.retrieve("test")).thenReturn("test123");

        lru.get("abc");
        lru.get("a");
        lru.get("aaa");

        assertTrue(lru.containsKey("a"));
        assertTrue(lru.containsKey("aaa"));
        assertTrue(lru.containsKey("abc"));
        lru.get("test");
        assertTrue(lru.containsKey("a"));
        assertTrue(lru.containsKey("aaa"));
        assertTrue(lru.containsKey("test"));
        assertFalse(lru.containsKey("abc"));

    }

    @Test
    public void testGetExceedingCapacityComplex() throws ItemNotFound {
        Mockito.when(storage.retrieve("abc")).thenReturn("def");
        Mockito.when(storage.retrieve("aaa")).thenReturn("bbb");
        Mockito.when(storage.retrieve("a")).thenReturn("b");
        Mockito.when(storage.retrieve("test")).thenReturn("test123");

        lru.get("abc");
        lru.get("a");
        lru.get("aaa");
        lru.get("a");
        lru.get("a");
        lru.get("aaa");
        lru.get("abc");
        lru.get("aaa");
        lru.get("aaa");
        lru.get("aaa");

        assertTrue(lru.containsKey("a"));
        assertTrue(lru.containsKey("aaa"));
        assertTrue(lru.containsKey("abc"));
        lru.get("test");
        assertTrue(lru.containsKey("abc"));
        assertTrue(lru.containsKey("aaa"));
        assertTrue(lru.containsKey("test"));
        assertFalse(lru.containsKey("a"));

    }
}