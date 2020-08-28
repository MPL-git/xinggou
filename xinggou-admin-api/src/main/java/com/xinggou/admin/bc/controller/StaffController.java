package com.xinggou.admin.bc.controller;

import com.github.xiaoymin.knife4j.annotations.ApiSupport;
import com.xinggou.admin.bc.converter.StaffConverter;
import com.xinggou.admin.bc.vo.request.staff.CreateStaffRequest;
import com.xinggou.admin.bc.vo.request.staff.PaginateRoleForStaffRequest;
import com.xinggou.admin.bc.vo.request.staff.PaginateStaffRequest;
import com.xinggou.admin.bc.vo.request.staff.UpdateStaffRequest;
import com.xinggou.admin.bc.vo.request.staff.UpdateStaffRoleRequest;
import com.xinggou.admin.bc.vo.response.StaffRoleVo;
import com.xinggou.admin.bc.vo.response.StaffVo;
import com.xinggou.admin.common.aspect.OperateLog;
import com.xinggou.admin.common.core.BaseController;
import com.xinggou.admin.common.vo.request.LongIdRequest;
import com.xinggou.admin.common.vo.request.LongIdsRequest;
import com.xinggou.bc.bean.dto.RoleDTO;
import com.xinggou.bc.bean.dto.StaffDTO;
import com.xinggou.bc.service.StaffService;
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
 * 系统用户
 *
 * @author hdl
 * @date 2020-08-06 11:43:33
 */
@Api(tags = "员工管理")
@ApiSupport(order = 40)
@RestController
public class StaffController extends BaseController {

    @Reference
    private StaffService staffService;

    @ApiOperation("员工列表")
    @PostMapping("bc/staff/list")
    public R<PageWrap<StaffVo>> paginate(@RequestBody @Valid PaginateStaffRequest request) {
        PageWrap<StaffDTO> staffPageWrap = staffService.findStaff(StaffConverter.INSTANCE.toPaginateStaffParams(request));
        PageWrap<StaffVo> result = StaffConverter.INSTANCE.toStaffVoPageWrap(staffPageWrap);
        return R.ok(result);
    }

    @ApiOperation("员工信息")
    @PostMapping("bc/staff/info")
    public R<StaffVo> info(@RequestBody @Valid LongIdRequest request) {
        StaffDTO staff = staffService.getStaffDetail(request.getId());
        StaffVo vo = StaffConverter.INSTANCE.toStaffVo(staff);
        return R.ok(vo);
    }

    @ApiOperation("新增员工")
    @OperateLog("新增员工")
    @PostMapping("bc/staff/create")
    public R create(@RequestBody @Valid CreateStaffRequest request) {
        staffService.createStaff(StaffConverter.INSTANCE.toCreateStaffParams(request));
        return R.ok();
    }

    @ApiOperation("更新员工")
    @OperateLog("更新员工")
    @PostMapping("bc/staff/update")
    public R save(@RequestBody @Valid UpdateStaffRequest request) {
        staffService.updateStaff(StaffConverter.INSTANCE.toUpdateStaffParams(request));
        return R.ok();
    }

    @ApiOperation("重置员工密码")
    @OperateLog("重置员工密码")
    @PostMapping("bc/staff/password/reset")
    public R resetStaffPassword(@RequestBody @Valid LongIdRequest request) {
        staffService.resetPassword(request.getId());
        return R.ok();
    }

    @ApiOperation("删除员工")
    @OperateLog("删除员工")
    @PostMapping("bc/staff/delete")
    public R delete(@RequestBody @Valid LongIdsRequest request) {
        staffService.bulkDeleteStaff(request.getIds());
        return R.ok();
    }

    @ApiOperation("禁用员工")
    @OperateLog("禁用员工")
    @PostMapping("bc/staff/disabled")
    public R disabled(@RequestBody @Valid LongIdRequest request) {
        staffService.disabledStaffById(request.getId());
        return R.ok();
    }

    @ApiOperation("激活员工")
    @OperateLog("激活员工")
    @PostMapping("bc/staff/active")
    public R active(@RequestBody @Valid LongIdRequest request) {
        staffService.activeStaffById(request.getId());
        return R.ok();
    }

    @ApiOperation("员工角色列表查询")
    @PostMapping("bc/staff/role/list")
    public R<PageWrap<StaffRoleVo>> paginateRoleForStaff(@RequestBody @Valid PaginateRoleForStaffRequest request) {
        PageWrap<RoleDTO> pageWrap = staffService.paginateRoleForStaff(StaffConverter.INSTANCE.toPaginateRoleForStaffParams(request));
        PageWrap<StaffRoleVo> vo = StaffConverter.INSTANCE.toStaffRoleVoPageWrap(pageWrap);
        return R.ok(vo);
    }

    @ApiOperation("员工角色添加")
    @OperateLog("员工角色添加")
    @PostMapping("bc/staff/role/add")
    public R addStaffRole(@RequestBody @Valid UpdateStaffRoleRequest request) {
        staffService.addStaffRole(request.getStaffId(), request.getRoleIdList());
        return R.ok();
    }

    @ApiOperation("员工角色移除")
    @OperateLog("员工角色移除")
    @PostMapping("bc/staff/role/remove")
    public R removeStaffRole(@RequestBody @Valid UpdateStaffRoleRequest request) {
        staffService.removeStaffRole(request.getStaffId(), request.getRoleIdList());
        return R.ok();
    }
}
