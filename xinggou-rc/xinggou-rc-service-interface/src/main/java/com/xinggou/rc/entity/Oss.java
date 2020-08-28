package com.xinggou.rc.entity;

import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import com.xinggou.common.core.Model;
import lombok.Data;

import java.util.Date;

/**
 * 文件上传
 * 
 * @author hdl
 * @date 2020-08-06 11:43:33
 */
@Data
@TableName("rc_oss")
public class Oss extends Model {
	private static final long serialVersionUID = 1L;

	/**
	 * 
	 */
	@TableId
	private Long id;
	/**
	 * URL地址
	 */
	private String url;
	/**
	 * 创建时间
	 */
	private Date createDate;
	/**
	 * 更新时间
	 */
	private Date updateDate;
	/**
	 * 备注
	 */
	private String remarks;
	/**
	 * 删除标志
	 */
	private String delFlag;

}
