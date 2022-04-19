package com.starry.codeview.MutiImplement;

import java.math.BigDecimal;

public interface VirtualCoinService {
    default public String sendMoney(BigDecimal money) {
        System.out.println("VirtualCoinService : sendMoney " + money);
        return "VirtualCoinService" + money;
    }

    public String receiveMoney(BigDecimal money);

    public String sendCoin(BigDecimal coin);

}
