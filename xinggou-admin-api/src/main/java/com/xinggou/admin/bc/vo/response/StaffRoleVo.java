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
public class StaffRoleVo {

    @ApiModelProperty("角色ID")
    private Long id;
    @ApiModelProperty("角色名称")
    private String name;
    @ApiModelProperty("创建时间，格式：yyyy-MM-dd HH:mm:ss")
    @JsonFormat(locale = "zh", timezone = "GMT+8", pattern = "yyyy-MM-dd HH:mm:ss")
    private Date createDate;
    @ApiModelProperty("备注")
    private String remarks;

    @ApiModelProperty("是否已绑定")
    private boolean bound;
}
