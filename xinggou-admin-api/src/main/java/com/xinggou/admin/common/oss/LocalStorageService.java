package com.xinggou.admin.common.oss;

import com.xinggou.common.exception.BizException;
import com.xinggou.common.utils.DateUtils;
import com.xinggou.rc.bean.CloudStorageConfig;
import org.apache.commons.lang.StringUtils;

import java.io.ByteArrayInputStream;
import java.io.File;
import java.io.FileOutputStream;
import java.io.InputStream;
import java.util.Date;
import java.util.UUID;

/**
 * 阿里云存储
 *
 *
 */
public class LocalStorageService extends CloudStorageService {

    public LocalStorageService(CloudStorageConfig config){
        this.config = config;
    }


    @Override
    public String upload(byte[] data, String path) {
        return upload(new ByteArrayInputStream(data), path);
    }

    @Override
    public String upload(InputStream inputStream, String path) {
        try {
            FileOutputStream fos = new FileOutputStream(config.getLocalPath() + "/"+path);
            byte[] b = new byte[1024];
            while ((inputStream.read(b)) != -1) {
                fos.write(b);// 写入数据
            }
            inputStream.close();
            fos.close();// 保存数据
        } catch (Exception e){
            e.printStackTrace();
            throw new BizException("上传文件失败，请检查配置信息", e);
        }

        return config.getLocalDomain() + "/" + path;
    }

    @Override
    public String uploadSuffix(byte[] data, String suffix) {
        return upload(data, getPath(config.getLocalPrefix(), suffix));
    }

    @Override
    public String uploadSuffix(InputStream inputStream, String suffix) {
        return upload(inputStream, getPath(config.getLocalPrefix(), suffix));
    }

    /**
     * 文件路径
     * @param prefix 前缀
     * @param suffix 后缀
     * @return 返回上传路径
     */
    @Override
    public String getPath(String prefix, String suffix) {
        //生成uuid
        String uuid = UUID.randomUUID().toString().replaceAll("-", "");
        //文件路径
        String path = DateUtils.format(new Date(), "yyyyMMdd");

        if(StringUtils.isNotBlank(prefix)){
            path = prefix + "/" + path;
        }

        File dir = new File(config.getLocalPath()+"/"+path);
        if (!dir.exists()&&!dir.isDirectory()) {
            dir.mkdir();
        }

        //拼上文件名
        path = path + "/" + uuid;

        return path + suffix;
    }

}
