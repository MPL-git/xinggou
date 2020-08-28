package com.xinggou.common.enums;

import com.baomidou.mybatisplus.annotation.EnumValue;
import com.xinggou.common.core.IEnum;

/**
 *
 */
public enum DirectionEnum implements IEnum {

    INCOME(10), //入
    EXPEND(20), //出
    //
    ;
    @EnumValue
    private final int code;

    DirectionEnum(int code) {
        this.code = code;
    }

    public int getCode() {
        return code;
    }

    @Override
    public String getValStr() {
        return String.valueOf(code);
    }
}
