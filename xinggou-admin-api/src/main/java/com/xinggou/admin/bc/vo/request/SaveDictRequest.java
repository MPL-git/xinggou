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
public class SaveDictRequest {

    @ApiModelProperty("ID")
    private Long id;

    @ApiModelProperty(value = "字典名称", required = true)
    @NotBlank(message = "字典名称不能为空")
    @Length(max = 100, message = "字典名称长度不能超过100个字")
    private String name;

    @ApiModelProperty(value = "字典类型",required = true)
    @NotBlank(message = "字典类型不能为空")
    @Length(max = 100, message = "字典类型长度不能超过100个字符")
    private String type;

    @ApiModelProperty("字典码")
    private String code;

    @ApiModelProperty("字典值")
    private String value;

    @ApiModelProperty(value = "排序",required = true)
    @NotNull(message = "排序不能为空")
    private Integer seqNo;

    @ApiModelProperty("备注")
    private String remarks;


}
