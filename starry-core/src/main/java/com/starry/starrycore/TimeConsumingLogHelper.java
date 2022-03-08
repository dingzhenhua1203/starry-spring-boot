package com.starry.starrycore;

import org.springframework.util.StopWatch;

/**
 * 耗时日志记录
 */
public class TimeConsumingLogHelper {

    private StopWatch timer = null;

    private StringBuilder logBuilder = new StringBuilder();

    /// <summary>
    /// Initializes a new instance of the <see cref="TimeConsumingLogHelper"/> class.
    /// 耗时日志记录
    /// </summary>
    public TimeConsumingLogHelper() {
        this.timer = new StopWatch();
    }

    /**
     * 启动
     * @return 当前对象
     */
    public TimeConsumingLogHelper Start() {
        if (!this.timer.isRunning()) {
            this.timer.start();
        }

        return this;
    }

    /**
     * 添加日志
     * @param log 日志内容
     * @return 当前对象
     */
    public TimeConsumingLogHelper AddLog(String log) {
        if (!this.timer.isRunning()) {
            this.timer.start();
        }

        this.logBuilder.append("当前毫秒数：");
        this.logBuilder.append(this.timer.getTotalTimeMillis());
        this.logBuilder.append(":");
        this.logBuilder.append(log);
        this.logBuilder.append(System.lineSeparator());
        return this;
    }

    /**
     * 【收尾】收集日志
     * @return 返回期间记录的所有日志
     */
    public String CollectLog() {
        this.logBuilder.append("总耗时：");
        this.logBuilder.append(this.timer.getTotalTimeMillis());
        this.logBuilder.append("毫秒!");
        this.logBuilder.append(System.lineSeparator());
        this.timer.stop();
        return this.logBuilder.toString();
    }
}

