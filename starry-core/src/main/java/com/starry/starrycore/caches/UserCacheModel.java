package com.starry.starrycore.caches;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class UserCacheModel implements Serializable {
    private String userID;
    private String userName;
    private Integer age;
}
