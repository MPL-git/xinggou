package com.xinggou.admin.bc.vo.request;

import com.xinggou.admin.common.vo.request.PageRequest;
import com.xinggou.bc.enums.ConfigStatusEnum;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

/**
 * @author luoyb
 * Created on 2020/8/19
 */
@Data
public class PaginateConfigRequest extends PageRequest {

    @ApiModelProperty("名称")
    private String name;

    @ApiModelProperty("状态：10显示 20隐藏")
    private ConfigStatusEnum status;

}
