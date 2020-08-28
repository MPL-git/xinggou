package com.xinggou.bc.converter;

import com.xinggou.bc.bean.dto.MenuDTO;
import com.xinggou.bc.bean.dto.MenuNode;
import com.xinggou.bc.bean.dto.PermissionNode;
import com.xinggou.bc.bean.dto.RoleDTO;
import com.xinggou.bc.bean.dto.StaffDTO;
import com.xinggou.bc.bean.dto.StaffLogOperateDTO;
import com.xinggou.bc.bean.params.CreateDeptParams;
import com.xinggou.bc.bean.params.CreateMenuParams;
import com.xinggou.bc.bean.params.SaveConfigParams;
import com.xinggou.bc.bean.params.UpdateMenuParams;
import com.xinggou.bc.bean.params.staff.CreateStaffLogOperateParams;
import com.xinggou.bc.bean.params.staff.CreateStaffParams;
import com.xinggou.bc.bean.params.staff.UpdateStaffParams;
import com.xinggou.bc.entity.Dept;
import com.xinggou.bc.entity.SetConfig;
import com.xinggou.bc.entity.SetMenu;
import com.xinggou.bc.entity.SetRole;
import com.xinggou.bc.entity.Staff;
import com.xinggou.bc.entity.StaffLogOperate;
import org.mapstruct.InheritConfiguration;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.MappingTarget;
import org.mapstruct.factory.Mappers;

import java.util.List;

/**
 * @author luoyb
 * Created on 2020/8/20
 */
@Mapper
public interface BcBizConverter {
    BcBizConverter INSTANCE = Mappers.getMapper(BcBizConverter.class);

    StaffDTO toStaffDTO(Staff staff);

    Dept toDept(CreateDeptParams params);

    MenuDTO toMenuDTO(SetMenu menu);

    StaffLogOperate toStaffLogOperate(CreateStaffLogOperateParams params);

    StaffLogOperateDTO toStaffLogOperateDTO(StaffLogOperate log);

    SetConfig toConfig(SaveConfigParams params);

    SetMenu toSetMenu(CreateMenuParams params);

    void updateSetMenu(UpdateMenuParams params, @MappingTarget SetMenu menu);

    List<RoleDTO> toRoleDTOs(List<SetRole> records);

    Staff toStaff(CreateStaffParams params);

    void updateStaff(UpdateStaffParams params, @MappingTarget Staff staff);

    @InheritConfiguration(name = "toMenuNode")
    List<MenuNode> toMenuNodeList(List<SetMenu> list);

    @Mapping(target = "menuId", source = "id")
    MenuNode toMenuNode(SetMenu setMenu);

    @Mapping(target = "menuId", source = "id")
    @Mapping(target = "href", source = "url")
    PermissionNode toPermissionNode(SetMenu menu);

    RoleDTO toRoleDTO(SetRole role);

}
