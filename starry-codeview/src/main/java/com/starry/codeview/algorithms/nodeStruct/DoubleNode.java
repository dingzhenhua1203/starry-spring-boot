package com.starry.codeview.algorithms.nodeStruct;

import lombok.Data;

@Data
public class DoubleNode {
    private Integer value;
    private Node pre;
    private Node next;

    public DoubleNode(Integer value) {
        this.value = value;
    }
}
