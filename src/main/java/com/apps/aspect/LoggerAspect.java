package com.apps.aspect;

import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Pointcut;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Before;
import org.aspectj.lang.annotation.After;
import org.aspectj.lang.annotation.AfterReturning;
import org.aspectj.lang.annotation.AfterThrowing;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;

import java.time.LocalTime;

@Aspect
@Component
public class LoggerAspect {

    private final Logger logger = LoggerFactory.getLogger(this.getClass());

    @Pointcut("execution(public * com.apps.*.*(String, Long))")
    public void firstPoint() {
    }

    @Pointcut("execution(public * com.apps.*.*(String))")
    public void secondPoint() {
    }

    @Around("firstPoint() || secondPoint()")
    public void getLogAround(ProceedingJoinPoint joinPoint) throws Throwable {
        LocalTime start = LocalTime.now();
        joinPoint.proceed();
        LocalTime end = LocalTime.now();
        logger.info("Method worked time: " + (end.getNano() - start.getNano()));
    }

    @Before("within(com.apps.*)")
    public void getLogBefore(JoinPoint joinPoint) {
        logger.info("Method " + joinPoint.getSignature() + " started!");
    }

    @After("within(com.apps.*)")
    public void getLogAfter(JoinPoint joinPoint) {
        logger.info("Method " + joinPoint.getSignature() + " finished!");
    }

    @AfterReturning(value = "within(com.apps.*)", returning = "whatWeWait")
    public void getLogAfterReturning(Object whatWeWait) {
        logger.info("Log after returning! " + whatWeWait);
    }

    @AfterThrowing(value = "within(com.apps.*)", throwing = "err")
    public void getLogAfterThrowing(Throwable err) {
        logger.warn("getLogAfterThrowing method" + err);
    }
}
