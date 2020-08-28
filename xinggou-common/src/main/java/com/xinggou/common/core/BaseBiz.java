package com.xinggou.common.core;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.baomidou.mybatisplus.extension.conditions.query.QueryChainWrapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.xinggou.common.utils.QueryObject;
import com.xinggou.common.vo.PageWrap;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.math.BigDecimal;
import java.util.List;


public abstract class BaseBiz<M extends BaseMapper<T>, T> extends ServiceImpl<M, T> {

    private Logger logger = LoggerFactory.getLogger(getClass());

    protected abstract QueryChainWrapper<T> builderQuery(QueryObject queryObject);

    /*protected T create(T model) {
        save(model);
        return model;
    }

    protected T update(T model) {
        Class clazz = model.getClass();
        try {
            Method method = clazz.getMethod("setUpdateTime", Date.class);
            method.invoke(model, new Date());
        } catch (NoSuchMethodException e) {
            logger.info("表{}无updateTime字段,请检查!", clazz.getName());
        } catch (Exception e) {
            logger.info("表{}设置updateTime字段出错!", clazz.getName());
        }

        updateById(model);
        return model;
    }*/

    public int count(QueryObject queryObject) {
        return builderQuery(queryObject.instantOnlyQueryParam()).count();
    }

    public BigDecimal sum(String field, QueryObject queryObject) {
        QueryChainWrapper<T> query = builderQuery(queryObject.instantOnlyQueryParam());
        query.select("IFNULL(sum(" + field + "), 0)");
        return (BigDecimal) baseMapper.selectObjs(query.getWrapper()).get(0);
    }

    public long max(String field, QueryObject queryObject) {
        QueryChainWrapper<T> query = builderQuery(queryObject.instantOnlyQueryParam());
        query.select("IFNULL(max(" + field + "), 0)");
        return (Long) baseMapper.selectObjs(query.getWrapper()).get(0);
    }

    public long min(String field, QueryObject queryObject) {
        QueryChainWrapper<T> query = builderQuery(queryObject.instantOnlyQueryParam());
        query.select("IFNULL(min(" + field + "), 0)");
        return (Long) baseMapper.selectObjs(query.getWrapper()).get(0);
    }

    public List<Long> listId(QueryObject queryObject) {
        QueryChainWrapper<T> query = builderQuery(queryObject);
        query.select("id");
        return baseMapper.selectObjs(query.getWrapper());
    }

    public T find(QueryObject queryObject) {
        return builderQuery(queryObject).one();
    }

    public List<T> list(QueryObject queryObject) {
        return builderQuery(queryObject).list();
    }

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
