package com.xinggou.rc.dao;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.xinggou.rc.entity.ScheduleJob;
import org.apache.ibatis.annotations.Mapper;

/**
 * 定时任务
 * 
 * @author hdl
 * @date 2020-08-06 11:43:33
 */
@Mapper
public interface ScheduleJobDao extends BaseMapper<ScheduleJob> {
	
}
