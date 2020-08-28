package com.xinggou.bc.dao;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.xinggou.bc.entity.SetConfig;
import org.apache.ibatis.annotations.Mapper;

/**
 * 系统配置信息表
 * 
 * @author hdl
 * @date 2020-08-06 11:43:33
 */
@Mapper
public interface SetConfigDao extends BaseMapper<SetConfig> {
	
}
