package com.xinggou.common.vo;

import lombok.Data;

import java.io.Serializable;

/**
 * @author luoyb
 * Created on 2020/8/15
 */
@Data
public class PageParams implements Serializable {

    private static final long serialVersionUID = 1L;

    private Integer currentPage;
    private Integer pageSize;

    private Integer offset;

}
