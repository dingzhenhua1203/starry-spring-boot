package com.starry.codeview.MutiImplement;

import java.math.BigDecimal;

public class MoneyServiceImpl implements VirtualCoinService, MoneyService {


    /**
     * 覆盖父类default接口，显示指定使用哪个,默认是调用的实现的第一个接口的方法
     *
     * @param money
     * @return
     */
    @Override
    public String sendMoney(BigDecimal money) {
        System.out.println("MoneyServiceImpl : sendMoney " + money);

        return VirtualCoinService.super.sendMoney(money);
        // return MoneyService.super.sendMoney(money);
        //return "impl self SendMoney" + money;
    }

    @Override
    public String receiveMoney(BigDecimal money) {
        System.out.println("MoneyServiceImpl : receiveMoney " + money);
        return null;
    }


    @Override
    public String sendCoin(BigDecimal coin) {
        System.out.println("MoneyServiceImpl : sendCoin " + coin);
        return null;
    }

}
