package com.xinggou.rc.entity;

import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import com.xinggou.common.core.Model;
import lombok.Data;

import java.util.Date;

/**
 * 定时任务
 * 
 * @author hdl
 * @date 2020-08-06 11:43:33
 */
@Data
@TableName("rc_schedule_job")
public class ScheduleJob extends Model {
	private static final long serialVersionUID = 1L;

	/**
	 * 任务id
	 */
	@TableId
	private Long id;
	/**
	 * spring bean名称
	 */
	private String beanName;
	/**
	 * 参数
	 */
	private String params;
	/**
	 * cron表达式
	 */
	private String cronExpression;
	/**
	 * 任务状态  10：正常  20：暂停
	 */
	private Integer status;
	/**
	 * 创建时间
	 */
	private Date createTime;
	/**
	 * 更新日期
	 */
	private Date updateTime;
	/**
	 * 备注
	 */
	private String remark;
	/**
	 * 删除标志
	 */
	private Boolean delFlag;

}
