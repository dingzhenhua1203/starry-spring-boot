package com.starry.starryapi.service.spiMode.godMode;

public class GodServiceImpl extends GodService {
    @Override
    public void execSql(String sql) {
        System.out.println("子类GodServiceImpl" + sql);
    }
}
