package com.xinggou.bc.entity;

import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import com.xinggou.common.core.Model;
import lombok.Data;

import java.util.Date;

/**
 * 用户token
 * 
 * @author pisces
 * @date 2020-07-31 15:37:25
 */
@Data
@TableName("bc_staff_token")
public class StaffToken extends Model {
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
	 * TOKEN
	 */
	private String token;
	/**
	 * 过期时间
	 */
	private Date expiretime;
	/**
	 * ip
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
