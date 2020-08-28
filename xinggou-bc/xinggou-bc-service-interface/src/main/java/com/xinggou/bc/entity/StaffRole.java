package com.xinggou.bc.entity;

import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;

import lombok.Data;

import java.util.Date;

import com.xinggou.common.core.Model;

/**
 * 平台账号与角色对应关系
 * 
 * @author pisces
 * @date 2020-08-06 21:17:34
 */
@Data
@TableName("bc_staff_role")
public class StaffRole extends Model {
	private static final long serialVersionUID = 1L;

	/**
	 * 
	 */
	@TableId
	private Long id;
	/**
	 * 用户ID
	 */
	private Long staffId;
	/**
	 * 角色ID
	 */
	private Long roleId;

}
