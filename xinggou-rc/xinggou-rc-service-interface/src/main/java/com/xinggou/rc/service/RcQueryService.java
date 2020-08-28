package com.xinggou.rc.service;

import com.xinggou.common.vo.PageWrap;
import com.xinggou.rc.bean.params.PaginateOssFileParams;
import com.xinggou.rc.entity.KeyValue;
import com.xinggou.rc.entity.Oss;

import java.util.List;

public interface RcQueryService {

    KeyValue findKeyValue(String typeCode, String key);

    List<KeyValue> listKeyValue(String typeCode);

    List<KeyValue> listKeyValue(String typeCode, String key);

    PageWrap<Oss> paginateOssFile(PaginateOssFileParams params);

}