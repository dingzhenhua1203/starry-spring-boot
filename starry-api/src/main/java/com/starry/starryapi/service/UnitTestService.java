package com.starry.starryapi.service;


import com.starry.starryapi.service.spiMode.dataDictionary.DataDictionaryService;
import com.starry.starryapi.service.spiMode.godMode.GodService;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Service;

import java.util.HashMap;
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


public class ListNode {
    int val;
    ListNode next;

    ListNode() {
    }

    ListNode(int val) {
        this.val = val;
    }

    ListNode(int val, ListNode next) {
        this.val = val;
        this.next = next;
    }
}

class Solution {

    public ListNode addTwoNumbers(ListNode l1, ListNode l2) {
        ListNode resp = new ListNode(0);
        int jumpStep = 0;
        ListNode tempL1 = l1;
        ListNode tempL2 = l2;
        do {
            if (tempL2 != null) {
                int sum = l1.val + tempL2.val + jumpStep;
                jumpStep = 0;
                if (sum >= 10) {
                    resp = new ListNode(sum - 10);
                    jumpStep = 1;
                } else {
                    resp = new ListNode(sum);
                }
                tempL2 = tempL2.next;
            }
            resp
        } while ((tempL1 = tempL1.next) != null);

    }
}

