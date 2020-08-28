package com.xinggou.bc.entity;

import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import com.xinggou.bc.enums.ConfigStatusEnum;
import com.xinggou.common.core.Model;
import lombok.Data;

import java.util.Date;

/**
 * 系统配置信息表
 * 
 * @author pisces
 * @date 2020-08-06 21:17:34
 */
@Data
@TableName("bc_set_config")
public class SetConfig extends Model {
	private static final long serialVersionUID = 1L;

	/**
	 * 
	 */
	@TableId
	private Long id;
	/**
	 * 名称
	 */
	private String name;
	/**
	 * key
	 */
	private String paramKey;
	/**
	 * value
	 */
	private String paramValue;
	/**
	 * 状态 10显示  20隐藏
	 */
	private ConfigStatusEnum status;
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
