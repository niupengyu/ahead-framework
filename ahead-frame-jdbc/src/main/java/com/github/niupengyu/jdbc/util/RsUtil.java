package com.github.niupengyu.jdbc.util;

import com.github.niupengyu.core.exception.SysException;
import com.github.niupengyu.core.util.ClassUtil;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.lang.reflect.Method;
import java.sql.ResultSet;
import java.sql.Timestamp;

public class RsUtil {

    private static Logger logger= LoggerFactory.getLogger(RsUtil.class);

    public static Object value(String type,String value) throws Exception {
        if("<空>".equals(value)){
            return null;
        }
        Object v=null;
        try{
            Method method=Value.class.getMethod("value"+type);
            v=method.invoke(new Value(value));
        }catch(Exception e){
            logger.error("取值异常 "+type+ " "+value+" "+e.getMessage(),e);
            throw new SysException(e.getMessage());
        }
        return v;
    }


    public static Object value(ResultSet rs, String type, String name) throws Exception {
        //logger.info("name: {} type: {} value: {} ",name,type,rs.getObject(name));
        Method method= ClassUtil.method(ResultSet.class,"get"+type,new Class[]{String.class});
        Object obj=method.invoke(rs,name);
        return obj;
    }

    public static void main(String[] args) throws Exception {

        Timestamp.valueOf("0000-00-00 00:00:00");

        System.out.println(RsUtil.value("Timestamp","0000-00-00 00:00:00"));
    }



}
