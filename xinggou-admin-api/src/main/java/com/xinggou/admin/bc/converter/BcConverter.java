package com.xinggou.admin.bc.converter;

import com.google.common.base.Joiner;
import com.xinggou.admin.bc.vo.request.CreateMenuRequest;
import com.xinggou.admin.bc.vo.request.PaginateConfigRequest;
import com.xinggou.admin.bc.vo.request.PaginateDictRequest;
import com.xinggou.admin.bc.vo.request.PaginateRoleRequest;
import com.xinggou.admin.bc.vo.request.PaginateStaffLogLoginRequest;
import com.xinggou.admin.bc.vo.request.SaveConfigRequest;
import com.xinggou.admin.bc.vo.request.SaveDictRequest;
import com.xinggou.admin.bc.vo.request.SaveRoleRequest;
import com.xinggou.admin.bc.vo.request.UpdateMenuRequest;
import com.xinggou.admin.bc.vo.request.staff.PaginateStaffLogOperateRequest;
import com.xinggou.admin.bc.vo.response.ConfigVo;
import com.xinggou.admin.bc.vo.response.DictVo;
import com.xinggou.admin.bc.vo.response.MenuNodeVo;
import com.xinggou.admin.bc.vo.response.MenuVo;
import com.xinggou.admin.bc.vo.response.PermissionNodeVo;
import com.xinggou.admin.bc.vo.response.RolePermissionNodeVo;
import com.xinggou.admin.bc.vo.response.RolePermissionVo;
import com.xinggou.admin.bc.vo.response.RoleSimpleVo;
import com.xinggou.admin.bc.vo.response.RoleVo;
import com.xinggou.admin.bc.vo.response.StaffLogLoginVo;
import com.xinggou.admin.bc.vo.response.StaffLogOperateVo;
import com.xinggou.bc.bean.dto.MenuDTO;
import com.xinggou.bc.bean.dto.MenuNode;
import com.xinggou.bc.bean.dto.PermissionNode;
import com.xinggou.bc.bean.dto.RoleDTO;
import com.xinggou.bc.bean.dto.RolePermissionDTO;
import com.xinggou.bc.bean.dto.RolePermissionNode;
import com.xinggou.bc.bean.dto.StaffLogOperateDTO;
import com.xinggou.bc.bean.params.CreateMenuParams;
import com.xinggou.bc.bean.params.PaginateConfigParams;
import com.xinggou.bc.bean.params.PaginateDictParams;
import com.xinggou.bc.bean.params.PaginateRoleParams;
import com.xinggou.bc.bean.params.SaveConfigParams;
import com.xinggou.bc.bean.params.SaveDictParams;
import com.xinggou.bc.bean.params.SaveRoleParams;
import com.xinggou.bc.bean.params.UpdateMenuParams;
import com.xinggou.bc.bean.params.staff.PaginateStaffLogLoginParams;
import com.xinggou.bc.bean.params.staff.PaginateStaffLogOperateParams;
import com.xinggou.bc.entity.SetConfig;
import com.xinggou.bc.entity.SetDict;
import com.xinggou.bc.entity.SetRole;
import com.xinggou.bc.entity.StaffLogLogin;
import com.xinggou.common.constant.Const;
import com.xinggou.common.utils.tree.TreeNode;
import com.xinggou.common.vo.PageWrap;
import org.mapstruct.InheritConfiguration;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.factory.Mappers;

import java.util.List;
import java.util.stream.Collectors;

/**
 * @author luoyb
 * Created on 2020/8/18
 */
@Mapper
public interface BcConverter {
    BcConverter INSTANCE = Mappers.getMapper(BcConverter.class);

    PaginateRoleParams toPaginateRoleParams(PaginateRoleRequest request);

    @InheritConfiguration(name = "toRoleVo")
    PageWrap<RoleVo> toRoleVoPageWrap(PageWrap<RoleDTO> pageWrap);

    @Mapping(target = "assignedStaffs", expression = "java(joinByComma(dto.getAssignedStaffList()))")
    RoleVo toRoleVo(RoleDTO dto);

    default String joinByComma(List<String> list) {
        return Joiner.on(Const.COMMA).join(list);
    }

    SaveRoleParams toSaveRoleParams(SaveRoleRequest request);

    @Mapping(target = "childNodes", expression = "java(toPermissionNodeVos(treeNode.getChildNodes()))")
    PermissionNodeVo toPermissionNodeVo(PermissionNode treeNode);

    default List<PermissionNodeVo> toPermissionNodeVos(List<TreeNode> nodeList) {
        return nodeList.stream()
                .map(node -> toPermissionNodeVo((PermissionNode) node))
                .collect(Collectors.toList());
    }

    RolePermissionVo toRolePermissionVo(RolePermissionDTO dto);

    @Mapping(target = "childNodes", expression = "java(toRoleNodeVos(treeNode.getChildNodes()))")
    RolePermissionNodeVo toRolePermissionNodeVo(RolePermissionNode treeNode);

    default List<RolePermissionNodeVo> toRoleNodeVos(List<TreeNode> nodeList) {
        return nodeList.stream()
                .map(node -> toRolePermissionNodeVo((RolePermissionNode) node))
                .collect(Collectors.toList());
    }

    @Mapping(target = "childNodes", expression = "java(toMenuNodeVos(treeNode.getChildNodes()))")
    MenuNodeVo toMenuNodeTreeVo(MenuNode treeNode);

    default List<MenuNodeVo> toMenuNodeVos(List<TreeNode> nodeList) {
        return nodeList.stream()
                .map(node -> toMenuNodeTreeVo((MenuNode) node))
                .collect(Collectors.toList());
    }

    CreateMenuParams toCreateMenuParams(CreateMenuRequest request);

    UpdateMenuParams toUpdateMenuParams(UpdateMenuRequest request);

    MenuVo toMenuVo(MenuDTO dto);

    PaginateDictParams toPaginateDictParams(PaginateDictRequest request);

    PageWrap<DictVo> toDictVoPageWrap(PageWrap<SetDict> pageWrap);

    SaveDictParams toSaveDictParams(SaveDictRequest request);

    DictVo toDictVo(SetDict dict);

    PaginateStaffLogLoginParams toPaginateStaffLogLoginParams(PaginateStaffLogLoginRequest request);

    PageWrap<StaffLogLoginVo> toStaffLogLoginVoPageWrap(PageWrap<StaffLogLogin> pageWrap);

    PaginateConfigParams toPaginateConfigParams(PaginateConfigRequest request);

    PageWrap<ConfigVo> toConfigVoPageWrap(PageWrap<SetConfig> pageWrap);

    SaveConfigParams toSaveConfigParams(SaveConfigRequest request);

    ConfigVo toConfigVo(SetConfig config);

    PaginateStaffLogOperateParams toPaginateStaffLogOperateParams(PaginateStaffLogOperateRequest request);

    PageWrap<StaffLogOperateVo> toStaffLogOperateVoPageWarp(PageWrap<StaffLogOperateDTO> pageWrap);

    RoleSimpleVo toRoleSimpleVo(SetRole role);
}
