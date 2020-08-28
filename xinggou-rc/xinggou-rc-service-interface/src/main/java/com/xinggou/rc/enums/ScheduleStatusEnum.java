package com.xinggou.rc.enums;

/**
 * 定时任务状态
 */
public enum ScheduleStatusEnum {
    /**
     * 正常
     */
    NORMAL(10),
    /**
     * 暂停
     */
    PAUSE(20);

    private int value;

    ScheduleStatusEnum(int value) {
        this.value = value;
    }

    public int getValue() {
        return value;
    }
}