package com.xinggou.bc.enums;

/**
 * @author luoyb
 * Created on 2020/8/19
 * 系统配置下特殊配置类型
 */
public enum ConfigTypeEnum {

    OSS("cloud_storage_config_key", "云存储配置"),

    //
    ;

    private final String key;
    private final String name;

    ConfigTypeEnum(String key, String name) {
        this.key = key;
        this.name = name;
    }

    public String getKey() {
        return key;
    }

    public String getName() {
        return name;
    }
}
