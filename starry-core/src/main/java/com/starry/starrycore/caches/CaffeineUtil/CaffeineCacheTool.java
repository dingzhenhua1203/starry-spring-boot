package com.starry.starrycore.caches.CaffeineUtil;

import com.github.benmanes.caffeine.cache.Cache;
import com.github.benmanes.caffeine.cache.Caffeine;
import com.starry.starrycore.JsonUtil;
import com.starry.starrycore.caches.MemoryCacheService;
import org.springframework.cache.annotation.EnableCaching;

import java.util.concurrent.TimeUnit;

/**
 * <dependency>
 * <groupId>com.github.ben-manes.caffeine</groupId>
 * <artifactId>caffeine</artifactId>
 * <version>2.6.2</version>
 * </dependency>
 */

/**
 * initialCapacity ：整数，缓存初始容量,表示能存储多少个缓存对象,尽量减少缓存的不断地进行扩容
 * <p>
 * 最大容量 和 最大权重 只能二选一作为缓存空间的限制
 * maximumSize ：最大容量，如果缓存中的数据量超过这个数值，Caffeine 会有一个异步线程来专门负责清除缓存，
 * 按照指定的清除策略来清除掉多余的缓存。
 * 注意：比如最大容量是 2，此时已经存入了2个数据了，此时存入第3个数据，触发异步线程清除缓存，
 * 在清除操作没有完成之前，缓存中仍然有3个数据，且 3 个数据均可读，缓存的大小也是 3，
 * 只有当缓存操作完成了，缓存中才只剩 2 个数据，
 * 至于清除掉了哪个数据，这就要看清除策略了
 * <p>
 * maximumWeight：最大权重，存入缓存的每个元素都要有一个权重值，当缓存中所有元素的权重值超过最大权重时，就会触发异步清除
 * <p>
 * expireAfterAccess 最后一次访问之后，隔多久没有被再次访问的话，就过期
 * <p>
 * expireAfterWrite 只能是被更新，才能延续数据的生命，即便是数据被读取了，也不行，时间一到，也会过期
 * <p>
 * <p>
 * V getIfPresent(K key) ：如果缓存中 key 存在，则获取 value，否则返回 null。
 * void put( K key, V value)：存入一对数据 <key, value>。
 * Map<K, V> getAllPresent(Iterable<?> var1) ：参数是一个迭代器，表示可以批量查询缓存。
 * void putAll( Map<? extends K, ? extends V> var1); 批量存入缓存。
 * void invalidate(K var1)：删除某个 key 对应的数据。
 * void invalidateAll(Iterable<?> var1)：批量删除数据。
 * void invalidateAll()：清空缓存。
 * long estimatedSize()：返回缓存中数据的个数。
 * CacheStats stats()：返回缓存当前的状态指标集。
 * ConcurrentMap<K, V> asMap()：将缓存中所有的数据构成一个 map。
 * void cleanUp()：会对缓存进行整体的清理，比如有一些数据过期了，但是并不会立马被清除，所以执行一次 cleanUp 方法，会对缓存进行一次检查，清除那些应该清除的数据。
 * V get( K var1, Function<? super K, ? extends V> var2)：第一个参数是想要获取的 key，第二个参数是函数，例子如下：
 */
@EnableCaching// 开启缓存，需要显示的指定
public class CaffeineCacheTool implements MemoryCacheService {
    private final Cache<Object, Object> cacheMap;
    public volatile static CaffeineCacheTool instance;

    private CaffeineCacheTool() {
        cacheMap = Caffeine.newBuilder()
                .initialCapacity(16)
                .maximumSize(128)
                .expireAfterAccess(2, TimeUnit.HOURS)
                //.maximumWeight(30)
                // .weigher((String key, Object value) -> 1)
                .build();
    }

    public static CaffeineCacheTool getInstance() {
        if (instance == null) {
            synchronized (CaffeineCacheTool.class) {
                if (instance == null) {
                    instance = new CaffeineCacheTool();
                }
            }
        }
        return instance;
    }

    public Boolean putCache(String key, Object obj) {
        cacheMap.put(key, obj);
        return true;
    }

    public <T> T getCache(String key, Class<T> clazz) {
        T obj = (T) cacheMap.getIfPresent(key);
        if (obj != null)
            return JsonUtil.parseObject(JsonUtil.parseString(obj), clazz);

        return null;
    }

    public Boolean delCache(String key) {
        cacheMap.invalidate(key);
        return true;
    }

    public Boolean cleanAll() {
        cacheMap.invalidateAll();
        return true;
    }
}

