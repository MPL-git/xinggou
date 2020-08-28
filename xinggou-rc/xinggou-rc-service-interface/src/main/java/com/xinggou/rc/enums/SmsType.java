package com.xinggou.rc.enums;

/**
 *
 */
public enum SmsType {

    VALIDATE_CODE(10), //短信验证码
    MEMBER_NOTICE(20), //通知客户

    //
    ;

    private final int code;

    SmsType(int code) {
        this.code = code;
    }

    public int getCode() {
        return code;
    }
}
