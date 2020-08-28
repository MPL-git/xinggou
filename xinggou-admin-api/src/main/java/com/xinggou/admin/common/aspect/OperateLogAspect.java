/**
 * Copyright (c) 2016-2019 南洋珠宝 All rights reserved.
 * <p>
 * https://www.yl.io
 * <p>
 * 版权所有，侵权必究！
 */

package com.xinggou.admin.common.aspect;

import com.google.gson.Gson;
import com.xinggou.bc.bean.params.staff.CreateStaffLogOperateParams;
import com.xinggou.bc.service.BcModifyService;
import com.xinggou.common.constant.Const;
import com.xinggou.common.utils.IPUtils;
import com.xinggou.common.utils.StrKit;
import lombok.extern.slf4j.Slf4j;
import org.apache.dubbo.config.annotation.Reference;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Pointcut;
import org.aspectj.lang.reflect.MethodSignature;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import javax.servlet.http.HttpServletRequest;
import java.lang.reflect.Method;

/**
 * 系统日志，切面处理类
 *
 * @author Mark sunlightcs@gmail.com
 */
@Aspect
@Component
@Slf4j
public class OperateLogAspect {

    @Reference
    private BcModifyService bcModifyService;
    @Autowired
    private HttpServletRequest httpServletRequest;

    @Pointcut("@annotation(com.xinggou.admin.common.aspect.OperateLog)")
    public void logPointCut() {

    }

    @Around("logPointCut()")
    public Object around(ProceedingJoinPoint point) throws Throwable {
        //执行开始
        long beginTime = System.currentTimeMillis();

        //执行方法
        Object result = point.proceed();

        //执行结束(毫秒)
        long time = System.currentTimeMillis() - beginTime;

        //保存日志
        saveOperateLog(point, time);
        return result;
    }

    private void saveOperateLog(ProceedingJoinPoint joinPoint, long time) {
        MethodSignature signature = (MethodSignature) joinPoint.getSignature();
        Method method = signature.getMethod();

        CreateStaffLogOperateParams params = new CreateStaffLogOperateParams();
        OperateLog operateLog = method.getAnnotation(OperateLog.class);
        if (operateLog != null) {
            params.setOperation(operateLog.value());
        }

        //请求的方法名
        String className = joinPoint.getTarget().getClass().getName();
        String methodName = signature.getName();
        params.setMethod(StrKit.buildMsg("{}.{}()", className, methodName));

        //请求的参数
        Object[] args = joinPoint.getArgs();
        try {
            params.setParams(new Gson().toJson(args[0]));
        } catch (Exception e) {
            log.debug("操作日志请求参数转化失败：{}", e.getMessage(), e);
        }

        params.setStaffId(getStaffId());
        params.setIp(IPUtils.getIpAddr(httpServletRequest));
        params.setTime(time);
        bcModifyService.createStaffLogOperate(params);
    }

    private Long getStaffId() {
        Object staffId = httpServletRequest.getAttribute(Const.AUTH_STAFF_ID);
        return staffId == null ? 0 : Long.parseLong(staffId.toString());
    }


}
