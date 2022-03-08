package com.starry.codeview.model;

import lombok.Data;
import lombok.Getter;

import java.io.Serializable;

/**
 * @Data：作用于类上，是以下注解的集合：@ToString @EqualsAndHashCode @Getter @Setter @RequiredArgsConstructor
 * @NoArgsConstructor：生成无参构造器；
 *
 * @RequiredArgsConstructor：生成包含final和@NonNull注解的成员变量的构造器；
 *
 * @AllArgsConstructor：生成全参构造器
 * 升级JDK对功能有影响
 * 有人把JDK从Java 8升级到Java 11时，我发现Lombok不能正常工作了。
 * 有一些坑
 * 使用@Data时会默认使用@EqualsAndHashCode(callSuper=false)，这时候生成的equals()方法只会比较子类的属性，不会考虑从父类继承的属性，无论父类属性访问权限是否开放。
 * 使用@Builder时要加上@AllArgsConstructor，否则可能会报错。

 */
@Data
public class User implements Serializable {
    private String userId;
    private String userName;
}
