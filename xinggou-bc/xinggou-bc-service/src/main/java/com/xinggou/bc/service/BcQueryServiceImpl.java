package com.xinggou.bc.service;

import com.xinggou.bc.bean.dto.MenuDTO;
import com.xinggou.bc.bean.dto.RoleDTO;
import com.xinggou.bc.bean.dto.StaffLogOperateDTO;
import com.xinggou.bc.bean.params.PaginateConfigParams;
import com.xinggou.bc.bean.params.PaginateDictParams;
import com.xinggou.bc.bean.params.PaginateRoleParams;
import com.xinggou.bc.bean.params.staff.PaginateStaffLogLoginParams;
import com.xinggou.bc.bean.params.staff.PaginateStaffLogOperateParams;
import com.xinggou.bc.biz.DeptBiz;
import com.xinggou.bc.biz.SetConfigBiz;
import com.xinggou.bc.biz.SetDictBiz;
import com.xinggou.bc.biz.SetMenuBiz;
import com.xinggou.bc.biz.SetRoleBiz;
import com.xinggou.bc.biz.StaffBiz;
import com.xinggou.bc.biz.StaffLogLoginBiz;
import com.xinggou.bc.biz.StaffLogOperateBiz;
import com.xinggou.bc.converter.BcBizConverter;
import com.xinggou.bc.entity.SetConfig;
import com.xinggou.bc.entity.SetDict;
import com.xinggou.bc.entity.SetMenu;
import com.xinggou.bc.entity.SetRole;
import com.xinggou.bc.entity.Staff;
import com.xinggou.bc.entity.StaffLogLogin;
import com.xinggou.common.vo.PageWrap;
import com.xinggou.rc.service.RcQueryService;
import org.apache.dubbo.config.annotation.Reference;
import org.apache.dubbo.config.annotation.Service;
import org.springframework.beans.factory.annotation.Autowired;

@Service
public class BcQueryServiceImpl implements BcQueryService {

    @Autowired
    private StaffBiz staffBiz;
    @Autowired
    private SetRoleBiz setRoleBiz;
    @Autowired
    private SetMenuBiz setMenuBiz;
    @Autowired
    private DeptBiz deptBiz;
    @Autowired
    private SetConfigBiz setConfigBiz;
    @Autowired
    private SetDictBiz setDictBiz;
    @Autowired
    private StaffLogLoginBiz staffLogLoginBiz;
    @Autowired
    private StaffLogOperateBiz staffLogOperateBiz;
    @Reference
    private RcQueryService rcQueryService;


    @Override
    public Staff findStaffById(Long id) {
        return staffBiz.getById(id);
    }

    @Override
    public Staff findStaffByUserName(String username) {
        return staffBiz.findByUserName(username);
    }

    @Override
    public PageWrap<StaffLogLogin> paginateStaffLogLogin(PaginateStaffLogLoginParams params) {
        return staffLogLoginBiz.findStaffLogLogin(params);
    }

    @Override
    public PageWrap<StaffLogOperateDTO> paginateStaffLogOperate(PaginateStaffLogOperateParams params) {
        return staffLogOperateBiz.findStaffLogOperate(params);
    }

    @Override
    public SetRole getRoleById(Long roleId) {
        return setRoleBiz.getById(roleId);
    }

    @Override
    public PageWrap<RoleDTO> paginateRole(PaginateRoleParams params) {
        return setRoleBiz.findRole(params);
    }

    @Override
    public MenuDTO getMenuById(Long menuId) {
        SetMenu menu = setMenuBiz.getById(menuId);
        MenuDTO dto = BcBizConverter.INSTANCE.toMenuDTO(menu);
        if (menu.getParentId() == 0) {
            dto.setParentName("一级菜单");
        }else{
            SetMenu parentMenu = setMenuBiz.getById(menu.getParentId());
            dto.setParentName(parentMenu.getName());
        }
        return dto;
    }

    @Override
    public SetConfig getConfigById(Long id) {
        return setConfigBiz.getById(id);
    }

    @Override
    public SetConfig findConfigByKey(String key) {
        return setConfigBiz.findByKey(key);
    }

    @Override
    public PageWrap<SetConfig> paginateConfig(PaginateConfigParams params) {
        return setConfigBiz.paginateConfig(params);
    }

    @Override
    public SetDict getDictById(Long id) {
        return setDictBiz.getById(id);
    }

    @Override
    public PageWrap<SetDict> paginateDict(PaginateDictParams params) {
        return setDictBiz.findDict(params);
    }

}
