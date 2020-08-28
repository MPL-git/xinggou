package com.xinggou.common.vo;

import com.baomidou.mybatisplus.core.metadata.IPage;
import io.swagger.annotations.ApiModelProperty;

import java.io.Serializable;
import java.util.Collections;
import java.util.List;

/**
 * @author luoyb
 * Created on 2020/1/19
 */
public class PageWrap<T> implements Serializable {

    private static final long serialVersionUID = 1L;

    @ApiModelProperty("总记录数")
    private int totalCount;
    @ApiModelProperty("每页记录数")
    private int pageSize;
    @ApiModelProperty("总页数")
    private int totalPage;
    @ApiModelProperty("当前页")
    private int currPage;
    @ApiModelProperty("列表数据")
    private List<T> list;

    public static <T> PageWrap<T> empty(PageParams params) {
        PageWrap<T> pageWrap = new PageWrap<>();
        pageWrap.setList(Collections.emptyList());
        pageWrap.setCurrPage(params.getCurrentPage());
        pageWrap.setPageSize(params.getPageSize());
        return pageWrap;
    }

    public static <T> PageWrap<T> of(List<T> list, int totalCount, int pageSize, int currPage) {
        PageWrap<T> pageWrap = new PageWrap<>();
        pageWrap.setList(list);
        pageWrap.setTotalCount(totalCount);
        pageWrap.setPageSize(pageSize);
        pageWrap.setCurrPage(currPage);
        pageWrap.setTotalPage((int) Math.ceil((double) totalCount / pageSize));
        return pageWrap;
    }

    public static <T> PageWrap<T> of(List<T> list, PageWrap another) {
        PageWrap<T> pageWrap = new PageWrap<>();
        pageWrap.setList(list);
        pageWrap.setTotalCount(another.getTotalCount());
        pageWrap.setPageSize(another.getPageSize());
        pageWrap.setCurrPage(another.getCurrPage());
        pageWrap.setTotalPage(another.getTotalPage());
        return pageWrap;
    }

    public static <T> PageWrap<T> of(IPage<T> page) {
        PageWrap<T> pageWrap = new PageWrap<>();
        pageWrap.setList(page.getRecords());
        pageWrap.setTotalCount((int) page.getTotal());
        pageWrap.setPageSize((int) page.getSize());
        pageWrap.setCurrPage((int) page.getCurrent());
        pageWrap.setTotalPage((int) page.getPages());
        return pageWrap;
    }

    public static <T> PageWrap<T> of(List<T> list, IPage page) {
        PageWrap<T> pageWrap = new PageWrap<>();
        pageWrap.setList(list);
        pageWrap.setTotalCount((int) page.getTotal());
        pageWrap.setPageSize((int) page.getSize());
        pageWrap.setCurrPage((int) page.getCurrent());
        pageWrap.setTotalPage((int) page.getPages());
        return pageWrap;
    }

    public int getTotalCount() {
        return totalCount;
    }

    public void setTotalCount(int totalCount) {
        this.totalCount = totalCount;
    }

    public int getPageSize() {
        return pageSize;
    }

    public void setPageSize(int pageSize) {
        this.pageSize = pageSize;
    }

    public int getTotalPage() {
        return totalPage;
    }

    public void setTotalPage(int totalPage) {
        this.totalPage = totalPage;
    }

    public int getCurrPage() {
        return currPage;
    }

    public void setCurrPage(int currPage) {
        this.currPage = currPage;
    }

    public List<T> getList() {
        return list;
    }

    public void setList(List<T> list) {
        this.list = list;
    }
}
