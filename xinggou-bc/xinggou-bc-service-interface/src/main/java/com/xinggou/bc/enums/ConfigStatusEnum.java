package com.xinggou.bc.enums;

import com.baomidou.mybatisplus.annotation.EnumValue;
import com.xinggou.common.core.IEnum;

/**
 * 菜单类型
 */
public enum ConfigStatusEnum implements IEnum {

    SHOW(10),
    HIDE(20);

    @EnumValue
    private int code;

    ConfigStatusEnum(int code) {
        this.code = code;
    }

    public int getValue() {
        return code;
    }

    @Override
    public String getValStr() {
        return String.valueOf(code);
    }
}