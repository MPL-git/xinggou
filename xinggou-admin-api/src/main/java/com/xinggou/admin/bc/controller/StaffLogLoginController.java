package com.xinggou.admin.bc.controller;

import com.github.xiaoymin.knife4j.annotations.ApiSupport;
import com.xinggou.admin.bc.converter.BcConverter;
import com.xinggou.admin.bc.vo.request.PaginateStaffLogLoginRequest;
import com.xinggou.admin.bc.vo.response.StaffLogLoginVo;
import com.xinggou.admin.common.core.BaseController;
import com.xinggou.bc.entity.StaffLogLogin;
import com.xinggou.bc.service.BcQueryService;
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
 * 平台工作人员登录日志
 *
 * @author hdl
 * @date 2020-08-06 11:43:33
 */
@Api(tags = "员工登录日志")
@ApiSupport(order = 810)
@RestController
public class StaffLogLoginController extends BaseController {

    @Reference
    private BcQueryService bcQueryService;

    @ApiOperation("员工登录日志列表")
    @PostMapping("bc/staff/loginlog/list")
    public R<PageWrap<StaffLogLoginVo>> paginate(@RequestBody @Valid PaginateStaffLogLoginRequest request) {
        PageWrap<StaffLogLogin> pageWrap = bcQueryService.paginateStaffLogLogin(BcConverter.INSTANCE.toPaginateStaffLogLoginParams(request));
        PageWrap<StaffLogLoginVo> vo = BcConverter.INSTANCE.toStaffLogLoginVoPageWrap(pageWrap);
        return R.ok(vo);
    }

}
