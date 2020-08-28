package com.xinggou.bc.entity;

import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;

import lombok.Data;

import java.util.Date;

import com.xinggou.common.core.Model;

/**
 * 平台工作人员操作日志
 * 
 * @author pisces
 * @date 2020-08-06 21:17:34
 */
@Data
@TableName("bc_staff_log_operate")
public class StaffLogOperate extends Model {
	private static final long serialVersionUID = 1L;

	/**
	 * 主键
	 */
	@TableId
	private Long id;
	/**
	 * 平台工作人员ID
	 */
	private Long staffId;
	/**
	 * 用户操作
	 */
	private String operation;
	/**
	 * 请求方法
	 */
	private String method;
	/**
	 * 请求参数
	 */
	private String params;
	/**
	 * 执行时长(毫秒)
	 */
	private Long time;
	/**
	 * IP地址
	 */
	private String ip;
	/**
	 * 创建时间
	 */
	private Date createDate;
	/**
	 * 备注
	 */
	private String remarks;
	/**
	 * 删除标志
	 */
	private String delFlag;

}
