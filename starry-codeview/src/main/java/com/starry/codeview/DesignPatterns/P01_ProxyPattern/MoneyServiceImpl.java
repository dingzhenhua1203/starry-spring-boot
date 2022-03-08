package com.starry.codeview.DesignPatterns.P01_ProxyPattern;

public class MoneyServiceImpl  implements  MoneyService{

    @Override
    public void sendMoney() {
        System.out.println("发红包了！");
    }
}
