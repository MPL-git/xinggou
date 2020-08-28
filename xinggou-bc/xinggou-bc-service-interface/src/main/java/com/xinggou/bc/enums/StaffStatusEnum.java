package com.xinggou.bc.enums;

import com.baomidou.mybatisplus.annotation.EnumValue;
import com.xinggou.common.core.IEnum;

/**
 * @author luoyb
 * Created on 2020/8/17
 */
public enum StaffStatusEnum implements IEnum {

    NORMAL(10, "正常"),
    PROHIBIT(20, "禁用"),
    //
    ;

    @EnumValue
    private final int code;
    private final String displayName;

    public static String getDisplayName(Integer code) {
        if (code != null) {
            for (StaffStatusEnum value : values()) {
                if (value.getCode() == code) {
                    return value.getDisplayName();
                }
            }
        }
        return "";
    }

    StaffStatusEnum(int code, String displayName) {
        this.code = code;
        this.displayName = displayName;
    }

    public String getDisplayName() {
        return displayName;
    }

    public int getCode() {
        return code;
    }


    @Override
    public String getValStr() {
        return String.valueOf(code);
    }
}
