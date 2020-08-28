package com.xinggou.bc.entity;

import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;

import lombok.Data;

import java.util.Date;

import com.xinggou.common.core.Model;

/**
 * 角色与菜单对应关系
 * 
 * @author pisces
 * @date 2020-08-06 21:17:34
 */
@Data
@TableName("bc_set_role_menu")
public class SetRoleMenu extends Model {
	private static final long serialVersionUID = 1L;

	/**
	 * 
	 */
	@TableId
	private Long id;
	/**
	 * 角色ID
	 */
	private Long roleId;
	/**
	 * 菜单ID
	 */
	private Long menuId;

}
