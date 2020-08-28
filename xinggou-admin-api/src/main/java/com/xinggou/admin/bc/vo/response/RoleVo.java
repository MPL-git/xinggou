package com.xinggou.admin.bc.vo.response;

import com.fasterxml.jackson.annotation.JsonFormat;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.util.Date;

/**
 * @author luoyb
 * Created on 2020/8/18
 */
@Data
public class RoleVo {

    @ApiModelProperty("角色ID")
    private Long id;
    @ApiModelProperty("角色名称")
    private String name;
    @ApiModelProperty("备注")
    private String remarks;
    @ApiModelProperty("创建日期，格式：yyyy-MM-dd HH:mm:ss")
    @JsonFormat(locale = "zh", timezone = "GMT+8", pattern = "yyyy-MM-dd HH:mm:ss")
    private Date createDate;
    @ApiModelProperty("已做关联的员工")
    private String assignedStaffs;
}
