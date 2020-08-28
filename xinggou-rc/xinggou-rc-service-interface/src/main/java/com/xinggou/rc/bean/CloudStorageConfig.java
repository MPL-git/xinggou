

package com.xinggou.rc.bean;


import com.xinggou.common.utils.validator.group.AliyunGroup;
import com.xinggou.common.utils.validator.group.FtpGroup;
import com.xinggou.common.utils.validator.group.LocalGroup;
import com.xinggou.common.utils.validator.group.QcloudGroup;
import com.xinggou.common.utils.validator.group.QiniuGroup;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import org.hibernate.validator.constraints.Range;
import org.hibernate.validator.constraints.URL;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import java.io.Serializable;

/**
 * 云存储配置信息
 */
@Data
public class CloudStorageConfig implements Serializable {
    private static final long serialVersionUID = 1L;

    @ApiModelProperty("存储类型 1：七牛  2：阿里云  3：腾讯云 4:本地存储 5:FTP")
    @Range(min = 1, max = 5, message = "存储类型错误")
    private Integer type;

    @ApiModelProperty("七牛绑定的域名")
    @NotBlank(message = "七牛绑定的域名不能为空", groups = QiniuGroup.class)
    @URL(message = "七牛绑定的域名格式不正确", groups = QiniuGroup.class)
    private String qiniuDomain;
    @ApiModelProperty("七牛绑定的服务前缀")
    private String qiniuPrefix;
    @ApiModelProperty("七牛AccessKey")
    @NotBlank(message = "七牛AccessKey不能为空", groups = QiniuGroup.class)
    private String qiniuAccessKey;
    @ApiModelProperty("七牛SecreKey")
    @NotBlank(message = "七牛SecretKey不能为空", groups = QiniuGroup.class)
    private String qiniuSecretKey;
    @ApiModelProperty("七牛空间名")
    @NotBlank(message = "七牛空间名不能为空", groups = QiniuGroup.class)
    private String qiniuBucketName;

    @ApiModelProperty("阿里云绑定的域名")
    @NotBlank(message = "阿里云绑定的域名不能为空", groups = AliyunGroup.class)
    @URL(message = "阿里云绑定的域名格式不正确", groups = AliyunGroup.class)
    private String aliyunDomain;
    @ApiModelProperty("阿里云的服务前缀")
    private String aliyunPrefix;
    @ApiModelProperty("阿里云EndPoint")
    @NotBlank(message = "阿里云EndPoint不能为空", groups = AliyunGroup.class)
    private String aliyunEndPoint;
    @ApiModelProperty("阿里云AccessKeyId")
    @NotBlank(message = "阿里云AccessKeyId不能为空", groups = AliyunGroup.class)
    private String aliyunAccessKeyId;
    @ApiModelProperty("阿里云AccessKeySecret")
    @NotBlank(message = "阿里云AccessKeySecret不能为空", groups = AliyunGroup.class)
    private String aliyunAccessKeySecret;
    @ApiModelProperty("阿里云BucketName")
    @NotBlank(message = "阿里云BucketName不能为空", groups = AliyunGroup.class)
    private String aliyunBucketName;

    @ApiModelProperty("腾讯云绑定的域名")
    @NotBlank(message = "腾讯云绑定的域名不能为空", groups = QcloudGroup.class)
    @URL(message = "腾讯云绑定的域名格式不正确", groups = QcloudGroup.class)
    private String qcloudDomain;
    @ApiModelProperty("腾讯云绑定的服务前缀")
    private String qcloudPrefix;
    @ApiModelProperty("腾讯云AppId")
    @NotNull(message = "腾讯云AppId不能为空", groups = QcloudGroup.class)
    private Integer qcloudAppId;
    @ApiModelProperty("腾讯云SecretId")
    @NotBlank(message = "腾讯云SecretId不能为空", groups = QcloudGroup.class)
    private String qcloudSecretId;
    @ApiModelProperty("腾讯云SecretKey")
    @NotBlank(message = "腾讯云SecretKey不能为空", groups = QcloudGroup.class)
    private String qcloudSecretKey;
    @ApiModelProperty("腾讯云BucketName")
    @NotBlank(message = "腾讯云BucketName不能为空", groups = QcloudGroup.class)
    private String qcloudBucketName;
    @ApiModelProperty("腾讯云所属地区")
    @NotBlank(message = "腾讯云所属地区不能为空", groups = QcloudGroup.class)
    private String qcloudRegion;

    //本地存储配置
    @ApiModelProperty("本地访问域名")
    @NotBlank(message = "本地访问域名不能为空", groups = LocalGroup.class)
    @URL(message = "访问域名格式不正确", groups = LocalGroup.class)
    private String localDomain;
    @ApiModelProperty("本地的服务前缀")
    private String localPrefix;
    @ApiModelProperty("本地存储路径")
    @NotBlank(message = "本地存储路径不能为空", groups = LocalGroup.class)
    private String localPath;

    //FTP配置
    @ApiModelProperty("FTP主机")
    @NotBlank(message = "FTP主机不能为空", groups = FtpGroup.class)
    private String ftpHost;
    @ApiModelProperty("FTP端口号")
    @NotNull(message = "FTP端口号不能为空", groups = FtpGroup.class)
    private Integer ftpPort;
    @ApiModelProperty("FTP用户名")
    @NotBlank(message = "FTP用户名不能为空", groups = FtpGroup.class)
    private String ftpUsername;
    @ApiModelProperty("FTP密码")
    @NotBlank(message = "FTP密码不能为空", groups = FtpGroup.class)
    private String ftpPassword;
    @ApiModelProperty("FTP类型")
    @NotNull(message = "FTP类型不能为空", groups = FtpGroup.class)
    private Integer ftpType;
    @ApiModelProperty("FTP访问域名")
    @NotBlank(message = "FTP访问域名不能为空", groups = FtpGroup.class)
    @URL(message = "FTP访问域名格式不正确", groups = FtpGroup.class)
    private String ftpDomain;
    @ApiModelProperty("FTP服务前缀")
    private String ftpPrefix;
    @ApiModelProperty("FTP上传根路径")
    @NotBlank(message = "FTP上传根路径不能为空", groups = FtpGroup.class)
    private String ftpBaseDir;


}
