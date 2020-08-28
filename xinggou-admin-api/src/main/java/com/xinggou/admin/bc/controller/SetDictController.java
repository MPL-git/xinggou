package com.xinggou.admin.bc.controller;

import com.github.xiaoymin.knife4j.annotations.ApiSupport;
import com.xinggou.admin.bc.converter.BcConverter;
import com.xinggou.admin.bc.vo.request.PaginateDictRequest;
import com.xinggou.admin.bc.vo.request.SaveDictRequest;
import com.xinggou.admin.bc.vo.response.DictVo;
import com.xinggou.admin.common.core.BaseController;
import com.xinggou.admin.common.vo.request.LongIdRequest;
import com.xinggou.admin.common.vo.request.LongIdsRequest;
import com.xinggou.bc.entity.SetDict;
import com.xinggou.bc.service.BcModifyService;
import com.xinggou.bc.service.BcQueryService;
import com.xinggou.admin.common.aspect.OperateLog;
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
 * 数据字典表
 *
 * @author hdl
 * @date 2020-08-06 11:43:33
 */
@Api(tags = "数据字典管理")
@ApiSupport(order = 70)
@RestController
public class SetDictController extends BaseController {
    @Reference
    private BcQueryService bcQueryService;
    @Reference
    private BcModifyService bcModifyService;


    @ApiOperation("数据字典列表")
    @PostMapping("bc/dict/list")
    public R<PageWrap<DictVo>> paginateDict(@RequestBody @Valid PaginateDictRequest request) {
        PageWrap<SetDict> pageWrap = bcQueryService.paginateDict(BcConverter.INSTANCE.toPaginateDictParams(request));
        PageWrap<DictVo> result = BcConverter.INSTANCE.toDictVoPageWrap(pageWrap);
        return R.ok(result);
    }

    @ApiOperation(value = "新增（修改）数据字典", notes = "ID不为空时表示修改")
    @OperateLog("新增（修改）数据字典")
    @PostMapping("bc/dict/save")
    public R save(@RequestBody @Valid SaveDictRequest request) {
        bcModifyService.saveDict(BcConverter.INSTANCE.toSaveDictParams(request));
        return R.ok();
    }

    @ApiOperation("删除数据字典")
    @OperateLog("删除数据字典")
    @PostMapping("bc/dict/delete")
    public R delete(@RequestBody @Valid LongIdsRequest request) {
        bcModifyService.deleteDict(request.getIds());
        return R.ok();
    }


    @ApiOperation("数据字典信息")
    @PostMapping("bc/dict/info")
    public R<DictVo> info(@RequestBody @Valid LongIdRequest request) {
        SetDict dict = bcQueryService.getDictById(request.getId());
        DictVo vo = BcConverter.INSTANCE.toDictVo(dict);
        return R.ok(vo);
    }

}
