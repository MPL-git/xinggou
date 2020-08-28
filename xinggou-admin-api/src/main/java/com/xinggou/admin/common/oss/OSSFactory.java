package com.xinggou.admin.common.oss;


import com.alibaba.fastjson.JSONObject;
import com.xinggou.bc.entity.SetConfig;
import com.xinggou.common.enums.CloudServiceEnum;
import com.xinggou.rc.bean.CloudStorageConfig;
import org.springframework.util.StringUtils;

/**
 * 文件上传Factory
 */
public final class OSSFactory {

    public static CloudStorageService build(SetConfig setConfig ) {
        //获取云存储配置信息
        if (setConfig == null || StringUtils.isEmpty(setConfig.getParamValue())) {
            return null;
        }

        CloudStorageConfig config = JSONObject.parseObject(setConfig.getParamValue(), CloudStorageConfig.class);
        if (config.getType() == CloudServiceEnum.QINIU.getValue()) {
            return new QiniuCloudStorageService(config);
        } else if (config.getType() == CloudServiceEnum.ALIYUN.getValue()) {
            return new AliyunCloudStorageService(config);
        } else if (config.getType() == CloudServiceEnum.QCLOUD.getValue()) {
            return new QcloudCloudStorageService(config);
        } else if (config.getType() == CloudServiceEnum.LOCAL.getValue()) {
            return new LocalStorageService(config);
        } else if (config.getType() == CloudServiceEnum.FTP.getValue()) {
            return new FtpStorageService(config);
        }

        return null;
    }

}
