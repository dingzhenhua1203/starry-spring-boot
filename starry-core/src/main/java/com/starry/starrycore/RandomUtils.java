package com.starry.starrycore;

public class RandomUtils {

    /**
     * 随机【0，max】闭区间的整数
     *
     * @param max
     * @return
     */
    public static Integer randomInt(int max) {
        max = max == Integer.MAX_VALUE ? Integer.MAX_VALUE - 1 : max;
        return (int) (Math.random() * (double) (max + 1));
    }
}
