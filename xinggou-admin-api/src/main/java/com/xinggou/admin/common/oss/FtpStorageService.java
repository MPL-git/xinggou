package com.xinggou.admin.common.oss;


import com.xinggou.common.exception.BizException;
import com.xinggou.common.utils.DateUtils;
import com.xinggou.rc.bean.CloudStorageConfig;

import java.io.ByteArrayInputStream;
import java.io.InputStream;
import java.util.Date;
import java.util.UUID;

/**
 * 阿里云存储
 *
 *
 */
public class FtpStorageService extends CloudStorageService{


    public FtpStorageService(CloudStorageConfig config){
        this.config = config;
    }

    @Override
    public String getPath(String prefix, String suffix) {
        return super.getPath(prefix, suffix);
    }

    @Override
    public String upload(byte[] data, String path) {
        return null;
    }

    @Override
    public String upload(InputStream inputStream, String path) {
        return null;
    }

    public String upload(byte[] data, String path, String fileName) {
        return upload(new ByteArrayInputStream(data), path,fileName);
    }

    public String upload(InputStream inputStream, String path,String fileName) {
        try {
            if(config.getFtpType().equals(1)){
                boolean flag = FtpUtil.uploadFile(config.getFtpHost(), config.getFtpPort(),config.getFtpUsername(), config.getFtpPassword(), config.getFtpBaseDir(),path,fileName, inputStream);
                if (!flag) {
                    throw new BizException("文件上传失败");
                }
            }
            if (config.getFtpType().equals(2)) {
                SFTPUtil sftp = new SFTPUtil(config.getFtpUsername(),config.getFtpPassword(), config.getFtpHost(),config.getFtpPort());
                sftp.login();
                sftp.upload(config.getFtpBaseDir(),path, fileName, inputStream);
                sftp.logout();
            }
        } catch (Exception e){
            e.printStackTrace();
            throw new BizException("上传文件失败，请检查配置信息", e);
        }

        return config.getFtpDomain() + "/" + path+"/"+fileName;
    }

    @Override
    public String uploadSuffix(byte[] data, String suffix) {
        return upload(data,config.getFtpPrefix()+"/"+ DateUtils.format(new Date(), "yyyyMMdd"), getFileName(suffix));
    }

    @Override
    public String uploadSuffix(InputStream inputStream, String suffix) {
        return upload(inputStream,config.getFtpPrefix()+"/"+ DateUtils.format(new Date(), "yyyyMMdd"), getFileName(suffix));
    }

    public String getFileName(String suffix){
         String uuid = UUID.randomUUID().toString().replaceAll("-", "");
        return uuid + suffix;
    }

}
