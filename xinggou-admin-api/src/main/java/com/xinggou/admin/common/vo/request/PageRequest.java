package com.xinggou.admin.common.vo.request;

import io.swagger.annotations.ApiModelProperty;

import javax.validation.constraints.Min;

/**
 * created on 2020/2/19.
 */
public class PageRequest {

    @ApiModelProperty(value = "页码,默认1",example = "1" )
    @Min(value = 1, message = "页码不能小于1")
    private Integer currentPage = 1;

    @ApiModelProperty(value = "页大小,默认20",example = "20")
    @Min(value = 1, message = "页大小不能小于1")
    private Integer pageSize = 20;

    public int getOffset() {
        return (currentPage - 1) * pageSize;
    }

    public PageRequest() {
    }

    public PageRequest(int currentPage, int pageSize) {
        this.currentPage = currentPage;
        this.pageSize = pageSize;
    }

    public int fetchSize() {
        return pageSize;
    }

    public Integer getCurrentPage() {
        return currentPage;
    }

    public void setCurrentPage(Integer currentPage) {
        this.currentPage = currentPage;
    }

    public Integer getPageSize() {
        return pageSize;
    }

    public void setPageSize(Integer pageSize) {
        this.pageSize = pageSize;
    }
}
