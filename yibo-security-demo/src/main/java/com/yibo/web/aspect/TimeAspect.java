package com.yibo.web.aspect;

import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.springframework.stereotype.Component;

import java.util.Date;

/**
 * @author: huangyibo
 * @Date: 2019/7/27 12:56
 * @Description:
 */

@Aspect
@Component
public class TimeAspect {

    @Around("execution(* com.yibo.web.controller.UserController.*(..))")
    public Object handleControllerMethod(ProceedingJoinPoint pjp) throws Throwable {
        System.out.println("time aspect start");
        Object[] args = pjp.getArgs();
        for (Object arg : args) {
            System.out.println("arg is：" + arg);
        }
        long start = new Date().getTime();
        Object object = pjp.proceed();
        System.out.println("time aspect 总耗时："+(new Date().getTime() - start));
        System.out.println("time aspect end");
        return object;
    }
}
