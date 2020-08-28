package com.xinggou.bc.service;

import com.xinggou.bc.bean.dto.DeptNode;
import com.xinggou.bc.bean.params.CreateDeptParams;
import com.xinggou.bc.bean.params.UpdateDeptParams;
import com.xinggou.bc.entity.Dept;
import org.apache.dubbo.config.annotation.Service;

import java.util.List;

/**
 * @author luoyb
 * Created on 2020/8/17
 */
@Service
public interface DeptService {

    /**
     * 获取部门树
     *
     * @return 部门树 {@link DeptNode}
     */
    DeptNode getDeptTree();

    Dept getDeptById(Long deptId);

    Dept createDept(CreateDeptParams params);

    Dept updateDept(UpdateDeptParams params);

    void bulkDeleteDept(List<Long> deptIds);

    /**
     * 获取员工部门树
     *
     * @param staffId 员工ID
     * @return 部门树 {@link DeptNode}
     */
    DeptNode getStaffDeptTree(long staffId);

}
