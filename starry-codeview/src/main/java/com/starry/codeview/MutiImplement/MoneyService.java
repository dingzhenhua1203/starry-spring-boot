package com.starry.codeview.MutiImplement;

import java.math.BigDecimal;

public interface MoneyService {
    default public String sendMoney(BigDecimal money) {
        System.out.println("MoneyService : sendMoney " + money);
        return "MoneyService" + money;
    }

    public String receiveMoney(BigDecimal money);
}
