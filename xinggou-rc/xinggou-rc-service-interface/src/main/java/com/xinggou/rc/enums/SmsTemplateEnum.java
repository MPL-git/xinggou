package com.xinggou.rc.enums;

/**
 * 短信模板
 */
public enum SmsTemplateEnum {

    USER_REG(10, "注册验证码", "您的注册验证码为${code}，请在${time}分钟内完成注册。如非本人操作，请忽略。"),
    USER_LOGIN(11, "登录验证码", ""),
    USER_FIND_PWD(12, "找回密码验证码", ""),

    //
    ;

    private final int code;
    private final String name;
    private final String template;

    SmsTemplateEnum(int code, String name, String template) {
        this.code = code;
        this.name = name;
        this.template = template;
    }

    public int getCode() {
        return code;
    }

    public String getName() {
        return name;
    }

    public String getTemplate() {
        return template;
    }
}
