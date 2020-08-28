package com.xinggou.bc.dao;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.xinggou.bc.entity.SetDict;
import org.apache.ibatis.annotations.Mapper;

/**
 * 数据字典表
 * 
 * @author hdl
 * @date 2020-08-06 11:43:33
 */
@Mapper
public interface SetDictDao extends BaseMapper<SetDict> {
	
}
