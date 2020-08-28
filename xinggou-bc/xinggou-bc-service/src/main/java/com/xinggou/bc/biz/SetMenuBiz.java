package com.xinggou.bc.biz;

import com.baomidou.mybatisplus.extension.conditions.query.QueryChainWrapper;
import com.xinggou.bc.bean.params.CreateMenuParams;
import com.xinggou.bc.bean.params.UpdateMenuParams;
import com.xinggou.bc.constant.BcConst;
import com.xinggou.bc.converter.BcBizConverter;
import com.xinggou.bc.dao.SetMenuDao;
import com.xinggou.bc.entity.SetMenu;
import com.xinggou.bc.enums.MenuTypeEnum;
import com.xinggou.common.constant.StateConst;
import com.xinggou.common.core.BaseBiz;
import com.xinggou.common.utils.QueryObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Date;
import java.util.List;
import java.util.stream.Collectors;

@Service
public class SetMenuBiz extends BaseBiz<SetMenuDao, SetMenu> {

    @Autowired
    private SetRoleMenuBiz roleMenuBiz;

    @Transactional
    public void deleteById(long menuId) {
        //删除菜单
        this.removeById(menuId);
        //删除菜单与角色关联
        roleMenuBiz.deleteByMenuId(menuId);
    }

    @Override
    protected QueryChainWrapper<SetMenu> builderQuery(QueryObject queryObject) {
        QueryChainWrapper<SetMenu> wrapper = queryObject.fillWrapper(query());
        if (queryObject.getQuery("id") != null) {
            wrapper.eq("id", queryObject.getQuery("id"));
        }
        if (queryObject.getQuery("parentId") != null) {
            wrapper.eq("parent_id", queryObject.getQuery("parentId"));
        }
        return wrapper;
    }

    public List<SetMenu> findStaffMenu(long staffId) {
        //系统管理员
        if (staffId == BcConst.SUPER_ADMIN) {
            return findAllMenu();
        }
        return this.baseMapper.findStaffMenu(staffId);
    }

    public List<SetMenu> findAllMenu() {
        return lambdaQuery()
                .eq(SetMenu::getDelFlag, StateConst.FALSE)
                .orderByAsc(SetMenu::getParentId)
                .orderByAsc(SetMenu::getSeqNo)
                .list();
    }

    public List<String> listPerms() {
        List<SetMenu> list = lambdaQuery()
                .select(SetMenu::getPerms)
                .eq(SetMenu::getType, MenuTypeEnum.PERM.getValue())
                .eq(SetMenu::getDelFlag, StateConst.FALSE)
                .list();
        return list.stream().map(SetMenu::getPerms).collect(Collectors.toList());
    }

    public SetMenu createMenu(CreateMenuParams params) {
        SetMenu menu = BcBizConverter.INSTANCE.toSetMenu(params);
        menu.setCreateDate(new Date());
        menu.setDelFlag(StateConst.FALSE);
        this.save(menu);
        return menu;
    }

    public SetMenu updateMenu(UpdateMenuParams params) {
        SetMenu menu = this.getById(params.getMenuId());
        BcBizConverter.INSTANCE.updateSetMenu(params, menu);
        menu.setUpdateDate(new Date());
        this.updateById(menu);
        return menu;
    }
}
