package com.xinggou.bc.biz;

import com.baomidou.mybatisplus.extension.conditions.query.QueryChainWrapper;
import com.xinggou.bc.dao.SetRoleMenuDao;
import com.xinggou.bc.entity.SetRoleMenu;
import com.xinggou.common.core.BaseBiz;
import com.xinggou.common.utils.QueryObject;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.CollectionUtils;

import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

@Service
public class SetRoleMenuBiz extends BaseBiz<SetRoleMenuDao, SetRoleMenu> {

    @Override
    protected QueryChainWrapper<SetRoleMenu> builderQuery(QueryObject queryObject) {
        QueryChainWrapper<SetRoleMenu> wrapper = queryObject.fillWrapper(query());
        if (queryObject.getQuery("id") != null) {
            wrapper.eq("id", queryObject.getQuery("id"));
        }
        return wrapper;
    }

    public void deleteByMenuId(Long menuId) {
        baseMapper.deleteByMenuId(menuId);
    }

    public void deleteByRoleId(Long roleId) {
        baseMapper.deleteByRoleId(roleId);
    }

    public Set<Long> findRoleMenuSet(long roleId) {
        List<SetRoleMenu> list = lambdaQuery()
                .eq(SetRoleMenu::getRoleId, roleId)
                .list();
        return list.stream().map(SetRoleMenu::getMenuId).collect(Collectors.toSet());
    }

    @Transactional
    public void updateRolePermission(Long roleId, List<Long> menuIds) {
        deleteByRoleId(roleId);

        if (!CollectionUtils.isEmpty(menuIds)) {
            baseMapper.bulkCreate(roleId, menuIds);
        }
    }
}
