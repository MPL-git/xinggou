package com.xinggou.admin.bc.vo.request;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import org.hibernate.validator.constraints.Length;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;

/**
 * @author luoyb
 * Created on 2020/8/19
 */
@Data
public class SaveConfigRequest {

    @ApiModelProperty("ID")
    private Long id;
    @ApiModelProperty(value = "名称", required = true)
    @NotBlank(message = "名称不能为空")
    @Length(max = 64, message = "名称不能超过64个字")
    private String name;
    @ApiModelProperty("键名")
    @NotBlank(message = "键名不能为空")
    @Length(max = 50,message = "键名不能超过50个字符")
    private String paramKey;
    @ApiModelProperty("值")
    @NotBlank(message = "值不能为空")
    @Length(max = 2000,message = "值不能超过2000个字符")
    private String paramValue;
    @ApiModelProperty(value = "排序", required = true)
    @NotNull(message = "排序值不能为空")
    private Integer seqNo;
    @ApiModelProperty("备注")
    private String remarks;

}
