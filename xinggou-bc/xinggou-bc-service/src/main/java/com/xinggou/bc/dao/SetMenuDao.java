package com.xinggou.bc.dao;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.xinggou.bc.entity.SetMenu;
import org.apache.ibatis.annotations.Mapper;

import java.util.List;

/**
 * 菜单管理
 * 
 * @author hdl
 * @date 2020-08-06 11:43:33
 */
@Mapper
public interface SetMenuDao extends BaseMapper<SetMenu> {

    List<SetMenu> findStaffMenu(long staffId);

}
