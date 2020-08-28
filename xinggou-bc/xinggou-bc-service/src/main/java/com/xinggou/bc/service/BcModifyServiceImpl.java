package com.xinggou.bc.service;

import com.xinggou.bc.bean.CurrentStaff;
import com.xinggou.bc.bean.params.CreateMenuParams;
import com.xinggou.bc.bean.params.staff.CreateStaffLogOperateParams;
import com.xinggou.bc.bean.params.SaveConfigParams;
import com.xinggou.bc.bean.params.SaveDictParams;
import com.xinggou.bc.bean.params.SaveRoleParams;
import com.xinggou.bc.bean.params.UpdateMenuParams;
import com.xinggou.bc.biz.SetConfigBiz;
import com.xinggou.bc.biz.SetDictBiz;
import com.xinggou.bc.biz.SetMenuBiz;
import com.xinggou.bc.biz.SetRoleBiz;
import com.xinggou.bc.biz.StaffBiz;
import com.xinggou.bc.biz.StaffLogLoginBiz;
import com.xinggou.bc.biz.StaffLogOperateBiz;
import com.xinggou.bc.entity.SetConfig;
import com.xinggou.bc.entity.SetDict;
import com.xinggou.bc.entity.SetMenu;
import com.xinggou.bc.entity.Staff;
import com.xinggou.bc.entity.StaffLogLogin;
import com.xinggou.bc.enums.StaffStatusEnum;
import com.xinggou.common.exception.BizException;
import org.apache.dubbo.config.annotation.Service;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.List;

@Service
public class BcModifyServiceImpl implements BcModifyService {

    @Autowired
    private StaffBiz staffBiz;
    @Autowired
    private SetRoleBiz setRoleBiz;
    @Autowired
    private SetMenuBiz setMenuBiz;
    @Autowired
    private SetConfigBiz setConfigBiz;
    @Autowired
    private SetDictBiz setDictBiz;
    @Autowired
    private StaffLogLoginBiz staffLogLoginBiz;
    @Autowired
    private StaffLogOperateBiz staffLogOperateBiz;


    @Override
    public CurrentStaff login(String userName, String password, String ip) throws BizException {
        Staff staff = staffBiz.findByPassword(userName, password);
        if (staff == null) {
            throw new BizException("账号或密码错误");
        }

        if (StaffStatusEnum.PROHIBIT == staff.getStatus()) {
            throw new BizException("用户被禁用");
        }

        return staffBiz.login(staff, ip);
    }

    @Override
    public void logout(String token) throws BizException {
        staffBiz.logout(token);
    }

    @Override
    public StaffLogLogin createStaffLogLogin(long staffId, String ip) {
        StaffLogLogin staffLogLogin = new StaffLogLogin();
        staffLogLogin.setStaffId(staffId);
        staffLogLogin.setIp(ip);
        staffLogLoginBiz.save(staffLogLogin);
        return staffLogLogin;
    }

    @Override
    public void createStaffLogOperate(CreateStaffLogOperateParams params) {
        staffLogOperateBiz.createOperateLog(params);
    }

    @Override
    public void saveRole(SaveRoleParams params) {
        if (params.getRoleId() == null) {
            setRoleBiz.createRole(params);
        } else {
            setRoleBiz.updateRole(params);
        }
    }

    @Override
    public void deleteRole(List<Long> idList) {
        for (Long id : idList) {
            setRoleBiz.deleteById(id);
        }

    }

    @Override
    public SetMenu createMenu(CreateMenuParams params) {
        return setMenuBiz.createMenu(params);
    }

    @Override
    public SetMenu updateMenu(UpdateMenuParams params) {
        return setMenuBiz.updateMenu(params);
    }

    @Override
    public void deleteMenu(Long menuId) {
        setMenuBiz.deleteById(menuId);
    }

    @Override
    public SetConfig saveConfig(SaveConfigParams params) {
        return setConfigBiz.saveInfo(params);
    }

    @Override
    public void deleteConfig(List<Long> idList) {
        setConfigBiz.removeByIds(idList);
    }

    @Override
    public void saveConfigByKey(SaveConfigParams params) {
        setConfigBiz.saveConfigByKey(params);
    }

    @Override
    public SetDict saveDict(SaveDictParams params) {
        return setDictBiz.saveDict(params);
    }

    @Override
    public void deleteDict(List<Long> idList) {
        setDictBiz.removeByIds(idList);
    }
}
