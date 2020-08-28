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
public class StaffLogLoginVo {

    @ApiModelProperty("ID")
    private Long id;

    @ApiModelProperty("平台工作人员ID")
    private Long staffId;

    @ApiModelProperty("来源ip")
    private String ip;

    @ApiModelProperty("创建日期，格式：yyyy-MM-dd HH:mm:ss")
    @JsonFormat(locale = "zh", timezone = "GMT+8", pattern = "yyyy-MM-dd HH:mm:ss")
    private Date createDate;
}
