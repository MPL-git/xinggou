package com.xinggou.bc.enums;

import com.baomidou.mybatisplus.annotation.EnumValue;
import com.xinggou.common.core.IEnum;

/**
 * 菜单类型
 */
public enum MenuTypeEnum implements IEnum {
    /**
     * 目录
     */
    CATALOG(10),
    /**
     * 菜单
     */
    MENU(20),
    /**
     * 权限
     */
    PERM(30);

    @EnumValue
    private int value;

    MenuTypeEnum(int value) {
        this.value = value;
    }

    public int getValue() {
        return value;
    }

    @Override
    public String getValStr() {
        return String.valueOf(value);
    }
}