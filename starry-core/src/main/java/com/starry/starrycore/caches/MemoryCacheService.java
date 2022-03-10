package com.starry.starrycore.caches;

import java.util.concurrent.ExecutionException;

public interface MemoryCacheService {
    <T> void SetCache(String cacheKey, T objObject, long expSeconds) throws ExecutionException;

    <T> T GetCache(String cacheKey);

    Boolean DelCache(String cacheKey);
}
