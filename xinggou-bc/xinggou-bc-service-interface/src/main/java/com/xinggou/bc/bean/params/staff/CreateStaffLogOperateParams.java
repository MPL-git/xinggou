package com.xinggou.bc.bean.params.staff;

import lombok.Data;

import java.io.Serializable;

/**
 * @author luoyb
 * Created on 2020/8/19
 */
@Data
public class CreateStaffLogOperateParams implements Serializable {

    private static final long serialVersionUID = 1L;

    /**
     * 员工ID
     */
    private Long staffId;
    /**
     * 用户操作
     */
    private String operation;
    /**
     * 请求方法
     */
    private String method;
    /**
     * 请求参数
     */
    private String params;
    /**
     * 执行时长(毫秒)
     */
    private Long time;
    /**
     * IP地址
     */
    private String ip;

}
