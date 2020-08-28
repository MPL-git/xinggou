package com.xinggou.common.utils;

/**
 *
 */
public final class TimeMonitor {

    private long begin;

    private TimeMonitor() {
    }

    public static TimeMonitor begin() {
        TimeMonitor monitor = new TimeMonitor();
        monitor.setBegin(System.currentTimeMillis());
        return monitor;
    }

    /**
     * 耗时(ms)
     */
    public long elapsedTime() {
        return System.currentTimeMillis() - begin;
    }

    public long getBegin() {
        return begin;
    }

    public void setBegin(long begin) {
        this.begin = begin;
    }
}
