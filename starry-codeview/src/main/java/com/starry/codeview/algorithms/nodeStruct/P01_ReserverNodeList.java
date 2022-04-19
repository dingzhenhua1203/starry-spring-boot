package com.starry.codeview.algorithms.nodeStruct;

/**
 * 反转单链表 12345 反转为54321
 */
public class P01_ReserverNodeList {
    public static void main(String[] args) {
        Node head = new Node(1);
        head.setNext(new Node(2));
        head.getNext().setNext(new Node(3));
        head.getNext().getNext().setNext(new Node(4));
        head.getNext().getNext().getNext().setNext(new Node(5));
        PrintNodeUtil.printSingleNode(head);
        head = reserveNodeList(head);
        PrintNodeUtil.printSingleNode(head);
    }

    public static Node reserveNodeList(Node n) {
        Node newN = null;
        while (n != null) {
            Node curr = n;
            Node next = n.getNext();
            if (newN == null) {
                curr.setNext(null);
                newN = curr;
            } else {
                curr.setNext(newN);
                newN = curr;
            }
            n = next;
        }
        return newN;
    }
}
