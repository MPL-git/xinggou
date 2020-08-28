package com.xinggou.admin.bc.controller;

import com.github.xiaoymin.knife4j.annotations.ApiSupport;
import com.xinggou.admin.bc.converter.BcConverter;
import com.xinggou.admin.bc.vo.request.PaginateRoleRequest;
import com.xinggou.admin.bc.vo.request.SaveRoleRequest;
import com.xinggou.admin.bc.vo.request.UpdateRolePermissionRequest;
import com.xinggou.admin.bc.vo.response.RolePermissionVo;
import com.xinggou.admin.bc.vo.response.RoleSimpleVo;
import com.xinggou.admin.bc.vo.response.RoleVo;
import com.xinggou.admin.common.aspect.OperateLog;
import com.xinggou.admin.common.core.BaseController;
import com.xinggou.admin.common.vo.request.LongIdRequest;
import com.xinggou.admin.common.vo.request.LongIdsRequest;
import com.xinggou.bc.bean.dto.RoleDTO;
import com.xinggou.bc.bean.dto.RolePermissionDTO;
import com.xinggou.bc.entity.SetRole;
import com.xinggou.bc.service.BcModifyService;
import com.xinggou.bc.service.BcQueryService;
import com.xinggou.bc.service.PermissionService;
import com.xinggou.common.utils.R;
import com.xinggou.common.vo.PageWrap;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.apache.dubbo.config.annotation.Reference;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import javax.validation.Valid;

/**
 * 角色
 *
 * @author hdl
 * @date 2020-08-06 11:43:33
 */
@Api(tags = "角色管理")
@ApiSupport(order = 50)
@RestController
public class SetRoleController extends BaseController {

    @Reference
    private BcQueryService bcQueryService;
    @Reference
    private BcModifyService bcModifyService;
    @Reference
    private PermissionService permissionService;

    @ApiOperation("角色列表")
    @PostMapping("bc/role/list")
    public R<PageWrap<RoleVo>> paginate(@RequestBody @Valid PaginateRoleRequest request) {
        PageWrap<RoleDTO> pageWrap = bcQueryService.paginateRole(BcConverter.INSTANCE.toPaginateRoleParams(request));
        PageWrap<RoleVo> result = BcConverter.INSTANCE.toRoleVoPageWrap(pageWrap);
        return R.ok(result);
    }

    @ApiOperation(value = "新增（更新）角色", notes = "roleId不为空时表示修改")
    @OperateLog("新增（更新）角色")
    @PostMapping("bc/role/save")
    public R save(@RequestBody @Valid SaveRoleRequest request) {
        bcModifyService.saveRole(BcConverter.INSTANCE.toSaveRoleParams(request));
        return R.ok();
    }

    @ApiOperation("删除角色")
    @OperateLog("删除角色")
    @PostMapping("bc/role/delete")
    public R delete(@RequestBody @Valid LongIdsRequest request) {
        bcModifyService.deleteRole(request.getIds());
        return R.ok();
    }

    @ApiOperation("角色信息")
    @PostMapping("bc/role/info")
    public R<RoleSimpleVo> info(@RequestBody @Valid LongIdRequest request) {
        SetRole role = bcQueryService.getRoleById(request.getId());
        RoleSimpleVo vo = BcConverter.INSTANCE.toRoleSimpleVo(role);
        return R.ok(vo);
    }

    @ApiOperation("角色权限树获取（返回整颗树）")
    @PostMapping("bc/role/permission/tree")
    public R<RolePermissionVo> getRolePermissionNode(@RequestBody @Valid LongIdRequest request) {
        RolePermissionDTO dto = permissionService.getRolePermission(request.getId());
        RolePermissionVo vo = BcConverter.INSTANCE.toRolePermissionVo(dto);
        return R.ok(vo);
    }

    @ApiOperation("更新角色权限")
    @OperateLog("更新角色权限")
    @PostMapping("bc/role/permission/update")
    public R updateRolePermission(@RequestBody @Valid UpdateRolePermissionRequest request) {
        permissionService.updateRolePermission(request.getRoleId(), request.getMenuIds());
        return R.ok();
    }
}
