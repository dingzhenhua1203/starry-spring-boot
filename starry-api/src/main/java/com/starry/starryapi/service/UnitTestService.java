package com.starry.starryapi.service;


import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Service;

@Service
public class UnitTestService {
    public void Sync(){
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
    public void Async(){
        try {
            Thread.sleep(3000);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }
}
