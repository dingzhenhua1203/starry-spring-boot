package com.starry.starrycore.caches;

import com.starry.starrycore.caches.CaffeineUtil.CaffeineCacheTool;

/**
 * @author : eric.ding
 * 分布式协同内存
 * 通过服务器ip和redis的incr以及set数据结构来保证分布式系统内存统一
 */
public class DistributeAssistMemoryCacheHelper {

    /**
     * 全局负载下的同步锁,当数据修改后，通过此key来通知到各个负载机器更新内存缓存数据
     * key作为string类型是是增量版本号，每次内容变化都需要incr提升版本
     * 然后SetLocalReceivedTag中会用此key拼接$"{key}:{version}"来作为set结构的key,用来存放已处理的服务器ip
     * 以此保证内存在分布式系统中一致性
     *
     * @param cacheKey 缓存的key
     * @return 全局唯一锁
     */
    private static String GlobleLockKey(String cacheKey) {
        return cacheKey + ".lock";
    }

    /**
     * 设置数据缓存
     * <param name="cacheKey">缓存Key</param>
     * <param name="objObject">缓存内容</param>
     * <param name="isNotifyAll">是否需要通知分布式系统其他机器，比如内容变化的则要通知给true， 本地内存失效重新写入的则不需要给false</param>
     */
    public static <T> void putCache(
            String cacheKey, T
            obj,
            long seconds, boolean
                    isNotifyAll) {

        CaffeineCacheTool caffeineCacheTool = CaffeineCacheTool.getInstance();
        caffeineCacheTool.putCache(cacheKey, obj);
        // 当数据修改后，需要通过redis来通知到各个负载机器更新内存缓存数据
        if (isNotifyAll) {
            GlobleAssistMemoryHelper.SetVersion(GlobleLockKey(cacheKey), GlobleAssistRateTypeEnum.Daily);
        }

        GlobleAssistMemoryHelper.SetLocalReceivedTag(GlobleLockKey(cacheKey), GlobleAssistRateTypeEnum.Daily, ",", 1);

    }


    public static <T> T getCache(String cacheKey) {
        try {
            // set 不进去表示处理过，缓存是最新的，直接取用
            if (!GlobleAssistMemoryHelper.SetLocalReceivedTag(GlobleLockKey(cacheKey), GlobleAssistRateTypeEnum.Daily, ",", 1)) {
                // var result = MemoryCacheCoreHelper.GetCache<T>(cacheKey);
                return null;
            }

            return null;
        } catch (Exception ex) {
            return null;
        }
    }

    /// <summary>
    /// 删除数据缓存
    /// </summary>
    /// <param name="cacheKey">键</param>
    public static void DelCache(String cacheKey) {

    }
}

