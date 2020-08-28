package com.xinggou.common.enums;

import com.baomidou.mybatisplus.annotation.EnumValue;
import com.xinggou.common.core.IEnum;

/**
 * 性别
 */
public enum GenderEnum implements IEnum {

    MALE(10, "男", "先生"),
    FEMALE(20, "女", "女士"),
    UNKNOWN(30, "未知", ""),
    //
    ;
    @EnumValue
    private final int code;
    private final String displayName;
    private final String call;//称呼

    GenderEnum(int code, String displayName, String call) {
        this.code = code;
        this.displayName = displayName;
        this.call = call;
    }

    public static GenderEnum getByCode(Integer code) {
        if (code != null) {
            for (GenderEnum value : GenderEnum.values()) {
                if (value.code == code) {
                    return value;
                }
            }
        }
        return UNKNOWN;
    }

    public static boolean validate(int code) {
        for (GenderEnum value : GenderEnum.values()) {
            if (value.code == code) {
                return true;
            }
        }
        return false;
    }

    public int getCode() {
        return code;
    }

    public String getDisplayName() {
        return displayName;
    }

    public String getCall() {
        return call;
    }

    @Override
    public String getValStr() {
        return String.valueOf(code);
    }
}
