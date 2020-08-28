package com.xinggou.bc.entity;

import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import com.xinggou.bc.enums.PositionStatusEnum;
import com.xinggou.bc.enums.StaffStatusEnum;
import com.xinggou.common.core.Model;
import com.xinggou.common.enums.GenderEnum;
import lombok.Data;

import java.util.Date;

/**
 * 系统用户
 * 
 * @author pisces
 * @date 2020-08-06 21:17:34
 */
@Data
@TableName("bc_staff")
public class Staff extends Model {
	private static final long serialVersionUID = 1L;

	@TableId
	private Long id;
	/**
	 * 用户名
	 */
	private String userName;
	/**
	 * 密码
	 */
	private String password;
	/**
	 * 盐
	 */
	private String salt;
	/**
	 * 昵称
	 */
	private String nickName;
	/**
	 * 真实姓名
	 */
	private String realName;
	/**
	 * 部门ID
	 */
	private Long deptId;
	/**
	 * 头像
	 */
	private String avatar;
	/**
	 * 邮箱
	 */
	private String email;
	/**
	 * 手机号
	 */
	private String mobile;
	/**
	 * 生日
	 */
	private Date birthday;
	/**
	 * 帐号状态  10正常   20禁用
	 */
	private StaffStatusEnum status;
	/**
	 * 性别：10男 20女 30未知
	 */
	private GenderEnum gender;
	/**
	 * 职位
	 */
	private String position;
	/**
	 * 职位状态 10在职   20离职
	 */
	private PositionStatusEnum positionStatus;
	/**
	 * 入职时间
	 */
	private Date entryTime;
	/**
	 * 离职时间
	 */
	private Date exitTime;
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
