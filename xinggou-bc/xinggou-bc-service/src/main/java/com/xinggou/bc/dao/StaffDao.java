package com.xinggou.bc.dao;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.xinggou.bc.bean.dto.StaffDTO;
import com.xinggou.bc.bean.params.staff.PaginateStaffParams;
import com.xinggou.bc.entity.Staff;
import org.apache.ibatis.annotations.Mapper;

import java.util.List;

/**
 * 系统用户
 * 
 * @author hdl
 * @date 2020-08-06 11:43:33
 */
@Mapper
public interface StaffDao extends BaseMapper<Staff> {

    int countStaff(PaginateStaffParams params);

    List<StaffDTO> findStaff(PaginateStaffParams params);

    List<String> listStaffPerm(Long staffId);

}
