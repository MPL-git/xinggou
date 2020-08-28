package com.xinggou.bc.entity;

import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;

import lombok.Data;

import java.util.Date;

import com.xinggou.common.core.Model;

/**
 * 数据字典表
 * 
 * @author pisces
 * @date 2020-08-06 21:17:34
 */
@Data
@TableName("bc_set_dict")
public class SetDict extends Model {
	private static final long serialVersionUID = 1L;

	/**
	 * 
	 */
	@TableId
	private Long id;
	/**
	 * 字典名称
	 */
	private String name;
	/**
	 * 字典类型
	 */
	private String type;
	/**
	 * 字典码
	 */
	private String code;
	/**
	 * 字典值
	 */
	private String value;
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
