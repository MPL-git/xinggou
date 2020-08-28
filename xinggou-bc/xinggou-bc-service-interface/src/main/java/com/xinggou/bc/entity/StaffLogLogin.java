package com.xinggou.bc.entity;

import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;

import lombok.Data;

import java.util.Date;

import com.xinggou.common.core.Model;

/**
 * 平台工作人员登录日志
 * 
 * @author pisces
 * @date 2020-08-06 21:17:34
 */
@Data
@TableName("bc_staff_log_login")
public class StaffLogLogin extends Model {
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
	 * 来源ip
	 */
	private String ip;
	/**
	 * 创建日期
	 */
	private Date createDate;
	/**
	 * 删除标识
	 */
	private String delFlag;

}
