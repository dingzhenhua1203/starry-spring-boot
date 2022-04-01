package com.starry.starrycore.caches;

import java.util.concurrent.ExecutionException;

public interface MemoryCacheService {

    <T> Boolean putCache(String key, T obj);

    <T> T getCache(String key, Class<T> clazz);

    Boolean delCache(String key);

    Boolean cleanAll();
}
