package com.nowcoder.aspect;

import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.annotation.After;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Before;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;

import java.util.Date;

/**
 * Created by nowcoder on 2016/7/10.
 */
@Aspect
@Component
public class LogAspect {
    private static final Logger logger = LoggerFactory.getLogger(LogAspect.class);
    StringBuilder sb = new StringBuilder();
    @Before("execution(* com.nowcoder.controller.*Controller.*(..))")
    public void before(JoinPoint joinPoint)
    {
        for(Object obj:joinPoint.getArgs())
        {
            sb.append("arg:" + obj.toString() +"|");
        }
        System.out.print(sb);
        logger.info("before method");
    }
    @After("execution(* com.nowcoder.controller.*Controller.*(..))")
    public void after()
    {
        logger.info("after method");
    }

}
