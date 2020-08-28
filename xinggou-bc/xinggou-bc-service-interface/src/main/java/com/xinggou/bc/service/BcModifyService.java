package com.xinggou.bc.service;

import com.xinggou.bc.bean.CurrentStaff;
import com.xinggou.bc.bean.params.CreateMenuParams;
import com.xinggou.bc.bean.params.staff.CreateStaffLogOperateParams;
import com.xinggou.bc.bean.params.SaveConfigParams;
import com.xinggou.bc.bean.params.SaveDictParams;
import com.xinggou.bc.bean.params.SaveRoleParams;
import com.xinggou.bc.bean.params.UpdateMenuParams;
import com.xinggou.bc.entity.SetConfig;
import com.xinggou.bc.entity.SetDict;
import com.xinggou.bc.entity.SetMenu;
import com.xinggou.bc.entity.StaffLogLogin;
import com.xinggou.common.exception.BizException;

import java.util.List;

public interface BcModifyService {

    // 用户登录接口
    CurrentStaff login(String userName, String password, String ip) throws BizException;

    /**
     * 登出
     *
     * @param token token
     */
    void logout(String token) throws BizException;

    StaffLogLogin createStaffLogLogin(long staffId, String ip);

    void createStaffLogOperate(CreateStaffLogOperateParams params);

    void saveRole(SaveRoleParams params);
    void deleteRole(List<Long> idList);

    // 菜单接口
    SetMenu createMenu(CreateMenuParams params);
    SetMenu updateMenu(UpdateMenuParams params);
    void deleteMenu(Long menuId);

    // 系统设置接口
    SetConfig saveConfig(SaveConfigParams params);
    void deleteConfig(List<Long> idList);

    /**
     * 根据key值新增或修改配置
     */
    void saveConfigByKey(SaveConfigParams params);

    // 数据字典
    SetDict saveDict(SaveDictParams params);
    void deleteDict(List<Long> idList);

}