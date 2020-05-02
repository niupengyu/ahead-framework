package com.github.niupengyu.jdbc.handler;

import java.io.IOException;
import java.io.Reader;
import java.sql.CallableStatement;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
  
  
import org.apache.ibatis.type.JdbcType;
import org.apache.ibatis.type.TypeHandler;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class OracleClobTypeHandlerCallback implements TypeHandler<Object> {

    private static final Logger logger =
            LoggerFactory.getLogger(OracleClobTypeHandlerCallback.class);

    public OracleClobTypeHandlerCallback(){
        logger.info("创建 OracleClobTypeHandlerCallback");
    }
     
   public Object valueOf(String param) {
       return null;
   }
   @Override
   public Object getResult(ResultSet rs, String columnName) throws SQLException {
       logger.info("getResult clob 处理2");
       java.sql.Clob s = rs.getClob(columnName);
        if (rs.wasNull()) {
            return "";
        } 
        else {
            String clobStr = "";
            Reader inStream = s.getCharacterStream();
            char[] c = new char[(int) s.length()];
            try {
                inStream.read(c);
                clobStr = new String(c);
                inStream.close();
            } 
            catch (IOException e) {
                e.printStackTrace();
            }
            return clobStr;
        }
   }
   @Override
   public Object getResult(ResultSet arg0, int arg1) throws SQLException {
       logger.info("getResult clob 处理1");
       return null;
   }
   @Override
   public Object getResult(CallableStatement arg0, int arg1) throws SQLException {
       logger.info("getResult clob 处理3");
       return null;
   }
   @Override
   public void setParameter(PreparedStatement arg0, int arg1, Object arg2, JdbcType arg3) throws SQLException {
       java.sql.Clob clob = null;
        try {
            logger.info("setParameter clob 处理");
            if (arg2 == null) {
                arg2 = "";
            }
            clob = new javax.sql.rowset.serial.SerialClob(arg2.toString().toCharArray());
        } 
        catch (Exception e) {
        }
        arg0.setClob(arg1, clob);
   }
}