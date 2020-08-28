package com.xinggou.common.enums;

/**
 * 客户端
 */
public enum ClientEnum {

    ANDROID(10),
    ANDROID_FLAT(11),
    IOS(20),
    IOS_FLAT(21),
    H5(30),
    PC(40),
    WX(50), //微信公众号
    WX_XCX(51) //微信小程序
    //
    ;

    private final int code;

    ClientEnum(int code) {
        this.code = code;
    }

    public int getCode() {
        return code;
    }
}
