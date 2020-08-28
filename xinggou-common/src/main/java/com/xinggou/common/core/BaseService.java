package com.xinggou.common.core;

import com.baomidou.mybatisplus.extension.service.IService;
import com.xinggou.common.utils.QueryObject;
import com.xinggou.common.vo.PageWrap;

import java.math.BigDecimal;
import java.util.List;


public interface BaseService<T> extends IService<T> {

    int count(QueryObject queryObject);

    BigDecimal sum(String field, QueryObject queryObject);

    long max(String field, QueryObject queryObject);

    long min(String field, QueryObject queryObject);

    List<Long> listId(QueryObject queryObject);

    T find(QueryObject queryObject);

    List<T> list(QueryObject queryObject);

    PageWrap<T> paginate(QueryObject queryObject);

}
