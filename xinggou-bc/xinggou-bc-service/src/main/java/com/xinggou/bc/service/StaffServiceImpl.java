package com.xinggou.bc.service;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.google.common.collect.Lists;
import com.xinggou.bc.bean.dto.RoleDTO;
import com.xinggou.bc.bean.dto.StaffDTO;
import com.xinggou.bc.bean.params.PaginateRoleForStaffParams;
import com.xinggou.bc.bean.params.staff.CreateStaffParams;
import com.xinggou.bc.bean.params.staff.PaginateStaffParams;
import com.xinggou.bc.bean.params.staff.UpdateStaffParams;
import com.xinggou.bc.biz.DeptBiz;
import com.xinggou.bc.biz.SetRoleBiz;
import com.xinggou.bc.biz.StaffBiz;
import com.xinggou.bc.biz.StaffRoleBiz;
import com.xinggou.bc.constant.BcConst;
import com.xinggou.bc.converter.BcBizConverter;
import com.xinggou.bc.entity.Dept;
import com.xinggou.bc.entity.SetRole;
import com.xinggou.bc.entity.Staff;
import com.xinggou.common.exception.BizException;
import com.xinggou.common.vo.PageWrap;
import org.apache.dubbo.config.annotation.Service;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

/**
 * @author luoyb
 * Created on 2020/8/17
 */
@Service
public class StaffServiceImpl implements StaffService {

    @Autowired
    private StaffBiz staffBiz;
    @Autowired
    private DeptBiz deptBiz;
    @Autowired
    private StaffRoleBiz staffRoleBiz;
    @Autowired
    private SetRoleBiz setRoleBiz;

    @Override
    public PageWrap<StaffDTO> findStaff(PaginateStaffParams params) {
        return staffBiz.findStaff(params);
    }

    @Override
    public StaffDTO getStaffDetail(Long staffId) {
        Staff staff = staffBiz.getById(staffId);
        StaffDTO dto = BcBizConverter.INSTANCE.toStaffDTO(staff);
        Dept dept = deptBiz.getById(staff.getDeptId());
        if (dept != null) {
            dto.setDeptName(dept.getName());
        }
        return dto;
    }

    @Override
    @Transactional
    public Staff createStaff(CreateStaffParams params) {
        if (staffBiz.findByUserName(params.getUserName()) != null) {
            throw new BizException("用户名已经存在");
        }
        Dept dept = deptBiz.getById(params.getDeptId());
        if (dept == null) {
            throw new BizException("部门不存在");
        }

        return staffBiz.createStaff(params);
    }

    @Override
    @Transactional
    public Staff updateStaff(UpdateStaffParams params) {
        return staffBiz.updateStaff(params);
    }

    @Override
    @Transactional
    public void bulkDeleteStaff(List<Long> staffIds) {
        staffBiz.removeByIds(staffIds);

        //删除用户与角色Mapping
        staffRoleBiz.deleteByStaffIds(staffIds);
    }

    @Override
    public void disabledStaffById(Long staffId) {
        staffBiz.disabledById(staffId);
    }

    @Override
    public void activeStaffById(Long staffId) {
        staffBiz.activeById(staffId);
    }

    @Override
    public void updatePassword(Long staffId, String password) {
        staffBiz.updatePassword(staffId, password);
    }

    @Override
    public void resetPassword(Long staffId) {
        staffBiz.updatePassword(staffId, BcConst.DEFAULT_STAFF_PASSWORD);
    }

    @Override
    public PageWrap<RoleDTO> paginateRoleForStaff(PaginateRoleForStaffParams params) {
        Set<Long> staffRoleIdSet = staffRoleBiz.findStaffRoleId(params.getStaffId());
        params.setStaffRoleIdList(Lists.newArrayList(staffRoleIdSet));
        IPage<SetRole> iPage = setRoleBiz.findRoleForStaff(params);

        List<RoleDTO> list = iPage.getRecords().stream().map(role -> {
            RoleDTO dto = BcBizConverter.INSTANCE.toRoleDTO(role);
            dto.setBound(staffRoleIdSet.contains(role.getId()));
            return dto;
        }).collect(Collectors.toList());
        return PageWrap.of(list, iPage);
    }

    @Override
    @Transactional
    public void addStaffRole(Long staffId, List<Long> roleIdList) {
        Set<Long> staffRoleIdSet = staffRoleBiz.findStaffRoleId(staffId);
        for (Long roleId : roleIdList) {
            if (staffRoleIdSet.contains(roleId)) {
                throw new BizException("不能重复添加角色");
            }
        }
        staffRoleBiz.bulkAddRole(staffId, roleIdList);
    }

    @Override
    @Transactional
    public void removeStaffRole(Long staffId, List<Long> roleIdList) {
        staffRoleBiz.deleteByStaffRole(staffId, roleIdList);
    }

}
