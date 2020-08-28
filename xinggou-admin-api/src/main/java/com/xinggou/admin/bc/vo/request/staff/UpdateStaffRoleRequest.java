package com.xinggou.admin.bc.vo.request.staff;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import javax.validation.constraints.NotNull;
import java.util.List;

/**
 * @author luoyb
 * Created on 2020/8/18
 */
@Data
public class UpdateStaffRoleRequest {

    @ApiModelProperty(value = "员工ID", required = true)
    @NotNull(message = "员工ID不能为空")
    private Long staffId;

    @ApiModelProperty(value = "角色ID集合", required = true)
    @NotNull(message = "角色ID集合不能为空")
    private List<Long> roleIdList;

}
