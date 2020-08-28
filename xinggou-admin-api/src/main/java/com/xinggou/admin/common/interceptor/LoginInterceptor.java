package com.xinggou.admin.common.interceptor;

import com.xinggou.bc.constant.BcRedisKeys;
import com.xinggou.common.config.JwtOperator;
import com.xinggou.common.constant.CodeConst;
import com.xinggou.common.constant.Const;
import com.xinggou.common.exception.BizException;
import com.xinggou.common.utils.AnnotationHelper;
import org.apache.commons.lang.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.stereotype.Component;
import org.springframework.web.method.HandlerMethod;
import org.springframework.web.servlet.handler.HandlerInterceptorAdapter;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 * 登录拦截器
 *
 * @author hdl
 */
@Component
public class LoginInterceptor extends HandlerInterceptorAdapter {

    @Resource
    private JwtOperator jwtOperator;
    @Autowired
    private StringRedisTemplate stringRedisTemplate;

    @Override
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) {
        if (handler instanceof HandlerMethod) {
            Login login = AnnotationHelper.findMethodOrClassLevelAnnotation(handler, Login.class);
            if (login != null && login.required()) {
                return validateToken(request);
            }
        }
        return true;
    }

    private boolean validateToken(HttpServletRequest request) {
        String token = request.getHeader(Const.AUTH_TOKEN);
        //未携带token
        if (StringUtils.isEmpty(token)) {
            throw new BizException("没有提供认证信息", CodeConst.CODE_401);
        }

        //token在黑名单内
        Boolean isBlackToken = stringRedisTemplate.hasKey(BcRedisKeys.getBlackTokenKey(token));
        if (isBlackToken != null && isBlackToken) {
            throw new BizException("token已失效", CodeConst.CODE_402);
        }

        //取出token包含的身份
        long staffId = jwtOperator.getStaffId(token);
        if (staffId == 0) {
            throw new BizException("无效的token", CodeConst.CODE_402);
        }

        //请求中暂存用户信息
        request.setAttribute(Const.AUTH_STAFF_ID, staffId);

        return true;
    }


}
