package com.starry.codeview.whatTransmit;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.ToString;

@Data
@AllArgsConstructor
@NoArgsConstructor
@ToString
public class Animal {
    private Integer no;
    private String name;
    private Food food;
}
