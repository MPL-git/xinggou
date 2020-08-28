package com.xinggou.rc.service;

import com.xinggou.rc.entity.KeyValue;
import com.xinggou.rc.entity.Oss;

import java.util.List;

public interface RcModifyService {

    KeyValue saveKeyValue(String typeCode, String key, String value);

    KeyValue saveKeyValue(String typeCode, String key, String value, long reservedLong);

    KeyValue updateKeyValue(KeyValue keyValue);

    void deleteKeyValue(long id);

    Oss createOss(String url);

    void deleteOss(List<Long> idList);
}