package com.github.niupengyu.jdbc.datasource;

import javax.sql.DataSource;
import java.util.HashMap;
import java.util.Map;

public class DataSourceManager {

    private static Map<String,DataSource> dataSourceMap=new HashMap<>();

    public static void put(String key,DataSource dataSource){
        dataSourceMap.put(key,dataSource);
    }

    public static DataSource get(String key){
        return dataSourceMap.get(key);
    }
}
