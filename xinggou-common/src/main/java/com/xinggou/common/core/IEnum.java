package com.xinggou.common.core;

import com.fasterxml.jackson.annotation.JsonValue;

/**
 * @author luoyb
 * Created on 2020/8/18
 * 用于指定枚举默认返回值为自定义值 {@link String}
 */
public interface IEnum {

    @JsonValue
    String getValStr();

}
