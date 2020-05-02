package com.github.niupengyu.jdbc.dao;

import com.github.niupengyu.core.util.StringUtil;
import com.github.niupengyu.jdbc.bean.DataSourceBean;
import com.github.niupengyu.jdbc.bean.DbConfig;
import org.apache.commons.lang3.StringEscapeUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class ConnectionFactory {

    private Map<String,DataSourceBean> map=new HashMap<>();

    private static final Logger logger= LoggerFactory.getLogger(ConnectionFactory.class);

    public void init(DbConfig db) {
        if(db==null){
            return;
        }
        List<DataSourceBean> dataSourceBeans=db.getDataSources();
        if(StringUtil.listIsNull(dataSourceBeans)){
            return;
        }
        logger.info("simple datasource "+dataSourceBeans.size());
        for(DataSourceBean sourceBean:dataSourceBeans){
            System.out.println(sourceBean.getName());
            map.put(sourceBean.getName(),sourceBean);
        }
    }

    /*public JdbcUtil createJdbcUtil(String key) throws Exception {
        DataSourceBean sourceBean=map.get(key);
        String driver=sourceBean.getDriverClassName();
        String url=sourceBean.getUrl();
        String password=sourceBean.getPassword();
        String user=sourceBean.getUsername();
        Class.forName(driver);
        Connection conn= DriverManager.getConnection(url,user,password);
        return new JdbcUtil(conn);
    }*/

    public Connection createConn(String key) throws Exception {
        DataSourceBean sourceBean=map.get(key);
        String driver=sourceBean.getDriverClassName();
        String url=sourceBean.getUrl();
        String password=sourceBean.getPassword();
        String user=sourceBean.getUsername();
        Class.forName(driver);
        Connection conn= DriverManager.getConnection(url,user,password);
        return conn;
    }

    public DataSourceBean dataSourceBean(String key){
        return map.get(key);
    }

    public Map<String, DataSourceBean> dbMap() {
        return map;
    }
}
