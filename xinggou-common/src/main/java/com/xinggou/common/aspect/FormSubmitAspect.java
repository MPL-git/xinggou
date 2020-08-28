package com.xinggou.common.aspect;

import com.google.common.cache.Cache;
import com.google.common.cache.CacheBuilder;
import com.xinggou.common.exception.BizException;
import com.xinggou.common.utils.R;
import com.xinggou.common.utils.StrKit;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.reflect.MethodSignature;
import org.springframework.context.annotation.Configuration;

import java.lang.reflect.Method;
import java.util.concurrent.TimeUnit;

@Aspect
@Configuration
public class FormSubmitAspect {

    private static final Cache<String, Object> CACHES = CacheBuilder.newBuilder()
            // 最大缓存 100 个
            .maximumSize(100)
            // 设置缓存过期时间为S
            .expireAfterWrite(5, TimeUnit.SECONDS)
            .build();

    @Around("execution(public * *(..)) && @annotation(com.xinggou.common.annotation.FormSubmitToken)")
    //@Around("execution(public * *(..))")
    public Object interceptor(ProceedingJoinPoint point) {
        MethodSignature signature = (MethodSignature) point.getSignature();
        Method method = signature.getMethod();
        String key = getCacheKey(method, point.getArgs());
        //System.out.println("key:" + key);
        if (StrKit.notBlank(key)) {
            if (CACHES.getIfPresent(key) != null) {
                return R.error("请勿重复请求");

            }
            // 如果是第一次请求,就将key存入缓存中
            CACHES.put(key, key);
        }

        try {
            return point.proceed();
        } catch (BizException rre) {
            throw rre;
        } catch (Throwable throwable) {
            throw new RuntimeException("服务器异常");
        } finally {
            CACHES.invalidate(key);
        }
    }

    /**
     * 将来还要加上用户的唯一标识
     */
    private String getCacheKey(Method method, Object[] args) {
        //if(args.length > 0)
        return method.getName() + args[0];
    }


}