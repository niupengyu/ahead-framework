package com.github.niupengyu.jdbc.util;

import com.github.niupengyu.core.util.DateUtil;
import com.github.niupengyu.core.util.StringUtil;
import com.github.niupengyu.core.util.ThreadLocalDateUtil;
//import oracle.sql.DATE;
//import oracle.sql.TIMESTAMP;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.BufferedReader;
import java.io.IOException;
import java.math.BigDecimal;
import java.sql.Clob;
import java.sql.SQLException;
import java.sql.Timestamp;
import java.text.SimpleDateFormat;
import java.util.Date;

public class ColumnUtil {

    private static final Logger logger= LoggerFactory.getLogger(ColumnUtil.class);


    public static String clobToString(Clob clob) throws SQLException, IOException {
        if(StringUtil.isNull(clob)){
            return "";
        }
        String reString = "";
        java.io.Reader is = clob.getCharacterStream();// 得到流
        BufferedReader br = new BufferedReader(is);
        String s = br.readLine();
        StringBuffer sb = new StringBuffer();
        while (s != null) {// 执行循环将字符串全部取出付值给StringBuffer由StringBuffer转成STRING
            sb.append(s);
            s = br.readLine();
        }
        reString = sb.toString();
        return reString;
    }

    public static Object convertData(Object value) throws IOException, SQLException {
        Object value1=null;
        if(value!=null){
             //logger.info("column type"+value.getClass().getName());
            if(value instanceof Date){
                value1= ThreadLocalDateUtil.formatDate(value,1);
            }else if (value instanceof Timestamp) {
                //value1= ThreadLocalDateUtil.formatDate(new Date(((TIMESTAMP) value).dateValue().getTime()), 1);
                value1= ThreadLocalDateUtil.formatDate(value1, 1);
            } else if (value instanceof Clob) {
                value1 = ColumnUtil.clobToString((Clob) value);
            }else if(value instanceof BigDecimal){
                BigDecimal bigDecimal=BigDecimal.class.cast(value);
                int v1=bigDecimal.intValue();
                float v2=bigDecimal.floatValue();
                if(v2!=v1){
                    value1= v2;
                }else{
                    value1= v1;
                }
            }else if(value instanceof Integer){
                value1= Integer.class.cast(value);
            }else if(value instanceof Long){
                value1= Long.class.cast(value);
            }else if(value instanceof Float){
                value1= Float.class.cast(value);
            }else if(value instanceof Double){
                value1= Double.class.cast(value);
            }else {
                value1= StringUtil.valueOf(value);
            }
        }
        return value1;
    }

    /*public static String convertOracleDate(Object date) throws Exception {
        logger.info("formatTime "+date);
        if(date==null){
            return "";
        }
        logger.info("formatTime "+date.getClass().getName());
        if(date instanceof String){
            return StringUtil.valueOf(date);
        }
        if(date instanceof TIMESTAMP){
            date=((TIMESTAMP)date).timestampValue();
        }
        if(date instanceof DATE){
            date=((DATE)date).timestampValue();
        }
        return new SimpleDateFormat(DateUtil.FORMAT).format(date).toString();
    }*/

}
