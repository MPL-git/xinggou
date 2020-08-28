package com.xinggou.rc.service;

import com.xinggou.common.vo.PageWrap;
import com.xinggou.rc.bean.params.PaginateOssFileParams;
import com.xinggou.rc.biz.KeyValueBiz;
import com.xinggou.rc.biz.OssBiz;
import com.xinggou.rc.entity.KeyValue;
import com.xinggou.rc.entity.Oss;
import org.apache.dubbo.config.annotation.Service;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.List;

@Service
public class RcQueryServiceImpl implements RcQueryService {
    private static final Logger logger = LoggerFactory.getLogger(RcQueryServiceImpl.class);

    @Autowired
    private KeyValueBiz keyValueBiz;
    @Autowired
    private OssBiz ossBiz;

    @Override
    public KeyValue findKeyValue(String typeCode, String key) {
        return keyValueBiz.findByTypeCodeAndKey(typeCode, key);
    }

    @Override
    public List<KeyValue> listKeyValue(String typeCode, String key) {
        return keyValueBiz.listByTypeCodeAndKey(typeCode, key);
    }

    @Override
    public PageWrap<Oss> paginateOssFile(PaginateOssFileParams params) {
        return ossBiz.findOssFile(params);
    }

    @Override
    public List<KeyValue> listKeyValue(String typeCode) {
        return keyValueBiz.listByTypeCode(typeCode);
    }

}
