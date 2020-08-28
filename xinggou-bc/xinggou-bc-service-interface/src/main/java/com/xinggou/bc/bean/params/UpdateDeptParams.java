package com.xinggou.bc.bean.params;

import lombok.Data;

import java.io.Serializable;

/**
 * @author luoyb
 * Created on 2020/8/17
 */
@Data
public class UpdateDeptParams implements Serializable {

    private static final long serialVersionUID = 1L;

    private Long deptId;
    private String name;
    private String remarks;

}
