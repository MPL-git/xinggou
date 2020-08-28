package com.xinggou.rc.entity;

import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import com.xinggou.common.core.Model;
import lombok.Data;

import java.util.Date;

/**
 * 扩展存储表
 * 
 * @author hdl
 * @date 2020-08-06 11:43:33
 */
@Data
@TableName("rc_key_value")
public class KeyValue extends Model {
	private static final long serialVersionUID = 1L;

	/**
	 * ID主键
	 */
	@TableId
	private Long id;
	/**
	 * 类型
	 */
	private String typeCode;
	/**
	 * 键
	 */
	private String paramKey;
	/**
	 * 值
	 */
	private String paramValue;
	/**
	 * 保留的long字段
	 */
	private Long reservedLong;
	/**
	 * 创建日期
	 */
	private Date createDate;
	/**
	 * 更新日期
	 */
	private Date updateDate;
	/**
	 * 删除标志
	 */
	private Boolean delFlag;

}
