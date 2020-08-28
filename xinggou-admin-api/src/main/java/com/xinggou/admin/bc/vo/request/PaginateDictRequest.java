package com.xinggou.admin.bc.vo.request;

import com.xinggou.admin.common.vo.request.PageRequest;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

/**
 * @author luoyb
 * Created on 2020/8/19
 */
@Data
public class PaginateDictRequest extends PageRequest {

    @ApiModelProperty("字典名称")
    private String name;

    @ApiModelProperty("字典类型")
    private String type;

}
