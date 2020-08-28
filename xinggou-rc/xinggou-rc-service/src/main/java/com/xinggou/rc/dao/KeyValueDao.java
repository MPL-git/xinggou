package com.xinggou.rc.dao;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.xinggou.rc.entity.KeyValue;
import org.apache.ibatis.annotations.Mapper;

/**
 * 扩展存储表
 * 
 * @author hdl
 * @date 2020-08-06 11:43:33
 */
@Mapper
public interface KeyValueDao extends BaseMapper<KeyValue> {
	
}
