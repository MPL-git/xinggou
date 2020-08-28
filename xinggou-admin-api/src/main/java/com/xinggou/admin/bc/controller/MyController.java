package com.xinggou.admin.bc.controller;

import com.alibaba.fastjson.JSONObject;
import com.github.xiaoymin.knife4j.annotations.ApiSupport;
import com.xinggou.admin.bc.converter.BcConverter;
import com.xinggou.admin.bc.converter.DeptConverter;
import com.xinggou.admin.bc.converter.StaffConverter;
import com.xinggou.admin.bc.vo.request.staff.UpdateStaffPasswordRequest;
import com.xinggou.admin.bc.vo.response.CurrentStaffVo;
import com.xinggou.admin.bc.vo.response.DeptNodeVo;
import com.xinggou.admin.bc.vo.response.PermissionNodeVo;
import com.xinggou.admin.common.aspect.OperateLog;
import com.xinggou.admin.common.core.BaseController;
import com.xinggou.bc.bean.dto.DeptNode;
import com.xinggou.bc.bean.dto.PermissionNode;
import com.xinggou.bc.bean.dto.StaffDTO;
import com.xinggou.bc.service.DeptService;
import com.xinggou.bc.service.PermissionService;
import com.xinggou.bc.service.StaffService;
import com.xinggou.common.exception.Assert;
import com.xinggou.common.utils.R;
import com.xinggou.common.utils.ValidateKit;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.apache.dubbo.config.annotation.Reference;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import javax.validation.Valid;
import java.util.ArrayList;
import java.util.List;

@Api(tags = "当前用户信息")
@ApiSupport(order = 20)
@RestController
public class MyController extends BaseController {

    @Reference
    private PermissionService permissionService;
    @Reference
    private StaffService staffService;
    @Reference
    private DeptService deptService;

    @ApiOperation("我的信息")
    @PostMapping("my/info")
    public R<CurrentStaffVo> myInfo() {
        StaffDTO staffDTO = staffService.getStaffDetail(getStaffId());
        CurrentStaffVo vo = StaffConverter.INSTANCE.toCurrentStaffVo(staffDTO);
        return R.ok(vo);
    }

    @ApiOperation("修改密码")
    @OperateLog("修改密码")
    @PostMapping("my/password/update")
    public R updateStaffPassword(@RequestBody @Valid UpdateStaffPasswordRequest request) {
        Assert.notFalse(ValidateKit.validatePassword(request.getPassword()), "无效的密码");

        staffService.updatePassword(getStaffId(), request.getPassword());
        return R.ok();
    }

    @ApiOperation("我的权限树")
    @PostMapping("my/permission/tree")
    public R<PermissionNodeVo> permissionTree() {
        PermissionNode treeNode = permissionService.getStaffPermissionTree(getStaffId());
        PermissionNodeVo vo = BcConverter.INSTANCE.toPermissionNodeVo(treeNode);
        return R.ok(vo);
    }

    @ApiOperation("我的部门树")
    @PostMapping("my/dept/tree")
    public R<DeptNodeVo> deptTree() {
        DeptNode treeNode = deptService.getStaffDeptTree(getStaffId());
        DeptNodeVo vo = DeptConverter.INSTANCE.toDeptNodeVo(treeNode);
        return R.ok(vo);
    }

    @ApiOperation("消息中心")
    @GetMapping("my/message")
    public R<List<JSONObject>> message() {
        List<JSONObject> list = new ArrayList<>();
        JSONObject notice1 = new JSONObject();
        notice1.put("id", 10);
        notice1.put("title", "站内信");

        List<JSONObject> systemMsg = new ArrayList<>();
        JSONObject msg1 = new JSONObject();
        msg1.put("id", 101);
        msg1.put("avatar", "statics/libs/pear/admin/images/success.png");
        msg1.put("title", "武林令牌");
        msg1.put("context", "请速到武当一趟");
        msg1.put("form", "三丰");
        msg1.put("time", "2019-02-15");
        systemMsg.add(msg1);
        systemMsg.add(msg1);
        systemMsg.add(msg1);

        notice1.put("children", systemMsg);
        list.add(notice1);

        JSONObject notice2 = new JSONObject();
        notice2.put("id", 20);
        notice2.put("title", "消息");

        List<JSONObject> messagelist = new ArrayList<>();
        JSONObject msg2 = new JSONObject();
        msg2.put("id", 101);
        msg2.put("avatar", "statics/libs/pear/admin/images/success.png");
        msg2.put("title", "华山武林令牌");
        msg2.put("context", "请速到华山一趟");
        msg2.put("form", "三丰");
        msg2.put("time", "2019-02-15");
        messagelist.add(msg2);
        messagelist.add(msg2);
        messagelist.add(msg2);

        notice2.put("children", messagelist);
        list.add(notice2);

        return R.ok(list);
    }
}
