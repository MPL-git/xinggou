package com.xinggou.common.enums;

/**
 * 系统应用
 */
public enum AppEnum {

    PLATFORM(10), //平台端
    SUPPLIER(20), //供应商端
    PARTNER(30), //推荐机构端
    FINANCE(40), //金融服务机构端
    USER(50), //用户端
    JOB(60), //定时任务

    ;
    private final int code;

    AppEnum(int code) {
        this.code = code;
    }

    public int getCode() {
        return code;
    }
}
