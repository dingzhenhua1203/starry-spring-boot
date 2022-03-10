package com.starry.starrycore.caches;

public interface MemoryCacheService {
    <T> void SetCache(String cacheKey, T objObject, long expSeconds);

    <T> T GetCache(String cacheKey);

    Boolean DelCache(String cacheKey);
}
