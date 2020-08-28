package com.xinggou.rc.entity;

import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import com.xinggou.common.core.Model;
import lombok.Data;

import java.util.Date;

/**
 * 定时任务日志
 * 
 * @author hdl
 * @date 2020-08-06 11:43:33
 */
@Data
@TableName("rc_schedule_job_log")
public class ScheduleJobLog extends Model {
	private static final long serialVersionUID = 1L;

	/**
	 * 任务日志id
	 */
	@TableId
	private Long id;
	/**
	 * 任务id
	 */
	private Long jobId;
	/**
	 * spring bean名称
	 */
	private String beanName;
	/**
	 * 参数
	 */
	private String params;
	/**
	 * 任务状态    10：成功    20：失败
	 */
	private Integer status;
	/**
	 * 失败信息
	 */
	private String error;
	/**
	 * 耗时(单位：毫秒)
	 */
	private Integer times;
	/**
	 * 创建时间
	 */
	private Date createTime;
	/**
	 * 更新日期
	 */
	private Date updateTime;
	/**
	 * 删除标志
	 */
	private Boolean delFlag;

}
