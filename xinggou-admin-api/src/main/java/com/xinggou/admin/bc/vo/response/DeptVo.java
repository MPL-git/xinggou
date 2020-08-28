package com.xinggou.admin.bc.vo.response;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

/**
 * @author luoyb
 * Created on 2020/8/17
 */
@Data
public class DeptVo {

    @ApiModelProperty("部门ID")
    private Long id;
    @ApiModelProperty("部门名称")
    private String name;
    @ApiModelProperty("备注")
    private String remarks;

}
