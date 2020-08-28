package com.xinggou.bc.service;

import com.xinggou.bc.bean.dto.DeptNode;
import com.xinggou.bc.bean.params.CreateDeptParams;
import com.xinggou.bc.bean.params.UpdateDeptParams;
import com.xinggou.bc.biz.DeptBiz;
import com.xinggou.bc.entity.Dept;
import org.apache.dubbo.config.annotation.Service;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

/**
 * @author luoyb
 * Created on 2020/8/17
 */
@Service
public class DeptServiceImpl implements DeptService {

    @Autowired
    private DeptBiz deptBiz;

    @Override
    public DeptNode getDeptTree() {
        return deptBiz.getDeptTree();
    }

    @Override
    public Dept getDeptById(Long deptId) {
        return deptBiz.getById(deptId);
    }

    @Override
    public Dept createDept(CreateDeptParams params) {
        return deptBiz.createDept(params);
    }

    @Override
    public Dept updateDept(UpdateDeptParams params) {
        return deptBiz.updateDept(params);
    }

    @Override
    @Transactional
    public void bulkDeleteDept(List<Long> deptIds) {
        for (Long deptId : deptIds) {
            deptBiz.deleteById(deptId);
        }
    }

    @Override
    public DeptNode getStaffDeptTree(long staffId) {
        return deptBiz.getStaffDeptTree(staffId);
    }
}
