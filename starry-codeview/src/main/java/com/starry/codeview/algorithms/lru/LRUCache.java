package com.starry.codeview.algorithms.lru;

import java.util.HashMap;
import java.util.LinkedList;

public class LRUCache {
    HashMap<Integer, Integer> maps = null;
    LinkedList<Integer> keys = new LinkedList();
    int totals = -1;

    public LRUCache(int capacity) {
        // write code here
        totals = capacity;
        maps = new HashMap<>(capacity);
    }

    public int get(int key) {
        // write code here
        int v = maps.getOrDefault(key, -1);
        if (v != -1) {
            keys.remove((Object)key);
            keys.addLast(key);
        }
        return v;
    }

    public void set(int key, int value) {
        // write code here
        if (maps.containsKey(key) ) {
            maps.put(key, value);
            keys.remove((Object)key);
            keys.addLast(key);
        } else if (maps.size() >= totals) {
            int k = keys.peekFirst();
            maps.remove((Object)k);
            keys.removeFirst();
            maps.put(key, value);
            keys.addLast(key);
        } else {
            maps.put(key, value);
            keys.addLast(key);
        }
    }

    public static void main(String[] args) {
        LRUCache ss = new LRUCache(2);
        ss.set(1, 1);
        ss.set(2, 2);
        ss.get(1);
        ss.set(3, 3);
        ss.get(2);
        ss.set(4, 4);
        ss.get(1);
        ss.get(3);
        ss.get(4);
    }
}
