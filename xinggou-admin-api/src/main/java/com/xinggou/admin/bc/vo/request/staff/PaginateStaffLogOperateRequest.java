package com.xinggou.admin.bc.vo.request.staff;

import com.xinggou.admin.common.vo.request.PageRequest;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

/**
 * @author luoyb
 * Created on 2020/8/19
 */
@Data
public class PaginateStaffLogOperateRequest extends PageRequest {

    @ApiModelProperty("员工ID")
    private Long staffId;
    @ApiModelProperty("用户操作")
    private String operation;
    @ApiModelProperty("IP地址")
    private String ip;

}
