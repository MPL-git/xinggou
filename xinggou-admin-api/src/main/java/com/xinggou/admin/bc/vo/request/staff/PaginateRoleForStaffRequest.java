package com.xinggou.admin.bc.vo.request.staff;

import com.xinggou.admin.common.vo.request.PageRequest;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import org.hibernate.validator.constraints.Range;

import javax.validation.constraints.NotNull;

/**
 * @author luoyb
 * Created on 2020/8/18
 */
@Data
public class PaginateRoleForStaffRequest extends PageRequest {

    @ApiModelProperty(value = "员工ID", required = true)
    @NotNull(message = "员工ID不能为空")
    private Long staffId;
    @ApiModelProperty("角色名称")
    private String roleName;
    @ApiModelProperty(value = "查询类型：0全部 1已绑定 2未绑定", required = true)
    @Range(min = 0, max = 2, message = "查询类型错误")
    @NotNull(message = "查询类型不能为空")
    private Integer boundType;

}
