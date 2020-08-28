package com.xinggou.admin.common.vo.request;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import javax.validation.constraints.NotEmpty;
import java.util.List;

/**
 * @author luoyb
 * Created on 2020/8/17
 */
@Data
public class LongIdsRequest {

    @ApiModelProperty(value = "ID集合", required = true)
    @NotEmpty(message = "ID集合不能为空")
    private List<Long> ids;
}
