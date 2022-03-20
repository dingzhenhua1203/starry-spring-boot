package com.starry.starryapi;

import com.starry.starryapi.studytest.circlereference.C;

import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class UnitTestDemo  extends StarryApiApplicationTests{

    @Autowired
    private C c;

    /**
     * @Test 是org.junit.Test，不要导入错误
     */
    @Test
    public void test() {
        c.sayHi();
    }
}
