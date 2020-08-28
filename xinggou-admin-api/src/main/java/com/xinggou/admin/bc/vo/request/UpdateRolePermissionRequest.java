package com.xinggou.admin.bc.vo.request;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import javax.validation.constraints.NotNull;
import java.util.List;

/**
 * @author luoyb
 * Created on 2020/8/18
 */
@Data
public class UpdateRolePermissionRequest {

    @ApiModelProperty(value = "角色ID", required = true)
    @NotNull(message = "角色ID不能为空")
    private Long roleId;
    @ApiModelProperty(value = "权限ID列表，空表示移除权限")
    private List<Long> menuIds;

}
