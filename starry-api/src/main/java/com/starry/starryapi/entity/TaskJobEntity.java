package com.starry.starryapi.entity;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Date;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class TaskJobEntity {
    private long TJID;
    private String TJTitleKey;
    private String TJTitle;
    private String TJHttpUrl;
    private String TJHttpVerb;
    private String TJCron;
    private String TJRemark;
    private int TJServiceSystem;
    private int TJRowStatus;
    private String TJCreateUser;
    private Date TJCreateDate;
    private Date TJUpdateDate;

}
