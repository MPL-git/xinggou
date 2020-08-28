package com.xinggou.admin.rc.controller;

import com.github.xiaoymin.knife4j.annotations.ApiSupport;
import com.xinggou.admin.common.core.BaseController;
import com.xinggou.admin.common.oss.CloudStorageService;
import com.xinggou.admin.common.oss.OSSFactory;
import com.xinggou.admin.common.vo.request.LongIdsRequest;
import com.xinggou.admin.rc.converter.RcConverter;
import com.xinggou.admin.rc.vo.request.PaginateOssFileRequest;
import com.xinggou.admin.rc.vo.response.OssVo;
import com.xinggou.bc.entity.SetConfig;
import com.xinggou.bc.enums.ConfigTypeEnum;
import com.xinggou.bc.service.BcQueryService;
import com.xinggou.common.constant.Const;
import com.xinggou.common.exception.BizException;
import com.xinggou.common.utils.DateUtils;
import com.xinggou.common.utils.R;
import com.xinggou.common.utils.StrKit;
import com.xinggou.common.vo.PageWrap;
import com.xinggou.rc.bean.params.PaginateOssFileParams;
import com.xinggou.rc.entity.Oss;
import com.xinggou.rc.service.RcModifyService;
import com.xinggou.rc.service.RcQueryService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.apache.dubbo.config.annotation.Reference;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import javax.validation.Valid;

/**
 * 文件上传
 *
 * @author hdl
 * @date 2020-08-06 11:43:33
 */
@Api(tags = "文件管理")
@ApiSupport(order = 10)
@RestController
public class OssController extends BaseController {
    @Reference
    private RcQueryService rcQueryService;
    @Reference
    private RcModifyService rcModifyService;
    @Reference
    private BcQueryService bcQueryService;

    @ApiOperation("上传文件")
    @PostMapping("rc/oss/upload")
    public R upload(@RequestParam("file") MultipartFile file) throws Exception {
        if (file.isEmpty()) {
            throw new BizException("上传文件不能为空");
        }
        String fileName = file.getOriginalFilename();
        if (StrKit.isBlank(fileName)) {
            throw new BizException("上传文件名不能为空");
        }

        SetConfig setConfig = bcQueryService.findConfigByKey(ConfigTypeEnum.OSS.getKey());
        CloudStorageService cloudStorageService = OSSFactory.build(setConfig);
        if (cloudStorageService == null) {
            throw new BizException("未添加文件上传配置");
        }

        //上传文件
        String suffix = fileName.substring(fileName.lastIndexOf(Const.DOT));
        String url = cloudStorageService.uploadSuffix(file.getBytes(), suffix);

        //保存文件信息
        rcModifyService.createOss(url);
        return R.ok(url);
    }

    @ApiOperation("文件列表")
    @PostMapping("rc/oss/list")
    public R<PageWrap<OssVo>> paginate(@RequestBody @Valid PaginateOssFileRequest request) {
        PaginateOssFileParams params = RcConverter.INSTANCE.toPaginateOssFileParams(request);
        if (request.getEndDate() != null) {
            params.setEndDate(DateUtils.endOfDay(request.getEndDate()));
        }
        PageWrap<Oss> pageWrap = rcQueryService.paginateOssFile(params);
        PageWrap<OssVo> vo = RcConverter.INSTANCE.toOssVoPageWrap(pageWrap);
        return R.ok(vo);
    }

    @ApiOperation("删除文件")
    @PostMapping("rc/oss/delete")
    public R delete(@RequestBody @Valid LongIdsRequest request) {
        rcModifyService.deleteOss(request.getIds());
        return R.ok();
    }
}
