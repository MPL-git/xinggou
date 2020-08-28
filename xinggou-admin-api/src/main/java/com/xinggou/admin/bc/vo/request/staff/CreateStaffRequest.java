package com.xinggou.admin.bc.vo.request.staff;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.xinggou.common.enums.GenderEnum;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import java.util.Date;

/**
 * @author luoyb
 * Created on 2020/8/15
 */
@Data
public class CreateStaffRequest {

    @ApiModelProperty(value = "用户名", required = true)
    @NotBlank(message = "用户名不能为空")
    private String userName;
    @ApiModelProperty("昵称")
    private String nickName;
    @ApiModelProperty("真实姓名")
    private String realName;
    @ApiModelProperty("手机号")
    private String mobile;
    @ApiModelProperty("头像")
    private String avatar;
    @ApiModelProperty("邮箱")
    private String email;
    @ApiModelProperty("生日，格式：yyyy-MM-dd")
    @JsonFormat(locale = "zh", timezone = "GMT+8", pattern = "yyyy-MM-dd")
    private Date birthday;
    @ApiModelProperty("性别：10男 20女 30未知")
    private GenderEnum gender;
    @ApiModelProperty("职位")
    private String position;

    @ApiModelProperty(value = "部门ID", required = true, example = "1")
    @NotNull(message = "部门ID不能为空")
    private Long deptId;
}
