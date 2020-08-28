package com.xinggou.bc.entity;

import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import com.xinggou.bc.enums.MenuTypeEnum;
import com.xinggou.common.core.Model;
import lombok.Data;

import java.util.Date;

/**
 * 菜单管理
 * 
 * @author pisces
 * @date 2020-08-06 21:17:34
 */
@Data
@TableName("bc_set_menu")
public class SetMenu extends Model {
	private static final long serialVersionUID = 1L;

	/**
	 * 
	 */
	@TableId
	private Long id;
	/**
	 * 父菜单ID，一级菜单为0
	 */
	private Long parentId;
	/**
	 * 类型   10：目录   20：菜单   30：按钮
	 */
	private MenuTypeEnum type;
	/**
	 * 菜单名称
	 */
	private String name;
	/**
	 * 菜单图标
	 */
	private String icon;
	/**
	 * 菜单URL
	 */
	private String url;
	/**
	 * 授权(多个用逗号分隔，如：user:list,user:create)
	 */
	private String perms;
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
