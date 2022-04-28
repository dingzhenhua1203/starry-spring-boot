package com.starry.codeview.algorithms.nodeStruct;

import java.util.ArrayList;
import java.util.List;
import java.util.PriorityQueue;
import java.util.stream.Collectors;
import java.util.stream.Stream;

public class P05_LinkedNode {

    public ListNode mergeKLists(ArrayList<ListNode> lists) {
        // 大根堆
        PriorityQueue<Integer> maxQ = new PriorityQueue<>((x,r)->{
            if(x>r) return -1;
            else return 1;
        });
        // 小根堆 默认从小到大
        PriorityQueue<Integer> litterQ = new PriorityQueue<>();
        for (ListNode item : lists) {
            while (item != null ) {
                litterQ.offer(item.val);
                item= item.next;
            }
        }

        ListNode result = new ListNode(-1);
        ListNode temp = result;
        while (!litterQ.isEmpty()) {
            temp.next = new ListNode(litterQ.poll());
            temp = temp.next;
        }
        return result.next;
    }

    static class ListNode {
        int val;
        ListNode next = null;

        public ListNode(int v) {
            val = v;
        }
    }

    public static void main(String[] args) {
        P05_LinkedNode p05_linkedNode = new P05_LinkedNode();
        // [{1,2,3},{4,5,6,7}]
        ListNode one = new ListNode(1);
        one.next = new ListNode(2);
        one.next.next = new ListNode(3);

        ListNode tw = new ListNode(1);
        tw.next = new ListNode(4);
        tw.next.next = new ListNode(5);
        ArrayList<ListNode> collect = (ArrayList)Stream.of(one, tw).collect(Collectors.toList());
        p05_linkedNode.mergeKLists(collect);
    }
}
