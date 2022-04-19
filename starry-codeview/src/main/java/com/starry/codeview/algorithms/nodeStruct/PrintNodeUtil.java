package com.starry.codeview.algorithms.nodeStruct;

public class PrintNodeUtil {

    public static void printSingleNode(Node n) {
        while (n != null) {
            System.out.print(n.getValue());
            n = n.getNext();
        }
        System.out.println("");
        System.out.println("************************");
    }
}
