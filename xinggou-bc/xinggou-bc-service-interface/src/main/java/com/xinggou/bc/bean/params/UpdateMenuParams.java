package com.xinggou.bc.bean.params;

import lombok.Data;

import java.io.Serializable;

/**
 * @author luoyb
 * Created on 2020/8/18
 */
@Data
public class UpdateMenuParams implements Serializable {

    private static final long serialVersionUID = 1L;

    private Long menuId;
    private String name;
    private String icon;
    private String url;
    private String perms;
    private Integer seqNo;

}
