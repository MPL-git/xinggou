package com.xinggou.admin.bc.vo.request;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import org.hibernate.validator.constraints.Length;

import javax.validation.constraints.NotBlank;

/**
 * @author luoyb
 * Created on 2020/8/18
 */
@Data
public class SaveRoleRequest {

    @ApiModelProperty("角色ID，当更新时需要传该值")
    private Long roleId;

    @ApiModelProperty(value = "角色名称",required = true)
    @NotBlank(message = "角色名称不能为空")
    private String name;

    @ApiModelProperty("备注")
    @Length(max = 250, message = "备注不能超过250个字")
    private String remarks;

}
