package com.xinggou.bc.bean;

import com.xinggou.common.enums.GenderEnum;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.io.Serializable;

/**
 * 当前登录用户信息
 *
 * @author ：huangdl
 */
@Data
public class CurrentStaff implements Serializable {

    private static final long serialVersionUID = 1L;

    @ApiModelProperty("用户ID")
    private long id;

    @ApiModelProperty("账号")
    private String userName;

    @ApiModelProperty("手机号")
    private String mobile;

    @ApiModelProperty("昵称")
    private String nickName;

    @ApiModelProperty("头像")
    private String avatar;

    @ApiModelProperty("性别：10男 20女 30未知")
    private GenderEnum gender;

    @ApiModelProperty("令牌")
    private String token;

    @ApiModelProperty("刷新令牌")
    private String refreshToken;

}
