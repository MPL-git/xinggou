package com.xinggou.admin.bc.vo.request;

import com.xinggou.bc.enums.MenuTypeEnum;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import org.hibernate.validator.constraints.Length;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;

/**
 * @author luoyb
 * Created on 2020/8/18
 */
@Data
public class CreateMenuRequest {

    @ApiModelProperty(value = "父级ID", required = true)
    @NotNull(message = "父级ID不能为空")
    private Long parentId;
    @ApiModelProperty(value = "名称", required = true)
    @NotBlank(message = "名称不能为空")
    @Length(max = 50, message = "名称不能超过50个字")
    private String name;
    @ApiModelProperty(value = "菜单类型：10：目录 20：菜单 30：按钮", required = true)
    @NotNull(message = "菜单类型不能为空")
    private MenuTypeEnum type;
    @ApiModelProperty("菜单图标")
    private String icon;
    @ApiModelProperty("菜单url")
    private String url;
    @ApiModelProperty("授权(多个用逗号分隔，如：user:list,user:create)")
    private String perms;
    @ApiModelProperty(value = "排序", required = true)
    @NotNull(message = "排序值不能为空")
    private Integer seqNo;

}
