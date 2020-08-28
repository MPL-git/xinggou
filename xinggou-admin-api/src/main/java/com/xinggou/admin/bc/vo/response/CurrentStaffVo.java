package com.xinggou.admin.bc.vo.response;

import com.xinggou.common.enums.GenderEnum;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

/**
 * @author luoyb
 * Created on 2020/8/17
 */
@Data
public class CurrentStaffVo {

    @ApiModelProperty("用户ID")
    private long id;

    @ApiModelProperty("手机号")
    private String mobile;

    @ApiModelProperty("昵称")
    private String nickName;

    @ApiModelProperty("头像")
    private String avatar;

    @ApiModelProperty("性别：10男 20女 30未知")
    private GenderEnum gender;

    @ApiModelProperty("性别")
    private String genderDesc;

}
