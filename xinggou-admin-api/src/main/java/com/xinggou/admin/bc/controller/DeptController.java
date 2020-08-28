package com.xinggou.admin.bc.controller;

import com.github.xiaoymin.knife4j.annotations.ApiSupport;
import com.xinggou.admin.bc.converter.DeptConverter;
import com.xinggou.admin.bc.vo.request.CreateDeptRequest;
import com.xinggou.admin.bc.vo.request.UpdateDeptRequest;
import com.xinggou.admin.bc.vo.response.DeptNodeVo;
import com.xinggou.admin.bc.vo.response.DeptVo;
import com.xinggou.admin.common.aspect.OperateLog;
import com.xinggou.admin.common.core.BaseController;
import com.xinggou.admin.common.vo.request.LongIdRequest;
import com.xinggou.admin.common.vo.request.LongIdsRequest;
import com.xinggou.bc.bean.dto.DeptNode;
import com.xinggou.bc.entity.Dept;
import com.xinggou.bc.service.DeptService;
import com.xinggou.common.utils.R;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.apache.dubbo.config.annotation.Reference;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import javax.validation.Valid;

/**
 * 部门管理
 *
 * @author hdl
 * @date 2020-08-06 11:43:33
 */
@Api(tags = "部门管理")
@ApiSupport(order = 30)
@RestController
public class DeptController extends BaseController {

    @Reference
    private DeptService deptService;

    @ApiOperation("部门树")
    @PostMapping("bc/dept/tree")
    public R<DeptNodeVo> deptTree() {
        DeptNode treeNode = deptService.getDeptTree();
        DeptNodeVo vo = DeptConverter.INSTANCE.toDeptNodeVo(treeNode);
        return R.ok(vo);
    }

    @ApiOperation("部门信息")
    @PostMapping("bc/dept/info")
    public R<DeptVo> deptInfo(@RequestBody @Valid LongIdRequest request) {
        Dept dept = deptService.getDeptById(request.getId());
        DeptVo vo = DeptConverter.INSTANCE.toDeptVo(dept);
        return R.ok(vo);
    }

    @ApiOperation("新增部门")
    @OperateLog("新增部门")
    @PostMapping("bc/dept/create")
    public R createDept(@RequestBody @Valid CreateDeptRequest request) {
        deptService.createDept(DeptConverter.INSTANCE.toDept(request));
        return R.ok();
    }

    @ApiOperation("更新部门")
    @OperateLog("更新部门")
    @PostMapping("bc/dept/update")
    public R updateDept(@RequestBody @Valid UpdateDeptRequest request) {
        deptService.updateDept(DeptConverter.INSTANCE.toDept(request));
        return R.ok();
    }

    @ApiOperation("删除部门")
    @OperateLog("删除部门")
    @PostMapping("bc/dept/delete")
    public R deleteDept(@RequestBody @Valid LongIdsRequest request) {
        deptService.bulkDeleteDept(request.getIds());
        return R.ok();
    }

}
