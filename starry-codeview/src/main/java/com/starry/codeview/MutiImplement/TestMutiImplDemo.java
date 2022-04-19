package com.starry.codeview.MutiImplement;

import java.math.BigDecimal;

public class TestMutiImplDemo {
    public static void main(String[] args) {
        MoneyServiceImpl moneyService = new MoneyServiceImpl();
        moneyService.sendMoney(new BigDecimal(12.9));
        moneyService.receiveMoney(new BigDecimal(12.1));
        moneyService.sendCoin(new BigDecimal(12.2));
    }
}
