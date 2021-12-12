package com.hyperionoj.common.cache;

import com.alibaba.fastjson.JSON;
import com.hyperionoj.common.vo.ErrorCode;
import com.hyperionoj.common.vo.Result;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.codec.digest.DigestUtils;
import org.apache.commons.lang3.StringUtils;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.Signature;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Pointcut;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Component;

import javax.annotation.Resource;
import java.lang.reflect.Method;
import java.time.Duration;


/**
 * @author Hyperion
 * @date 2021/12/12
 */

@Aspect
@Component
@Slf4j
public class CacheAspect {

    @Resource
    private RedisTemplate<String, String> redisTemplate;

    @Pointcut("@annotation(com.hyperionoj.common.cache.Cache)")
    public void pt() {
    }

    @Around("pt()")
    public Object around(ProceedingJoinPoint pjp) {
        try {
            Signature signature = pjp.getSignature();
            //类名
            String className = pjp.getTarget().getClass().getSimpleName();
            //调用的方法名
            String methodName = signature.getName();

            Class[] parameterTypes = new Class[pjp.getArgs().length];
            Object[] args = pjp.getArgs();
            //参数
            String params = "";
            for (int i = 0; i < args.length; i++) {
                if (args[i] != null) {
                    params += JSON.toJSONString(args[i]);
                    parameterTypes[i] = args[i].getClass();
                } else {
                    parameterTypes[i] = null;
                }
            }
            String toRedisParams = params;
            if (StringUtils.isNotEmpty(params)) {
                //加密 以防出现key过长以及字符转义获取不到的情况
                toRedisParams = DigestUtils.md5Hex(toRedisParams);
            }
            Method method = pjp.getSignature().getDeclaringType().getMethod(methodName, parameterTypes);
            //获取Cache注解
            Cache annotation = method.getAnnotation(Cache.class);
            //缓存过期时间
            long expire = annotation.time();
            //缓存名称
            String name = annotation.name();
            //先从redis获取
            String redisKey = name + ":" + className + ":" + methodName + ":" + toRedisParams;
            String redisValue = redisTemplate.opsForValue().get(redisKey);
            if (StringUtils.isNotEmpty(redisValue)) {
                log.info("读取缓存: {},{}", className, methodName);
                // 判断当前请求是否会改变其他变量如浏览量之类的数据
                return checkMethodName(className, methodName, redisKey, redisValue, expire);
            }
            Object proceed = pjp.proceed();
            redisTemplate.opsForValue().set(redisKey, JSON.toJSONString(proceed), Duration.ofMillis(expire));
            log.info("更新缓存: {},{}", className, methodName);
            return proceed;
        } catch (Throwable throwable) {
            throwable.printStackTrace();
        }
        return Result.fail(ErrorCode.SYSTEM_ERROR);
    }

    private Object checkMethodName(String className, String methodName, String redisKey, String redisValue, long expire) {

        Object result = JSON.parseObject(redisValue, Object.class);

        // 如果访问某篇文章则浏览量+1

        return result;
    }
}
