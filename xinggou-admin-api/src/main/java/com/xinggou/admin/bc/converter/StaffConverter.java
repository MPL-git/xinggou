package com.xinggou.admin.bc.converter;

import com.xinggou.admin.bc.vo.request.staff.CreateStaffRequest;
import com.xinggou.admin.bc.vo.request.staff.PaginateRoleForStaffRequest;
import com.xinggou.admin.bc.vo.request.staff.PaginateStaffRequest;
import com.xinggou.admin.bc.vo.request.staff.UpdateStaffRequest;
import com.xinggou.admin.bc.vo.response.CurrentStaffVo;
import com.xinggou.admin.bc.vo.response.StaffRoleVo;
import com.xinggou.admin.bc.vo.response.StaffVo;
import com.xinggou.bc.bean.dto.RoleDTO;
import com.xinggou.bc.bean.dto.StaffDTO;
import com.xinggou.bc.bean.params.PaginateRoleForStaffParams;
import com.xinggou.bc.bean.params.staff.CreateStaffParams;
import com.xinggou.bc.bean.params.staff.PaginateStaffParams;
import com.xinggou.bc.bean.params.staff.UpdateStaffParams;
import com.xinggou.common.vo.PageWrap;
import org.mapstruct.InheritConfiguration;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.factory.Mappers;

/**
 * @author luoyb
 * Created on 2020/8/15
 */
@Mapper
public interface StaffConverter {
    StaffConverter INSTANCE = Mappers.getMapper(StaffConverter.class);

    CreateStaffParams toCreateStaffParams(CreateStaffRequest request);

    @Mapping(target = "gender", defaultValue = "UNKNOWN")
    UpdateStaffParams toUpdateStaffParams(UpdateStaffRequest request);

    PaginateStaffParams toPaginateStaffParams(PaginateStaffRequest request);

    @InheritConfiguration(name = "toStaffVo")
    PageWrap<StaffVo> toStaffVoPageWrap(PageWrap<StaffDTO> pageWrap);

    @Mapping(target = "statusDesc", expression = "java(staff.getStatus().getDisplayName())")
    @Mapping(target = "genderDesc", expression = "java(staff.getGender().getDisplayName())")
    @Mapping(target = "positionStatusDesc", expression = "java(staff.getPositionStatus().getDisplayName())")
    StaffVo toStaffVo(StaffDTO staff);

    @Mapping(target = "genderDesc", expression = "java(staffDTO.getGender().getDisplayName())")
    CurrentStaffVo toCurrentStaffVo(StaffDTO staffDTO);

    @Mapping(target = "staffRoleIdList", ignore = true)
    PaginateRoleForStaffParams toPaginateRoleForStaffParams(PaginateRoleForStaffRequest request);

    @InheritConfiguration(name = "toStaffRoleVo")
    PageWrap<StaffRoleVo> toStaffRoleVoPageWrap(PageWrap<RoleDTO> pageWrap);

    StaffRoleVo toStaffRoleVo(RoleDTO dto);
}
