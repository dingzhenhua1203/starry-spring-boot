package com.starry.codeview.algorithms.nodeStruct;

import lombok.Data;

@Data
public class Node {
    private Integer value;
    private Node next;

    public Node(Integer value) {
        this.value = value;
    }
}
