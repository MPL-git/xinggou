package com.xinggou.rc.service;

import com.xinggou.common.constant.StateConst;
import com.xinggou.rc.biz.KeyValueBiz;
import com.xinggou.rc.biz.OssBiz;
import com.xinggou.rc.entity.KeyValue;
import com.xinggou.rc.entity.Oss;
import org.apache.dubbo.config.annotation.Service;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.Date;
import java.util.List;

@Service
public class RcModifyServiceImpl implements RcModifyService {
    private static final Logger logger = LoggerFactory.getLogger(RcModifyServiceImpl.class);

    @Autowired
    private KeyValueBiz keyValueBiz;
    @Autowired
    private OssBiz ossBiz;

    @Override
    public KeyValue saveKeyValue(String typeCode, String key, String value){
        return saveKeyValue(typeCode, key, value, 0);
    }

    @Override
    public KeyValue saveKeyValue(String typeCode, String key, String value, long reservedLong) {
        KeyValue model = new KeyValue();
        model.setTypeCode(typeCode);
        model.setParamKey(key);
        model.setParamValue(value);
        model.setReservedLong(reservedLong);
        keyValueBiz.save(model);
        return model;
    }

    @Override
    public KeyValue updateKeyValue(KeyValue keyValue) {
        keyValue.setUpdateDate(new Date());
        keyValueBiz.updateById(keyValue);
        return keyValue;
    }

    @Override
    public void deleteKeyValue(long id) {
        keyValueBiz.removeById(id);
    }

    @Override
    public Oss createOss(String url) {
        Oss oss = new Oss();
        oss.setUrl(url);
        oss.setCreateDate(new Date());
        oss.setDelFlag(StateConst.FALSE);
        ossBiz.save(oss);
        return oss;
    }

    @Override
    public void deleteOss(List<Long> idList) {
        ossBiz.removeByIds(idList);
    }
}
