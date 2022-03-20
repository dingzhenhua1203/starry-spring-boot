package com.starry.starryapi.studytest.circlereference;


import org.springframework.beans.factory.annotation.Autowired;

public class ThreadAutowiredProblemTest {

    @Autowired
    private C c;

    public static void main(String[] args) {

        new Thread(() -> {

        }).start();
    }


}
