package com.starry.starrycore.caches;

import com.starry.starrycore.caches.CaffeineUtil.CaffeineCacheTool;

public class CacheUnitTest {
    public static void main(String[] args) {
        UserCacheModel model = new UserCacheModel("1001", "luffy", 20);

        CaffeineCacheTool caffeineCacheTool = CaffeineCacheTool.getInstance();
        caffeineCacheTool.putCache("111", "nihao x");
        caffeineCacheTool.putCache("us", model);


        String cache = caffeineCacheTool.getCache("111", String.class);
        UserCacheModel us = caffeineCacheTool.getCache("us", UserCacheModel.class);
        System.out.println(cache);
        System.out.println(us.toString());
        caffeineCacheTool.delCache("111");
        String cache1 = caffeineCacheTool.getCache("111", String.class);
        System.out.println(cache1);
    }
}
