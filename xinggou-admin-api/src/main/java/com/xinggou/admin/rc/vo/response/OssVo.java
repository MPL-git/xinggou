package com.xinggou.admin.rc.vo.response;

import com.fasterxml.jackson.annotation.JsonFormat;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.util.Date;

/**
 * @author luoyb
 * Created on 2020/8/20
 */
@Data
public class OssVo {

    @ApiModelProperty("ID")
    private Long id;
    @ApiModelProperty("地址")
    private String url;
    @ApiModelProperty("创建时间")
    @JsonFormat(locale = "zh", timezone = "GMT+8", pattern = "yyyy-MM-dd HH:mm:ss")
    private Date createDate;

}
