//package com.eazybytes.example18.Aspects;
//
//import lombok.extern.slf4j.Slf4j;
//import org.aspectj.lang.JoinPoint;
//import org.aspectj.lang.ProceedingJoinPoint;
//import org.aspectj.lang.annotation.AfterThrowing;
//import org.aspectj.lang.annotation.Around;
//import org.aspectj.lang.annotation.Aspect;
//import org.springframework.stereotype.Component;
//import java.time.Duration;
//import java.time.Instant;
//
//@Slf4j
//@Aspect
//@Component
//public class LoggerAspect {
//
//    @Around("execution(* com.eazybytes.example18..*.*.(..))")
//    public Object log(ProceedingJoinPoint joinPoint) throws Throwable{
//        log.info(joinPoint.getSignature().toString()+"method execution start".toUpperCase());
//        Instant start = Instant.now();
//        Object returnObject=joinPoint.proceed();
//        Instant finish=Instant.now();
//        long elapsedTime= Duration.between(start,finish).toMillis();
//        log.info("Time took to  execute "+joinPoint.getSignature().toString()+"method is :".toUpperCase()+elapsedTime);
//        log.info(joinPoint.getSignature().toString()+"method execution end".toUpperCase());
//        return returnObject;
//    }
//
//    @AfterThrowing(value = "execution(* com.eazybytes.example18..*.*.(..))",throwing = "ex")
//    public void logException(JoinPoint joinPoint, Exception ex){
//        log.error(joinPoint.getSignature()+"an exception happened due to :".toUpperCase()+ex.getMessage());
//    }
//}
