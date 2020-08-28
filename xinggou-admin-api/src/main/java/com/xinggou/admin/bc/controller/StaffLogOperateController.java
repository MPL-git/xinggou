package com.xinggou.admin.bc.controller;

import com.github.xiaoymin.knife4j.annotations.ApiSupport;
import com.xinggou.admin.bc.converter.BcConverter;
import com.xinggou.admin.bc.vo.request.staff.PaginateStaffLogOperateRequest;
import com.xinggou.admin.bc.vo.response.StaffLogOperateVo;
import com.xinggou.admin.common.core.BaseController;
import com.xinggou.bc.bean.dto.StaffLogOperateDTO;
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
 * 平台工作人员操作日志
 *
 * @author hdl
 * @date 2020-08-06 11:43:33
 */
@Api(tags = "员工操作日志")
@ApiSupport(order = 820)
@RestController
public class StaffLogOperateController extends BaseController {

    @Reference
    private BcQueryService bcQueryService;

    @ApiOperation("员工操作日志列表")
    @PostMapping("bc/staff/operatelog/list")
    public R<PageWrap<StaffLogOperateVo>> paginate(@RequestBody @Valid PaginateStaffLogOperateRequest request) {
        PageWrap<StaffLogOperateDTO> pageWrap = bcQueryService.paginateStaffLogOperate(BcConverter.INSTANCE.toPaginateStaffLogOperateParams(request));
        PageWrap<StaffLogOperateVo> vo = BcConverter.INSTANCE.toStaffLogOperateVoPageWarp(pageWrap);
        return R.ok(vo);
    }

}
