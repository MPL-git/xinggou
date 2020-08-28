package com.xinggou.admin.bc.vo.request;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;

/**
 * @author luoyb
 * Created on 2020/8/17
 */
@Data
public class UpdateDeptRequest {

    @ApiModelProperty(value = "部门ID", required = true)
    @NotNull(message = "部门ID不能为空")
    private Long deptId;
    @ApiModelProperty(value = "部门名称", required = true)
    @NotBlank(message = "部门名称不能为空")
    private String name;
    @ApiModelProperty("备注")
    private String remarks;

}
