package com.xinggou.admin.bc.vo.response;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.xinggou.bc.enums.ConfigStatusEnum;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.util.Date;

/**
 * @author luoyb
 * Created on 2020/8/19
 */
@Data
public class ConfigVo {

    @ApiModelProperty("ID")
    private Long id;
    @ApiModelProperty("名称")
    private String name;
    @ApiModelProperty("键")
    private String paramKey;
    @ApiModelProperty("值")
    private String paramValue;
    @ApiModelProperty("状态：10显示 20隐藏")
    private ConfigStatusEnum status;
    @ApiModelProperty("排序")
    private Integer seqNo;
    @ApiModelProperty("创建日期，格式：yyyy-MM-dd HH:mm:ss")
    @JsonFormat(locale = "zh", timezone = "GMT+8", pattern = "yyyy-MM-dd HH:mm:ss")
    private Date createDate;
    @ApiModelProperty("备注")
    private String remarks;

}
