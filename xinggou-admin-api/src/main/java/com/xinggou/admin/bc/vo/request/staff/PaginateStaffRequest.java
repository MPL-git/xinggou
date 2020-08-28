package com.xinggou.admin.bc.vo.request.staff;

import com.xinggou.admin.common.vo.request.PageRequest;
import com.xinggou.bc.enums.StaffStatusEnum;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

/**
 * @author luoyb
 * Created on 2020/8/15
 */
@Data
public class PaginateStaffRequest extends PageRequest {

    @ApiModelProperty("部门ID")
    private Long deptId;

    @ApiModelProperty("昵称")
    private String nickName;

    @ApiModelProperty("账号")
    private String userName;

    @ApiModelProperty("真实姓名")
    private String realName;

    @ApiModelProperty("手机号")
    private String mobile;

    @ApiModelProperty("帐号状态：10正常 20禁用")
    private StaffStatusEnum status;

}
