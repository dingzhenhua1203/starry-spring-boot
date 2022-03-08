package com.starry.starrycore;

public class MachineWatchHelper {

    /**
     * 获取cpu核数 常用于设置线程池最大值
     */
    public static int getMaxCPUCores() {
        return Runtime.getRuntime().availableProcessors();
    }
}
