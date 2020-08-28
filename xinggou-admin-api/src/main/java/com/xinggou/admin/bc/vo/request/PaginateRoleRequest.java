package com.xinggou.admin.bc.vo.request;

import com.xinggou.admin.common.vo.request.PageRequest;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

/**
 * @author luoyb
 * Created on 2020/8/18
 */
@Data
public class PaginateRoleRequest extends PageRequest {

    @ApiModelProperty("角色名称")
    private String name;

}
