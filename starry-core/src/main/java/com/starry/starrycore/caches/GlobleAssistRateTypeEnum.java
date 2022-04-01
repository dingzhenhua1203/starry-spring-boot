package com.starry.starrycore.caches;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

@AllArgsConstructor
@Getter
public enum GlobleAssistRateTypeEnum {
    Forevey(1, "Forevey"), Daily(2, "Daily");

    private Integer value;
    private String name;

}
