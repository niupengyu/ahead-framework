package com.github.niupengyu.jdbc.dao;

import com.github.niupengyu.jdbc.exception.DaoException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.lang.reflect.InvocationHandler;
import java.lang.reflect.Method;
import java.lang.reflect.Proxy;

public class RetryJdbcDao implements InvocationHandler {

    private JdbcDao jdbcDao;

    private int count=5;

    private static final Logger logger= LoggerFactory.getLogger(RetryJdbcDao.class);

    public RetryJdbcDao(JdbcDao jdbcDao){
        this.jdbcDao=jdbcDao;
    }

    public RetryJdbcDao(JdbcDao jdbcDao, int count) {
        this.jdbcDao=jdbcDao;
        this.count=count;
    }

    @Override
    public Object invoke(Object proxy, Method method, Object[] args) throws Throwable {
        Object returnValue=null;
        int i=0;
        Exception exception=null;
        for(;i<count;i++){
            try{
                logger.info("执行 第 "+i+" 次");
                returnValue=method.invoke(jdbcDao,args);
                break;
            }catch (Exception e){
                exception=e;
            }
            Thread.sleep(5000l);
        }
        if(i>=count){
            logger.error("执行异常 ！"+i,exception);
            throw exception;
        }
        return returnValue;
    }

    public static void main(String[] args) throws DaoException {
        JdbcDao jdbcDao=new JdbcDao();
        JdbcDaoFace realSubjectProxy= JdbcDao.createRetryJdbcDao(jdbcDao,5);
        realSubjectProxy.getIntValue("");
    }
}
