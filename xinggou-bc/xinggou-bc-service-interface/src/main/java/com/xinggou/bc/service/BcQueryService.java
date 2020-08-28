package com.xinggou.bc.service;

import com.xinggou.bc.bean.dto.MenuDTO;
import com.xinggou.bc.bean.dto.RoleDTO;
import com.xinggou.bc.bean.dto.StaffLogOperateDTO;
import com.xinggou.bc.bean.params.PaginateConfigParams;
import com.xinggou.bc.bean.params.PaginateDictParams;
import com.xinggou.bc.bean.params.PaginateRoleParams;
import com.xinggou.bc.bean.params.staff.PaginateStaffLogLoginParams;
import com.xinggou.bc.bean.params.staff.PaginateStaffLogOperateParams;
import com.xinggou.bc.entity.SetConfig;
import com.xinggou.bc.entity.SetDict;
import com.xinggou.bc.entity.SetRole;
import com.xinggou.bc.entity.Staff;
import com.xinggou.bc.entity.StaffLogLogin;
import com.xinggou.common.vo.PageWrap;

public interface BcQueryService {

    // 系统用户接口
    Staff findStaffById(Long id);
    Staff findStaffByUserName(String username);

    PageWrap<StaffLogLogin> paginateStaffLogLogin(PaginateStaffLogLoginParams params);
    PageWrap<StaffLogOperateDTO> paginateStaffLogOperate(PaginateStaffLogOperateParams params);

    // 角色接口
    SetRole getRoleById(Long roleId);
    PageWrap<RoleDTO> paginateRole(PaginateRoleParams params);

    // 菜单接口
    MenuDTO getMenuById(Long menuId);

    // 系统设置接口
    SetConfig getConfigById(Long id);
    SetConfig findConfigByKey(String key);
    PageWrap<SetConfig> paginateConfig(PaginateConfigParams params);

    // 数据字典
    SetDict getDictById(Long id);
    PageWrap<SetDict> paginateDict(PaginateDictParams params);

}