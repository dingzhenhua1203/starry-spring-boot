package com.starry.starrycore.caches.GuavaCacheUtil;

import com.starry.starrycore.caches.MemoryCacheService;

/**
 * Guava是Google团队开源的一款 Java 核心增强库，
 * 包含集合、并发原语、缓存、IO、反射等工具箱，性能和稳定性上都有保障，应用十分广泛
 *
 * <dependency>
 *     <groupId>com.google.guava</groupId>
 *     <artifactId>guava</artifactId>
 *     <version>30.1.1-jre</version>
 * </dependency>
 */
public class GuavaCacheServiceImpl implements MemoryCacheService {
    @Override
    public <T> void SetCache(String cacheKey, T objObject, long expSeconds) {
        Cache<String, String> loadingCache = CacheBuilder.newBuilder()
                //cache的初始容量
                .initialCapacity(5)
                //cache最大缓存数
                .maximumSize(10)
                //设置写缓存后n秒钟过期
                .expireAfterWrite(17, TimeUnit.SECONDS)
                //设置读写缓存后n秒钟过期,实际很少用到,类似于expireAfterWrite
                //.expireAfterAccess(17, TimeUnit.SECONDS)
                .build();
        String key = "key";
        // 往缓存写数据
        loadingCache.put(key, "v");

        // 获取value的值，如果key不存在，调用collable方法获取value值加载到key中再返回
        String value = loadingCache.get(key, new Callable<String>() {
            @Override
            public String call() throws Exception {
                return getValueFromDB(key);
            }
        });

        // 删除key
        loadingCache.invalidate(key);
    }

    @Override
    public <T> T GetCache(String cacheKey) {
        return null;
    }

    @Override
    public Boolean DelCache(String cacheKey) {
        return null;
    }
}
