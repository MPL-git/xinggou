package com.xinggou.admin.bc.controller;

import com.alibaba.fastjson.JSONObject;
import com.github.xiaoymin.knife4j.annotations.ApiSupport;
import com.xinggou.admin.bc.converter.BcConverter;
import com.xinggou.admin.bc.vo.request.PaginateConfigRequest;
import com.xinggou.admin.bc.vo.request.SaveConfigRequest;
import com.xinggou.admin.bc.vo.response.ConfigVo;
import com.xinggou.admin.common.aspect.OperateLog;
import com.xinggou.admin.common.core.BaseController;
import com.xinggou.admin.common.vo.request.LongIdRequest;
import com.xinggou.admin.common.vo.request.LongIdsRequest;
import com.xinggou.bc.bean.params.SaveConfigParams;
import com.xinggou.bc.entity.SetConfig;
import com.xinggou.bc.enums.ConfigTypeEnum;
import com.xinggou.bc.service.BcModifyService;
import com.xinggou.bc.service.BcQueryService;
import com.xinggou.common.enums.CloudServiceEnum;
import com.xinggou.common.utils.R;
import com.xinggou.common.utils.validator.ValidatorUtils;
import com.xinggou.common.utils.validator.group.AliyunGroup;
import com.xinggou.common.utils.validator.group.FtpGroup;
import com.xinggou.common.utils.validator.group.LocalGroup;
import com.xinggou.common.utils.validator.group.QcloudGroup;
import com.xinggou.common.utils.validator.group.QiniuGroup;
import com.xinggou.common.vo.PageWrap;
import com.xinggou.rc.bean.CloudStorageConfig;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.apache.dubbo.config.annotation.Reference;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;
import springfox.documentation.annotations.ApiIgnore;

import javax.validation.Valid;

/**
 * 系统配置信息表
 *
 * @author hdl
 * @date 2020-08-06 11:43:33
 */
@ApiIgnore
@Api(tags = "系统设置")
@ApiSupport(order = 700)
@RestController
public class SetConfigController extends BaseController {
    @Reference
    private BcQueryService bcQueryService;
    @Reference
    private BcModifyService bcModifyService;

    @ApiOperation("配置列表")
    @PostMapping("bc/config/list")
    public R<PageWrap<ConfigVo>> paginateConfig(@RequestBody @Valid PaginateConfigRequest request) {
        PageWrap<SetConfig> pageWrap = bcQueryService.paginateConfig(BcConverter.INSTANCE.toPaginateConfigParams(request));
        PageWrap<ConfigVo> vo = BcConverter.INSTANCE.toConfigVoPageWrap(pageWrap);
        return R.ok(vo);
    }

    @ApiOperation(value = "新增（修改）配置列表", notes = "ID不为空时表示修改")
    @OperateLog("新增（修改）配置列表")
    @PostMapping("bc/config/save")
    public R saveConfig(@RequestBody @Valid SaveConfigRequest request) {
        bcModifyService.saveConfig(BcConverter.INSTANCE.toSaveConfigParams(request));
        return R.ok();
    }

    @ApiOperation("删除配置")
    @OperateLog("删除配置")
    @PostMapping("bc/config/delete")
    public R deleteConfig(@RequestBody @Valid LongIdsRequest request) {
        bcModifyService.deleteConfig(request.getIds());
        return R.ok();
    }

    @ApiOperation("配置信息")
    @PostMapping("bc/config/info")
    public R<ConfigVo> configIInfo(@RequestBody @Valid LongIdRequest request) {
        SetConfig config = bcQueryService.getConfigById(request.getId());
        ConfigVo vo = BcConverter.INSTANCE.toConfigVo(config);
        return R.ok(vo);
    }

    @ApiOperation("云存储配置信息")
    @PostMapping("bc/config/oss/info")
    public R<CloudStorageConfig> ossConfigInfo() {
        SetConfig setConfig = bcQueryService.findConfigByKey(ConfigTypeEnum.OSS.getKey());
        CloudStorageConfig config;
        if (setConfig == null || StringUtils.isEmpty(setConfig.getParamValue())) {
            config = new CloudStorageConfig();
        } else {
            config = JSONObject.parseObject(setConfig.getParamValue(), CloudStorageConfig.class);
        }
        return R.ok(config);
    }

    @ApiOperation("保存云存储配置")
    @OperateLog("保存云存储配置")
    @PostMapping("bc/config/oss/save")
    public R saveOssConfig(@RequestBody CloudStorageConfig config) {
        ValidatorUtils.validateEntity(config);

        if (config.getType() == CloudServiceEnum.QINIU.getValue()) {
            ValidatorUtils.validateEntity(config, QiniuGroup.class);
        } else if (config.getType() == CloudServiceEnum.ALIYUN.getValue()) {
            ValidatorUtils.validateEntity(config, AliyunGroup.class);
        } else if (config.getType() == CloudServiceEnum.QCLOUD.getValue()) {
            ValidatorUtils.validateEntity(config, QcloudGroup.class);
        } else if (config.getType() == CloudServiceEnum.LOCAL.getValue()) {
            ValidatorUtils.validateEntity(config, LocalGroup.class);
        } else if (config.getType() == CloudServiceEnum.FTP.getValue()) {
            ValidatorUtils.validateEntity(config, FtpGroup.class);
        }

        SaveConfigParams params = new SaveConfigParams();
        params.setName(ConfigTypeEnum.OSS.getName());
        params.setParamKey(ConfigTypeEnum.OSS.getKey());
        params.setParamValue(JSONObject.toJSONString(config));
        bcModifyService.saveConfigByKey(params);
        return R.ok();
    }


}
