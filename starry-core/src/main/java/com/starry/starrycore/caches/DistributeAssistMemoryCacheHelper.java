package com.starry.starrycore.caches;

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
     * <param name="seconds">过期秒</param>
     * <param name="isNeedNotifyAll">是否需要通知分布式系统其他机器，比如内容变化的则要通知给true， 本地内存失效重新写入的则不需要给false</param>
     * <param name="priority">缓存项的优先级，你可以设置缓存项的到期策略一样，你还可以为缓存项赋予优先级。如果服务器内存紧缺的话，就会基于此优先级对缓存项进行清理以回收内存</param>
     */
    public static <T> void SetCache(
            String cacheKey, T
            objObject,
            long seconds, boolean
                    isNeedNotifyAll) {
    }


    public static <T> T GetCache(String cacheKey) {
        try {
            // set 不进去表示处理过，缓存是最新的，直接取用


        } catch (Exception exception) {


        }
        return null;
    }

    /// <summary>
    /// 删除数据缓存
    /// </summary>
    /// <param name="cacheKey">键</param>
    public static void DelCache(String cacheKey) {

    }
}

