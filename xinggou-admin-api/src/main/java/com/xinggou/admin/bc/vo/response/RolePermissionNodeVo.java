package com.xinggou.admin.bc.vo.response;

import com.xinggou.bc.enums.MenuTypeEnum;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.util.ArrayList;
import java.util.List;

/**
 * @author luoyb
 * Created on 2020/8/18
 */
@Data
public class RolePermissionNodeVo {

    @ApiModelProperty("菜单等级")
    private int level;
    @ApiModelProperty("菜单ID")
    private Long menuId;
    @ApiModelProperty("父级ID")
    private Long parentId;

    @ApiModelProperty("菜单名称")
    private String name;
    @ApiModelProperty("菜单类型：10：目录 20：菜单 30：按钮")
    private MenuTypeEnum type;
    @ApiModelProperty("是否拥有权限")
    private boolean own;

    @ApiModelProperty("子集")
    private final List<RolePermissionNodeVo> childNodes = new ArrayList<>();
}
