package com.starry.starrycore.redis;

import com.starry.starrycore.JsonUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.DefaultTypedTuple;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.lang.Nullable;
import org.springframework.stereotype.Component;
import org.springframework.util.CollectionUtils;

import java.util.*;
import java.util.concurrent.TimeUnit;
import java.util.function.Supplier;

@Component
public class SpringDataRedisHelper {
    @Autowired
    private RedisTemplate redisTemplate;


    //- - - - - - - - - - - - - - - - - - - - -  公共方法 - - - - - - - - - - - - - - - - - - - -

    /**
     * 是否存在
     *
     * @param key
     * @return
     */
    public Boolean hasKey(String key) {
        Boolean result = catchInvoke(() -> redisTemplate.hasKey(key));
        return result == null ? false : result;
    }

    /**
     * 根据key 获取过期时间
     *
     * @param key
     * @return
     */
    public Boolean delKey(String key) {
        Boolean result = catchInvoke(() -> redisTemplate.delete(key));
        return result == null ? false : result;
    }

    /**
     * 根据key 获取过期时间
     *
     * @param keys
     * @return
     */
    public Boolean delKeys(String... keys) {
        Boolean result = catchInvoke(() -> {
            if (keys != null && keys.length > 0) {
                if (keys.length == 1) {
                    return redisTemplate.delete(keys[0]);
                } else {
                    return redisTemplate.delete(CollectionUtils.arrayToList(keys)) > 0;
                }
            }
            return true;
        });
        return result == null ? false : result;
    }

    /**
     * 设置过期时间
     *
     * @param key
     * @param expireSecond
     * @return
     */
    public Boolean expire(String key, long expireSecond) {

        Boolean result = catchInvoke(() -> redisTemplate.expire(key, expireSecond, TimeUnit.SECONDS));
        return result == null ? false : result;
    }

    /**
     * 根据key 获取过期时间
     *
     * @param key
     * @return -1 表示没有获取到
     */
    public Long ttl(String key) {

        Long result = catchInvoke(() -> redisTemplate.getExpire(key, TimeUnit.SECONDS));
        return result == null ? -1 : result;
    }


    //- - - - - - - - - - - - - - - - - - - - -  String类型 - - - - - - - - - - - - - - - - - - - -

    /**
     * 根据key获取值
     *
     * @param key 键
     * @return 值
     */
    @Nullable
    public Object sgGet(String key) {
        Object result = catchInvoke(() -> {
            return key == null ? null : redisTemplate.opsForValue().get(key);
        });
        return result;
    }

    /**
     * 将值放入缓存并设置时间
     *
     * @param key          键
     * @param value        值
     * @param expireSecond 时间(秒) -1为无期限
     * @return true成功 false 失败
     */
    public void sgSet(String key, String value, long expireSecond) {
        if (expireSecond > 0) {
            redisTemplate.opsForValue().set(key, JsonUtil.parseString(value), expireSecond, TimeUnit.SECONDS);
        } else {
            redisTemplate.opsForValue().set(key, value);
        }
    }

    /**
     * 对一个 key-value 的值进行加减操作,
     * 如果该 key 不存在 将创建一个key 并赋值该 number
     * 如果 key 存在,但 value 不是长整型 ,将报错
     *
     * @param key
     * @param delta
     */
    public Long incrBy(String key, long delta) {
        return redisTemplate.opsForValue().increment(key, delta);
    }


    public Integer sgSetNx(String key, String value, int expireSecond) {
        Boolean result = catchInvoke(() -> {
            return redisTemplate.opsForValue().setIfAbsent(key, value, expireSecond, TimeUnit.SECONDS);
        });
        return result == null ? -1 : result.booleanValue() ? 1 : 0;
    }

    //- - - - - - - - - - - - - - - - - - - - -  set类型 - - - - - - - - - - - - - - - - - - - -

    /**
     * 将数据放入set缓存
     *
     * @param key 键
     * @return
     */
    public void sAdd(String key, String value) {
        redisTemplate.opsForSet().add(key, value);
    }

    /**
     * 获取变量中的值
     *
     * @param key 键
     * @return
     */
    public Set<Object> sGet(String key) {
        return redisTemplate.opsForSet().members(key);
    }

    /**
     * 随机获取变量中指定个数的元素
     *
     * @param key   键
     * @param count 值
     * @return
     *//*
    public void sRandomMembers(String key, long count) {
        redisTemplate.opsForSet().randomMembers(key, count);
    }

    *//**
     * 随机获取变量中的元素
     *
     * @param key 键
     * @return
     *//*
    public Object randomMember(String key) {
        return redisTemplate.opsForSet().randomMember(key);
    }*/

    /**
     * 弹出变量中的元素
     *
     * @param key 键
     * @return
     */
    public Object sPOP(String key) {
        return redisTemplate.opsForSet().pop("setValue");
    }

    /**
     * 根据value从一个set中查询,是否存在
     *
     * @param key   键
     * @param value 值
     * @return true 存在 false不存在
     */
    public boolean sHasKey(String key, Object value) {
        return redisTemplate.opsForSet().isMember(key, value);
    }

    /**
     * 批量移除set缓存中元素
     *
     * @param key    键
     * @param values 值
     * @return
     */
    public void sRemove(String key, Object... values) {
        redisTemplate.opsForSet().remove(key, values);
    }


    //- - - - - - - - - - - - - - - - - - - - -  hash类型 - - - - - - - - - - - - - - - - - - - -

    /**
     * 加入缓存
     *
     * @param key 键
     * @param map 键
     * @return
     */
    public void hAdd(String key, Map<String, String> map) {
        redisTemplate.opsForHash().putAll(key, map);
    }

    /**
     * 加入缓存
     *
     * @param key   键
     * @param field 键
     * @param value value
     * @return
     */
    public void hAdd(String key, String field, Object value) {
        redisTemplate.opsForHash().put(key, field, value);
    }

    /**
     * 获取指定key的值string
     *
     * @param key   键
     * @param field 键
     * @return
     */
    public <T> T hGet(String key, String field, Class<T> clazz) {
        String result = redisTemplate.opsForHash().get(key, field).toString();
        return JsonUtil.parseObject(result, clazz);
    }

    public Object hGet(String key, String field) {
        Object result = redisTemplate.opsForHash().get(key, field);
        return result;
    }

    /**
     * 获取 key 下的 所有  hashkey 和 value
     *
     * @param key 键
     * @return
     */
    public Map<Object, Object> hGetMaps(String key) {
        return redisTemplate.opsForHash().entries(key);
    }

    /**
     * 验证指定 key 下 有没有指定的 hashkey
     *
     * @param key
     * @param field
     * @return
     */
    public boolean hHasKey(String key, String field) {
        return redisTemplate.opsForHash().hasKey(key, field);
    }

    /**
     * 删除指定 hash 的 HashKey
     *
     * @param key
     * @param hashKeys
     * @return 删除成功的 数量
     */
    public Long delete(String key, String... hashKeys) {
        return redisTemplate.opsForHash().delete(key, hashKeys);
    }

    //- - - - - - - - - - - - - - - - - - - - -  zset类型 - - - - - - - - - - - - - - - - - - - -
    public Long zadd(String key, String field, Double score) {
        // 向集合中插入元素
        redisTemplate.opsForZSet().add(key, field, score);

        // 批量插入
        DefaultTypedTuple<String> tuple1 = new DefaultTypedTuple<String>(field, score);
        DefaultTypedTuple<String> tuple2 = new DefaultTypedTuple<String>(field, score);
        redisTemplate.opsForZSet().add(key, new HashSet<>(Arrays.asList(tuple1, tuple2)));

        // 从集合中删除指定元素
        redisTemplate.opsForZSet().remove(key, field);

        //为指定元素加分
        Double score1 = redisTemplate.opsForZSet().incrementScore("ranking-list", "p1", 2);
        System.out.println(score);//返回加分后的得分

        //返回指定成员的排名（从小到大）
        Long rank = redisTemplate.opsForZSet().rank("ranking-list", "p1");
        //从大到小
        Long reverseRank = redisTemplate.opsForZSet().reverseRank("ranking-list", "p1");
        System.out.println(rank);
        System.out.println(reverseRank);

        // 返回集合内元素的排名，以及分数（从小到大）
        Set<DefaultTypedTuple<String>> tuples = redisTemplate.opsForZSet().rangeWithScores("ranking-list", 0, -1);
        for (DefaultTypedTuple<String> tuple : tuples) {
            System.out.println(tuple.getValue() + " : " + tuple.getScore());
        }

        //返回集合内元素在指定分数范围内的排名（从小到大）
        Set<String> ranking = redisTemplate.opsForZSet().rangeByScore("ranking-list", 0, 5);
        System.out.println(ranking);
        //带偏移量和个数，下例意为从第二个开始，要三个
        Set<String> ranking2 = redisTemplate.opsForZSet().rangeByScore("ranking-list", 0, 5, 1, 3);
        System.out.println(ranking2);
        //也可以带分数返回，类似于test5

        //返回集合内指定分数范围的成员个数
        Long count = redisTemplate.opsForZSet().count("ranking-list", 0, 2);
        System.out.println(count);
        //返回集合内的成员个数
        Long size = redisTemplate.opsForZSet().size("ranking-list");//等同于zCard(key);
        System.out.println(size);

        //获得指定元素的分数
        Double score2 = redisTemplate.opsForZSet().score("ranking-list", "p1");
        System.out.println(score);

        //删除指定索引范围的元素
        printZSet("ranking-list");
        redisTemplate.opsForZSet().removeRange("ranking-list", 0, 0);
        printZSet("ranking-list");

        //删除指定分数范围内的元素
        printZSet("ranking-list");
        redisTemplate.opsForZSet().removeRangeByScore("ranking-list", 4, 5);
        printZSet("ranking-list");
        redisTemplate.opsForZSet();
        return 1L;
    }

    private void printZSet(String key) {
        // 按照排名先后(从小到大)打印指定区间内的元素, -1为打印全部
        Set<String> range = redisTemplate.opsForZSet().range(key, 0, -1);
        // reverseRange 从大到小
        System.out.println(range);
    }

    @Nullable
    private <T> T catchInvoke(Supplier<T> callback) {
        try {
            return callback.get();
        } catch (Exception ex) {
            return null;
        }
    }
}
