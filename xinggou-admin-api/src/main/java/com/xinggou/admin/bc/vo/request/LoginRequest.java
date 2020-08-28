package com.xinggou.admin.bc.vo.request;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import javax.validation.constraints.NotBlank;

/**
 * @author luoyb
 * Created on 2020/8/14
 */
@Data
public class LoginRequest {

    @ApiModelProperty(value = "账号", required = true)
    @NotBlank(message = "账号不能为空")
    private String userName;

    @ApiModelProperty(value = "密码", required = true)
    @NotBlank(message = "密码不能为空")
    private String password;
}
