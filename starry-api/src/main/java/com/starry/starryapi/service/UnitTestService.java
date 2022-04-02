package com.starry.starryapi.service;


import com.starry.starryapi.service.spiMode.dataDictionary.DataDictionaryService;
import com.starry.starryapi.service.spiMode.godMode.GodService;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Service;

import java.util.HashMap;
import java.util.HashSet;
import java.util.ServiceLoader;

@Service
public class UnitTestService {
    public void Sync() {
        try {
            Thread.sleep(3000);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }

    /**
     * 开启异步
     */
    @Async
    public void Async() {
        try {
            Thread.sleep(3000);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }

    public static void main(String[] args) {
        System.out.println("s");
        final ServiceLoader<GodService> godService = ServiceLoader.load(GodService.class);
        final ServiceLoader<DataDictionaryService> dataDictionaryService = ServiceLoader.load(DataDictionaryService.class);
        godService.forEach(x -> {
            x.execSql("111");
        });

        dataDictionaryService.forEach(x -> {
            x.addDictionary("aaa", "1234");
        });
    }
}



