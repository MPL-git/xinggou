package com.xinggou.admin.bc.vo.response;

import com.fasterxml.jackson.annotation.JsonFormat;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.util.Date;

/**
 * @author luoyb
 * Created on 2020/8/19
 */
@Data
public class StaffLogOperateVo {

    @ApiModelProperty("员工ID")
    private Long staffId;
    @ApiModelProperty("员工姓名")
    private String staffName;
    @ApiModelProperty("用户操作")
    private String operation;
    @ApiModelProperty("请求方法")
    private String method;
    @ApiModelProperty("请求参数")
    private String params;
    @ApiModelProperty("执行时长(毫秒)")
    private Long time;
    @ApiModelProperty("IP地址")
    private String ip;
    @ApiModelProperty("创建日期，格式：yyyy-MM-dd HH:mm:ss")
    @JsonFormat(locale = "zh", timezone = "GMT+8", pattern = "yyyy-MM-dd HH:mm:ss")
    private Date createDate;

}
