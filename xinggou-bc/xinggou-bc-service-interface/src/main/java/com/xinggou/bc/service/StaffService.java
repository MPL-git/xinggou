package com.xinggou.bc.service;

import com.xinggou.bc.bean.dto.RoleDTO;
import com.xinggou.bc.bean.dto.StaffDTO;
import com.xinggou.bc.bean.params.staff.CreateStaffParams;
import com.xinggou.bc.bean.params.PaginateRoleForStaffParams;
import com.xinggou.bc.bean.params.staff.PaginateStaffParams;
import com.xinggou.bc.bean.params.staff.UpdateStaffParams;
import com.xinggou.bc.entity.Staff;
import com.xinggou.common.vo.PageWrap;

import java.util.List;

/**
 * @author luoyb
 * Created on 2020/8/17
 */
public interface StaffService {

    PageWrap<StaffDTO> findStaff(PaginateStaffParams params);

    StaffDTO getStaffDetail(Long staffId);

    Staff createStaff(CreateStaffParams params);

    Staff updateStaff(UpdateStaffParams params);

    void bulkDeleteStaff(List<Long> staffIds);

    void disabledStaffById(Long staffId);

    void activeStaffById(Long staffId);

    void updatePassword(Long staffId, String password);

    void resetPassword(Long staffId);

    /**
     * 查询角色列表：用于员工角色绑定
     */
    PageWrap<RoleDTO> paginateRoleForStaff(PaginateRoleForStaffParams params);

    void addStaffRole(Long staffId, List<Long> roleIdList);

    void removeStaffRole(Long staffId, List<Long> roleIdList);

}
