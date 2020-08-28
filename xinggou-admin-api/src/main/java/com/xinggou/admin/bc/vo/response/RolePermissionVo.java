package com.xinggou.admin.bc.vo.response;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.util.ArrayList;
import java.util.List;

/**
 * @author luoyb
 * Created on 2020/8/21
 */
@Data
public class RolePermissionVo {

    @ApiModelProperty("角色权限树")
    private RolePermissionNodeVo tree;

    @ApiModelProperty("角色已绑定的菜单ID集合")
    private final List<Long> ownMenuIdList = new ArrayList<>();

}
