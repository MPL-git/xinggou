package com.xinggou.admin.bc.vo.response;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.xinggou.bc.enums.PositionStatusEnum;
import com.xinggou.bc.enums.StaffStatusEnum;
import com.xinggou.common.enums.GenderEnum;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.util.Date;

/**
 * @author luoyb
 * Created on 2020/8/15
 */
@Data
public class StaffVo {

    @ApiModelProperty("职工ID")
    private Long id;
    @ApiModelProperty("用户名")
    private String userName;
    @ApiModelProperty("昵称")
    private String nickName;
    @ApiModelProperty("真实姓名")
    private String realName;
    @ApiModelProperty("部门ID")
    private Long deptId;
    @ApiModelProperty("头像")
    private String avatar;
    @ApiModelProperty("邮箱")
    private String email;
    @ApiModelProperty("手机号")
    private String mobile;
    @ApiModelProperty("帐号状态：10正常 20禁用")
    private StaffStatusEnum status;
    @ApiModelProperty("性别：10男 20女 30未知")
    private GenderEnum gender;
    @ApiModelProperty("职位")
    private String position;
    @ApiModelProperty("职位状态: 10在职 20离职")
    private PositionStatusEnum positionStatus;

    @ApiModelProperty("部门名称")
    private String deptName;
    @ApiModelProperty("生日，格式：yyyy-MM-dd")
    @JsonFormat(locale = "zh", timezone = "GMT+8", pattern = "yyyy-MM-dd")
    private Date birthday;
    @ApiModelProperty("帐号状态")
    private String statusDesc;
    @ApiModelProperty("性别")
    private String genderDesc;
    @ApiModelProperty("职位状态")
    private String positionStatusDesc;
    @ApiModelProperty("入职时间，格式：yyyy-MM-dd HH:mm:ss")
    @JsonFormat(locale = "zh", timezone = "GMT+8", pattern = "yyyy-MM-dd HH:mm:ss")
    private Date entryTime;
    @ApiModelProperty("离职时间，格式：yyyy-MM-dd HH:mm:ss")
    @JsonFormat(locale = "zh", timezone = "GMT+8", pattern = "yyyy-MM-dd HH:mm:ss")
    private Date exitTime;

}
