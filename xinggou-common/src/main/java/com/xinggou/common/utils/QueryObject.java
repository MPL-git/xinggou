package com.xinggou.common.utils;


import com.baomidou.mybatisplus.extension.conditions.query.QueryChainWrapper;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;

import java.io.Serializable;
import java.util.Date;
import java.util.HashMap;
import java.util.Iterator;
import java.util.LinkedHashMap;
import java.util.Map;
import java.util.Set;

@ApiModel("查询对象")
public class QueryObject implements Serializable {

    private static final long serialVersionUID = 1;

    public static final String SORT_ASC = "asc";
    public static final String SORT_DESC = "desc";

    @ApiModelProperty("页码")
    private int pageNumber;    //页码

    @ApiModelProperty("每页几条")
    private int pageSize;    //每页几条

    private Map<String, Object> queryParam = new HashMap<>();
    private LinkedHashMap<String, String> sortParam = new LinkedHashMap<>();


    public QueryObject() {
    }

    public QueryObject(int pageNumber, int pageSize) {
        this.pageNumber = pageNumber;
        this.pageSize = pageSize;
    }

    public QueryObject addQuery(String name, Object value) {
        queryParam.put(name, value);
        return this;
    }

    @SuppressWarnings("unchecked")
    public <T> T getQuery(String name) {
        Object result = queryParam.get(name);
        return (result != null ? (T) result : null);
    }

    public String getQueryToStr(String name) {
        Object result = queryParam.get(name);
        return (result != null ? result.toString() : null);
    }

    public Integer getQueryToInt(String name) {
        Object result = queryParam.get(name);
        return (result != null ? (Integer) result : null);
    }

    public Date getQueryToDate(String name) {
        Object result = queryParam.get(name);
        return (result != null ? (Date) result : null);
    }

    public QueryObject removeQuery(String column) {
        queryParam.remove(column);
        return this;
    }

    public boolean hasQuery() {
        return queryParam.size() > 0;
    }

    public QueryObject addSort(String name, String sortType) {
        sortParam.put(name, sortType);
        return this;
    }

    public QueryObject removeSort(String name) {
        sortParam.remove(name);
        return this;
    }

    public QueryObject cleanSort() {
        sortParam.clear();
        return this;
    }


    public int getPageSize() {
        return pageSize;
    }

    public QueryObject setPageSize(int pageSize) {
        this.pageSize = pageSize;
        return this;
    }

    public int getPageNumber() {
        return pageNumber;
    }

    public QueryObject setPageNumber(int pageNumber) {
        this.pageNumber = pageNumber;
        return this;
    }

    public Integer calLimitStart() {
        return (pageNumber - 1) * pageSize;
    }

    public int getTotalPage(int totalCount) {
        return totalCount % pageSize == 0 ? (totalCount / pageSize) : (totalCount / pageSize) + 1;
    }

    public QueryObject instantOnlyQueryParam() {
        QueryObject queryObject = new QueryObject();
        queryObject.queryParam = queryParam;
        return queryObject;
    }

    public <T> QueryChainWrapper<T> fillWrapper(QueryChainWrapper<T> query) {
        if (sortParam.size() > 0) {
            Set<String> columns = sortParam.keySet();
            Iterator<String> names = columns.iterator();
            while (names.hasNext()) {
                String name = names.next();
                if (sortParam.get(name).toLowerCase().equals(QueryObject.SORT_ASC)) {
                    query.orderByAsc(name);
                } else {
                    query.orderByDesc(name);
                }
            }
        }
        if (getPageSize() > 0) {
            query.last("limit " + calLimitStart() + "," + getPageSize());
        }
        return query;
    }


}
