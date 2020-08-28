package com.xinggou.common.vo;

import lombok.Data;

import java.io.Serializable;
import java.util.Date;

/**
 * 当前登录用户信息
 *
 * @author ：huangdl
 */
@Data
public class CurrentUser implements Serializable {

    private static final long serialVersionUID = 1L;

    /**
     * 用户ID
     */
    private long id;

    /**
     * 登录账号
     */
    private String userName;

    /**
     * 手机号
     */
    private String mobile;

    /**
     * 头像
     */
    private String avatar;

    /**
     * 昵称
     */
    private String nickName;

    /**
     * 令牌
     */
    private String token;
    /**
     * 登录IP
     */
    private String loginIp;

    /**
     * 登录时间
     */
    private Date loginTime;

    private String realName;
    private Date birthday;
    private String vipName;
    private Integer gender;
    private String address;

}
