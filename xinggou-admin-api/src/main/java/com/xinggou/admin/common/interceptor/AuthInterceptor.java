package com.xinggou.admin.common.interceptor;

import com.xinggou.bc.constant.BcRedisKeys;
import com.xinggou.common.constant.CodeConst;
import com.xinggou.common.constant.Const;
import com.xinggou.common.exception.BizException;
import com.xinggou.common.utils.AnnotationHelper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.stereotype.Component;
import org.springframework.util.StringUtils;
import org.springframework.web.method.HandlerMethod;
import org.springframework.web.servlet.handler.HandlerInterceptorAdapter;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 * 授权拦截器
 *
 * @author hdl
 */
@Component
public class AuthInterceptor extends HandlerInterceptorAdapter {

    @Autowired
    private StringRedisTemplate stringRedisTemplate;

    @Override
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) throws Exception {
        if (handler instanceof HandlerMethod) {
            checkPerms(request, handler);
        }
        return true;
    }

    private void checkPerms(HttpServletRequest request, Object handler) {
        Perms perms = AnnotationHelper.findMethodOrClassLevelAnnotation(handler, Perms.class);
        if (perms != null && !StringUtils.hasText(perms.value())) {
            long staffId = (Long) request.getAttribute(Const.AUTH_STAFF_ID);
            Boolean hasPerm = stringRedisTemplate.opsForSet().isMember(BcRedisKeys.getStaffPermsKey(staffId), perms.value());
            if (hasPerm == null || !hasPerm) {
                throw new BizException("没有权限，请联系管理员授权", CodeConst.CODE_403);
            }
        }
    }

}
