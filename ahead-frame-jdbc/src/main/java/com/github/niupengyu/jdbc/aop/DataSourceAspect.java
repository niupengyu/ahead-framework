package com.github.niupengyu.jdbc.aop;

import com.github.niupengyu.jdbc.annotation.DataSourceManager;
import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.*;
import org.slf4j.Logger; import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;

@Aspect
@Component
public class DataSourceAspect {
    final static Logger logger = LoggerFactory.getLogger(DataSourceAspect.class);
    ThreadLocal<Long> beginTime = new ThreadLocal<>();


    @Pointcut("@annotation(dataSourceManager)")
    public void serviceStatistics( DataSourceManager dataSourceManager) {
        logger.info("init DataSourceManager Aspect");
    }

    @Around("serviceStatistics(dataSourceManager)")
    public Object around(JoinPoint joinPoint1, DataSourceManager dataSourceManager) {
        long start = System.currentTimeMillis();
        Object object = null;
        ProceedingJoinPoint joinPoint=(ProceedingJoinPoint) joinPoint1;
        try {

            object = joinPoint.proceed();
            long end = System.currentTimeMillis();
            logger.info(this.getClass().getName()+" around " + joinPoint
                    + "\tUse time : " + (end - start) + " ms!");
        } catch (Throwable e) {
            long end = System.currentTimeMillis();
            logger.info(this.getClass().getName()+" around " + joinPoint
                    + "\tUse time : " + (end - start) + " ms with exception : " + e.getMessage());
            e.printStackTrace();
        }finally{
        }
        long end = System.currentTimeMillis();
        logger.info("DataSourceAspect around " + joinPoint
                + "\tUse time : " + (end - start));

        return object;
    }

}

