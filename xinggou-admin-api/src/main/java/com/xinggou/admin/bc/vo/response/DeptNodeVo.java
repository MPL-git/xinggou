package com.xinggou.admin.bc.vo.response;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.util.ArrayList;
import java.util.List;

/**
 * @author luoyb
 * Created on 2020/8/17
 */
@Data
public class DeptNodeVo {

    @ApiModelProperty("部门等级")
    private int level;
    @ApiModelProperty("部门ID")
    private Long deptId;
    @ApiModelProperty("父级ID")
    private Long parentId;

    @ApiModelProperty("部门名称")
    private String name;

    @ApiModelProperty("子集")
    private final List<DeptNodeVo> childNodes = new ArrayList<>();
}
