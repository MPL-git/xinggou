package com.xinggou.admin.bc.controller;

import com.github.xiaoymin.knife4j.annotations.ApiSupport;
import com.xinggou.admin.bc.converter.BcConverter;
import com.xinggou.admin.bc.vo.request.CreateMenuRequest;
import com.xinggou.admin.bc.vo.request.UpdateMenuRequest;
import com.xinggou.admin.bc.vo.response.MenuNodeVo;
import com.xinggou.admin.bc.vo.response.MenuVo;
import com.xinggou.admin.common.aspect.OperateLog;
import com.xinggou.admin.common.core.BaseController;
import com.xinggou.admin.common.vo.request.LongIdRequest;
import com.xinggou.bc.bean.dto.MenuDTO;
import com.xinggou.bc.bean.dto.MenuNode;
import com.xinggou.bc.service.BcModifyService;
import com.xinggou.bc.service.BcQueryService;
import com.xinggou.bc.service.PermissionService;
import com.xinggou.common.utils.R;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.apache.dubbo.config.annotation.Reference;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import javax.validation.Valid;

/**
 * 菜单管理
 *
 * @author hdl
 * @date 2020-08-06 11:43:33
 */
@Api(tags = "菜单管理")
@ApiSupport(order = 60)
@RestController
public class SetMenuController extends BaseController {
    @Reference
    private BcQueryService bcQueryService;
    @Reference
    private BcModifyService bcModifyService;
    @Reference
    private PermissionService permissionService;

    @ApiOperation("菜单树")
    @PostMapping("bc/menu/tree")
    public R<MenuNodeVo> getMenuTree() {
        MenuNode tree = permissionService.getMenuTree();
        MenuNodeVo treeVo = BcConverter.INSTANCE.toMenuNodeTreeVo(tree);
        return R.ok(treeVo);
    }

    @ApiOperation("新增菜单")
    @OperateLog("新增菜单")
    @PostMapping("bc/menu/create")
    public R createMenu(@RequestBody @Valid CreateMenuRequest request) {
        bcModifyService.createMenu(BcConverter.INSTANCE.toCreateMenuParams(request));
        return R.ok();
    }

    @ApiOperation("更新菜单")
    @OperateLog("更新菜单")
    @PostMapping("bc/menu/update")
    public R updateMenu(@RequestBody @Valid UpdateMenuRequest request) {
        bcModifyService.updateMenu(BcConverter.INSTANCE.toUpdateMenuParams(request));
        return R.ok();
    }

    @ApiOperation("删除菜单")
    @OperateLog("删除菜单")
    @PostMapping("bc/menu/delete")
    public R delete(@RequestBody @Valid LongIdRequest request) {
        bcModifyService.deleteMenu(request.getId());
        return R.ok();
    }

    @ApiOperation("菜单信息")
    @PostMapping("bc/menu/info")
    public R<MenuVo> info(@RequestBody @Valid LongIdRequest request) {
        MenuDTO dto = bcQueryService.getMenuById(request.getId());
        MenuVo vo = BcConverter.INSTANCE.toMenuVo(dto);
        return R.ok(vo);
    }

}
