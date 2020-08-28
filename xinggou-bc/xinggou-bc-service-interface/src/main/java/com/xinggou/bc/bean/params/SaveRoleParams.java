package com.xinggou.bc.bean.params;

import lombok.Data;

import java.io.Serializable;

/**
 * @author luoyb
 * Created on 2020/8/18
 */
@Data
public class SaveRoleParams implements Serializable {

    private static final long serialVersionUID = 1L;

    private Long roleId;
    private String name;
    private String remarks;

}
