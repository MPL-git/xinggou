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
public class CreateDeptRequest {

    @ApiModelProperty(value = "父级部门ID", required = true)
    @NotNull(message = "父级部门ID不能为空")
    private Long parentId;
    @NotBlank(message = "部门名称不能为空")
    @ApiModelProperty(value = "部门名称", required = true)
    private String name;
    @ApiModelProperty("备注")
    private String remarks;

}
