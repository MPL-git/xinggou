package com.xinggou.bc.bean.params;

import lombok.Data;

import java.io.Serializable;

/**
 * @author luoyb
 * Created on 2020/8/17
 */
@Data
public class CreateDeptParams  implements Serializable {

    private static final long serialVersionUID = 1L;

    private Long parentId;
    private String name;
    private String remarks;

}
