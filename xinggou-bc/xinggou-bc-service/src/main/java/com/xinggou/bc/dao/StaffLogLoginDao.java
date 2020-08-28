package com.xinggou.bc.dao;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.xinggou.bc.entity.StaffLogLogin;
import org.apache.ibatis.annotations.Mapper;

/**
 * 平台工作人员登录日志
 * 
 * @author hdl
 * @date 2020-08-06 11:43:33
 */
@Mapper
public interface StaffLogLoginDao extends BaseMapper<StaffLogLogin> {
	
}
