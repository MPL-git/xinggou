package com.xinggou.admin.common.core;

import com.xinggou.admin.common.interceptor.Login;
import com.xinggou.common.constant.Const;
import com.xinggou.common.utils.IPUtils;
import org.springframework.beans.factory.annotation.Autowired;

import javax.servlet.http.HttpServletRequest;

@Login
public abstract class BaseController {

    @Autowired
    protected HttpServletRequest httpServletRequest;

    /**
     * 获取请求IP地址
     */
    protected String getIpAddr() {
        return IPUtils.getIpAddr(httpServletRequest);
    }

    /**
     * 获取登陆用户ID（仅登陆状态下有值）
     */
    protected long getStaffId() {
        Object staffId = httpServletRequest.getAttribute(Const.AUTH_STAFF_ID);
        return staffId == null ? 0 : Long.parseLong(staffId.toString());
    }

}
