package com.xinggou.admin.bc.vo.request.staff;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import javax.validation.constraints.NotBlank;

/**
 * @author luoyb
 * Created on 2020/8/17
 */
@Data
public class UpdateStaffPasswordRequest {

    @ApiModelProperty(value = "密码", required = true)
    @NotBlank(message = "密码不能为空")
    private String password;

}
