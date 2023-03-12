package com.apps.aspect;

import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.*;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;

import java.time.LocalTime;

@Aspect
@Component
public class LoggerAspect {

    private final Logger log = LoggerFactory.getLogger(this.getClass());

    @Pointcut("execution(public * com.apps.*.*(String, Long))")
    public void f(){
    }

    @Pointcut("execution(public * com.apps.*.*(String))")
    public void ff(){
    }

    @Around("f() || ff()")
    public void getLogAround(ProceedingJoinPoint joinPoint) throws Throwable {
        LocalTime start = LocalTime.now();
        joinPoint.proceed();
        LocalTime end = LocalTime.now();
        log.info("Method worked time: " + (end.getNano() - start.getNano()));
    }

    @Before("within(com.apps.*)")
    public void getLogBefore(JoinPoint joinPoint) {
        log.info("Method " + joinPoint.getSignature() + " started!");
    }

    @After("within(com.apps.*)")
    public void getLogAfter(JoinPoint joinPoint) {
        log.info("Method " + joinPoint.getSignature() + " finished!");
    }

    @AfterReturning(value = "within(com.apps.*)", returning = "whatWeWait")
    public void getLogAfterReturning(Object whatWeWait) {
        log.info("Log after returning! " + whatWeWait);
    }

    @AfterThrowing(value = "within(com.apps.*)", throwing = "err")
    public void getLogAfterThrowing(Throwable err) {
        log.warn("getLogAfterThrowing method" + err);
    }
}
