package com.xinggou.common.core;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.baomidou.mybatisplus.extension.conditions.query.QueryChainWrapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.xinggou.common.utils.QueryObject;
import com.xinggou.common.vo.PageWrap;

import java.math.BigDecimal;
import java.util.List;


public abstract class BaseServiceImpl<M extends BaseMapper<T>, T> extends ServiceImpl<M, T> implements BaseService<T> {

    protected abstract QueryChainWrapper<T> builderQuery(QueryObject queryObject);

    protected T create(T model) {
        save(model);
        return model;
    }

    protected T update(T model) {
        updateById(model);
        return model;
    }

    @Override
    public int count(QueryObject queryObject) {
        return builderQuery(queryObject.instantOnlyQueryParam()).count();
    }

    @Override
    public BigDecimal sum(String field, QueryObject queryObject) {
        QueryChainWrapper<T> query = builderQuery(queryObject.instantOnlyQueryParam());
        query.select("IFNULL(sum(" + field + "), 0)");
        return (BigDecimal) baseMapper.selectObjs(query.getWrapper()).get(0);
    }

    @Override
    public long max(String field, QueryObject queryObject) {
        QueryChainWrapper<T> query = builderQuery(queryObject.instantOnlyQueryParam());
        query.select("IFNULL(max(" + field + "), 0)");
        return (Long) baseMapper.selectObjs(query.getWrapper()).get(0);
    }

    @Override
    public long min(String field, QueryObject queryObject) {
        QueryChainWrapper<T> query = builderQuery(queryObject.instantOnlyQueryParam());
        query.select("IFNULL(min(" + field + "), 0)");
        return (Long) baseMapper.selectObjs(query.getWrapper()).get(0);
    }

    @Override
    public List<Long> listId(QueryObject queryObject) {
        QueryChainWrapper<T> query = builderQuery(queryObject);
        query.select("id");
        return baseMapper.selectObjs(query.getWrapper());
    }

    @Override
    public T find(QueryObject queryObject) {
        return builderQuery(queryObject).one();
    }

    @Override
    public List<T> list(QueryObject queryObject) {
        return builderQuery(queryObject).list();
    }

    @Override
    public PageWrap<T> paginate(QueryObject queryObject) {
        if (queryObject.getPageNumber() == 0) {
            queryObject.setPageNumber(1);
        }
        if (queryObject.getPageSize() == 0) {
            queryObject.setPageSize(20);
        }

        int totalCount = count(queryObject);
        List<T> list = builderQuery(queryObject).list();
        return PageWrap.of(list, totalCount, queryObject.getPageSize(), queryObject.getPageNumber());
    }


}
