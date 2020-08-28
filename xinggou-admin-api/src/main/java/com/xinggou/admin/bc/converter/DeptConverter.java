package com.xinggou.admin.bc.converter;

import com.xinggou.admin.bc.vo.request.CreateDeptRequest;
import com.xinggou.admin.bc.vo.request.UpdateDeptRequest;
import com.xinggou.admin.bc.vo.response.DeptNodeVo;
import com.xinggou.admin.bc.vo.response.DeptVo;
import com.xinggou.bc.bean.dto.DeptNode;
import com.xinggou.bc.bean.params.CreateDeptParams;
import com.xinggou.bc.bean.params.UpdateDeptParams;
import com.xinggou.bc.entity.Dept;
import com.xinggou.common.utils.tree.TreeNode;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.factory.Mappers;

import java.util.List;
import java.util.stream.Collectors;

/**
 * @author luoyb
 * Created on 2020/8/17
 */
@Mapper
public interface DeptConverter {
    DeptConverter INSTANCE = Mappers.getMapper(DeptConverter.class);

    @Mapping(target = "childNodes", expression = "java(toDeptNodeVos(treeNode.getChildNodes()))")
    DeptNodeVo toDeptNodeVo(DeptNode treeNode);

    default List<DeptNodeVo> toDeptNodeVos(List<TreeNode> nodeList) {
        return nodeList.stream()
                .map(node -> toDeptNodeVo((DeptNode) node))
                .collect(Collectors.toList());
    }

    DeptVo toDeptVo(Dept dept);

    CreateDeptParams toDept(CreateDeptRequest request);

    UpdateDeptParams toDept(UpdateDeptRequest request);
}
