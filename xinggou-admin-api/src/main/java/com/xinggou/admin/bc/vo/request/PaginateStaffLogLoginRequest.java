package com.xinggou.admin.bc.vo.request;

import com.xinggou.admin.common.vo.request.PageRequest;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

/**
 * @author luoyb
 * Created on 2020/8/19
 */
@Data
public class PaginateStaffLogLoginRequest extends PageRequest {

    @ApiModelProperty("员工ID")
    private Long staffId;
    @ApiModelProperty("来源IP")
    private String ip;

}
