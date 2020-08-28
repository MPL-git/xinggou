package com.xinggou.admin.common.vo.request;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import javax.validation.constraints.NotNull;

/**
 * @author luoyb
 * Created on 2020/8/15
 */
@Data
public class LongIdRequest {

    @NotNull(message = "ID不能为空")
    @ApiModelProperty(value = "ID",required = true)
    private Long id;

}
