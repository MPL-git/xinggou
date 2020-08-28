package com.xinggou.bc.enums;

import com.baomidou.mybatisplus.annotation.EnumValue;
import com.xinggou.common.core.IEnum;

/**
 *
 */
public enum PositionStatusEnum implements IEnum {

    ON(10,"在职"), //在职
    QUIT(20,"离职"), //离职
    //
    ;

    @EnumValue
    private final int code;
    private final String displayName;

    PositionStatusEnum(int code, String displayName) {
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
