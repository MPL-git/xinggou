package com.xinggou.common.enums;

/**
 * 云服务商
 */
public enum CloudServiceEnum{
    /**
     * 七牛云
     */
    QINIU(1),
    /**
     * 阿里云
     */
    ALIYUN(2),
    /**
     * 腾讯云
     */
    QCLOUD(3),

    /**
     * 本地存储
     */
    LOCAL(4),

    /**
     * FTP
     */
    FTP(5);

    private int value;

    CloudServiceEnum(int value) {
        this.value = value;
    }

    public int getValue() {
        return value;
    }
}