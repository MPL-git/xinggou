package com.xinggou.admin.bc.vo.response;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

/**
 * @author luoyb
 * Created on 2020/8/18
 */
@Data
public class RoleSimpleVo {

    @ApiModelProperty("角色ID")
    private Long id;
    @ApiModelProperty("角色名称")
    private String name;
    @ApiModelProperty("备注")
    private String remarks;
}
