package com.xinggou.bc.entity;

import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;

import lombok.Data;

import java.util.Date;

import com.xinggou.common.core.Model;

/**
 * 角色
 * 
 * @author pisces
 * @date 2020-08-06 21:17:34
 */
@Data
@TableName("bc_set_role")
public class SetRole extends Model {
	private static final long serialVersionUID = 1L;

	/**
	 * 
	 */
	@TableId
	private Long id;
	/**
	 * 部门ID
	 */
	private Long deptId;
	/**
	 * 角色名称
	 */
	private String name;
	/**
	 * 排序
	 */
	private Integer seqNo;
	/**
	 * 创建日期 
	 */
	private Date createDate;
	/**
	 * 更新日期
	 */
	private Date updateDate;
	/**
	 * 备注
	 */
	private String remarks;
	/**
	 * 删除标识
	 */
	private String delFlag;

}
