package com.xinggou.bc.biz;

import com.baomidou.mybatisplus.extension.conditions.query.QueryChainWrapper;
import com.google.common.collect.Lists;
import com.xinggou.bc.bean.dto.DeptNode;
import com.xinggou.bc.bean.params.CreateDeptParams;
import com.xinggou.bc.bean.params.UpdateDeptParams;
import com.xinggou.bc.constant.BcConst;
import com.xinggou.bc.converter.BcBizConverter;
import com.xinggou.bc.dao.DeptDao;
import com.xinggou.bc.entity.Dept;
import com.xinggou.bc.entity.Staff;
import com.xinggou.common.constant.StateConst;
import com.xinggou.common.core.BaseBiz;
import com.xinggou.common.exception.Assert;
import com.xinggou.common.exception.BizException;
import com.xinggou.common.utils.QueryObject;
import com.xinggou.common.utils.tree.TreeBuilder;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.CollectionUtils;

import java.util.Date;
import java.util.List;
import java.util.stream.Collectors;

@Service
public class DeptBiz extends BaseBiz<DeptDao, Dept> {

    @Autowired
    private StaffBiz staffBiz;
    @Autowired
    private SetRoleBiz setRoleBiz;

    public Dept saveInfo(Dept info) {
        Dept model;
        if (info.getId() == null || info.getId() == 0L) {
            model = new Dept();
        } else {
            model = getById(info.getId());
        }

        model.setParentId(info.getParentId());
        model.setName(info.getName());
        model.setSeqNo(info.getSeqNo());
        if (model.getId() == null || model.getId() == 0) {
            save(model);
        } else {
            model.setUpdateDate(new Date());
            updateById(model);
        }
        return model;
    }

    public void deleteById(Long id) {
        QueryObject queryObject = new QueryObject();
        queryObject.addQuery("deptId", id);
        if (staffBiz.count(queryObject) > 0) {
            throw new BizException("该部门底下还有用户，不允许删除");
        }
        if (setRoleBiz.count(queryObject) > 0) {
            throw new BizException("该部门底下还有角色，不允许删除");
        }

        this.removeById(id);
    }


    @Override
    protected QueryChainWrapper<Dept> builderQuery(QueryObject queryObject) {
        QueryChainWrapper<Dept> wrapper = queryObject.fillWrapper(query());
        if (queryObject.getQuery("id") != null) {
            wrapper.eq("id", queryObject.getQuery("id"));
        }
        if (queryObject.getQuery("parentId") != null) {
            wrapper.eq("parent_id", queryObject.getQuery("parentId"));
        }
        if (queryObject.getQuery("name") != null) {
            wrapper.like("name", queryObject.getQuery("name"));
        }
        return wrapper;
    }

    public List<Dept> findAllDept() {
        return lambdaQuery()
                .eq(Dept::getDelFlag, StateConst.FALSE)
                .orderByAsc(Dept::getParentId)
                .orderByAsc(Dept::getSeqNo)
                .list();
    }

    public Dept createDept(CreateDeptParams params) {
        Dept dept = BcBizConverter.INSTANCE.toDept(params);
        dept.setCreateDate(new Date());
        dept.setDelFlag(StateConst.FALSE);
        this.save(dept);
        return dept;
    }

    public Dept updateDept(UpdateDeptParams params) {
        Dept dept = this.getById(params.getDeptId());
        Assert.notNull(dept, "ID为[" + params.getDeptId() + "]的用户不存在");
        dept.setName(params.getName());
        dept.setRemarks(params.getRemarks());
        dept.setUpdateDate(new Date());
        this.updateById(dept);
        return dept;
    }

    public DeptNode getDeptTree() {
        List<Dept> list = this.findAllDept();
        return buildDeptNode(list);
    }

    public DeptNode getStaffDeptTree(long staffId) {
        List<Dept> list;
        if (BcConst.SUPER_ADMIN == staffId) {
            list = this.findAllDept();
        } else {
            Staff staff = staffBiz.getById(staffId);
            list = listStaffDept(staff.getDeptId());
        }
        return buildDeptNode(list);
    }

    private DeptNode buildDeptNode(List<Dept> deptList) {
        List<DeptNode> deptNodeList = deptList.stream().map(dept -> {
            DeptNode node = new DeptNode();
            node.setDeptId(dept.getId());
            node.setName(dept.getName());
            node.setParentId(dept.getParentId());
            return node;
        }).collect(Collectors.toList());
        DeptNode rootNode = new DeptNode();
        rootNode.setLevel(0);
        TreeBuilder.convertTree(rootNode, deptNodeList);
        return rootNode;
    }

    private List<Dept> listStaffDept(Long deptId) {
        List<Dept> result = Lists.newArrayList();
        result.add(this.getById(deptId));
        loopFillStaffDept(result, deptId);
        return result;
    }

    private void loopFillStaffDept(List<Dept> result, Long parentDeptId) {
        List<Dept> list = findByParentId(parentDeptId);
        if (!CollectionUtils.isEmpty(list)) {
            result.addAll(list);
            for (Dept dept : list) {
                loopFillStaffDept(result, dept.getId());
            }
        }
    }

    private List<Dept> findByParentId(Long parentDeptId) {
        return lambdaQuery()
                .eq(Dept::getParentId, parentDeptId)
                .eq(Dept::getDelFlag, StateConst.FALSE)
                .orderByAsc(Dept::getSeqNo)
                .list();
    }
}
