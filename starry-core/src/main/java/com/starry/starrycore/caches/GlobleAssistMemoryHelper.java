package com.starry.starrycore.caches;

import com.sun.org.apache.xpath.internal.operations.Bool;

/**
 * author : eric.ding
 * 【非线程安全】内存全局协助同步类
 * 背景：
 * 解决负载均衡中的服务器内存缓存同步更新问题
 * 思想：
 * 数据每发生改变时，就通过reids设置版本号，
 * 每个服务器尝试写入ip到这个版本号的set集合中，
 * 写入成功表示没有被同步过，执行同步动作更新本地的内存缓存。
 * 否则跳过直接取内存缓存用
 * 缺点：
 * 变化频率高的时候， redis存的数量也多，不过可以优化，版本号自增后就删掉旧版本的缓存
 */
public class GlobleAssistMemoryHelper {

    public static void SetVersion(String configName, GlobleAssistRateTypeEnum rateType) {
        // redisTemplate.IncrBy(configName);
        if (rateType == GlobleAssistRateTypeEnum.Daily) {
            // 每天类的版本控制 以0点为起点
            // CSRedisHelper.KeyExpire(configName, DateTime.Now.TodayLeftSeconds());
        }
    }


    private static long GetVersion(String configName) {
        // redis.GetStr < long>(configName);
        return 1;
    }

    public static Boolean SetLocalReceivedTag(String configName, GlobleAssistRateTypeEnum rateType, String ipAddress, long version) {
        if (version <= 0) {
            version = GetVersion(configName);
        }

        if (ipAddress == null) {
            // ipAddress = IPAddressHelper.GetIpAddress();
        }

        Integer expireTime = -1;
        if (rateType == GlobleAssistRateTypeEnum.Daily) {
            // expireTime = DateTime.Now.TodayLeftSeconds();
        }

        // var execCount = CSRedisHelper.SAdd($"{configName}:{version}", ipAddress, expireTime);
        return false;
    }

}
