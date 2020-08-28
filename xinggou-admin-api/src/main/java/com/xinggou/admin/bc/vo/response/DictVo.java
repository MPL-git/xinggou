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
public class DictVo {

    @ApiModelProperty("ID")
    private Long id;

    @ApiModelProperty("字典名称")
    private String name;

    @ApiModelProperty("字典类型")
    private String type;

    @ApiModelProperty("字典码")
    private String code;

    @ApiModelProperty("字典值")
    private String value;

    @ApiModelProperty("排序")
    private Integer seqNo;

    @ApiModelProperty("创建日期，格式：yyyy-MM-dd HH:mm:ss")
    @JsonFormat(locale = "zh", timezone = "GMT+8", pattern = "yyyy-MM-dd HH:mm:ss")
    private Date createDate;

    @ApiModelProperty("备注")
    private String remarks;

}
