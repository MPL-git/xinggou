package com.xinggou.admin.rc.vo.request;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.xinggou.admin.common.vo.request.PageRequest;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.util.Date;

/**
 * @author luoyb
 * Created on 2020/8/20
 */
@Data
public class PaginateOssFileRequest extends PageRequest {

    @ApiModelProperty("开始时间")
    @JsonFormat(locale = "zh", timezone = "GMT+8", pattern = "yyyy-MM-dd")
    private Date beginDate;

    @ApiModelProperty("结束时间")
    @JsonFormat(locale = "zh", timezone = "GMT+8", pattern = "yyyy-MM-dd")
    private Date endDate;

}
