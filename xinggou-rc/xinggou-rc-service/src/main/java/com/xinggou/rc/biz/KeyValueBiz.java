package com.xinggou.rc.biz;

import com.baomidou.mybatisplus.extension.conditions.query.QueryChainWrapper;
import com.xinggou.common.core.BaseBiz;
import com.xinggou.common.utils.QueryObject;
import com.xinggou.rc.dao.KeyValueDao;
import com.xinggou.rc.entity.KeyValue;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class KeyValueBiz extends BaseBiz<KeyValueDao, KeyValue> {

    @Override
    protected QueryChainWrapper<KeyValue> builderQuery(QueryObject queryObject) {
        QueryChainWrapper<KeyValue> wrapper = queryObject.fillWrapper(query());
        if(queryObject.getQuery("id") != null){
            wrapper.eq("id", queryObject.getQuery("id"));
        }
        return wrapper;
    }

    public KeyValue findByTypeCodeAndKey(String typeCode, String key) {
        return lambdaQuery().eq(KeyValue::getTypeCode, typeCode).eq(KeyValue::getParamKey, key).one();
    }

    public List<KeyValue> listByTypeCodeAndKey(String typeCode, String key) {
        return lambdaQuery().eq(KeyValue::getTypeCode, typeCode).eq(KeyValue::getParamKey, key).list();
    }

    public List<KeyValue> listByTypeCode(String typeCode) {
        return lambdaQuery().eq(KeyValue::getTypeCode, typeCode).list();
    }

}
