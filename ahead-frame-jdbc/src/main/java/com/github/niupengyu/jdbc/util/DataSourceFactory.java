package com.github.niupengyu.jdbc.util;

import com.github.niupengyu.core.util.StringUtil;
import com.github.niupengyu.jdbc.bean.DataSourceBean;
import com.github.niupengyu.jdbc.bean.DbConfig;
import com.github.niupengyu.jdbc.dao.JdbcDao;
import com.github.niupengyu.jdbc.datasource.BasicDataSource;
import com.github.niupengyu.jdbc.datasource.SingleDataSource;
import com.alibaba.druid.pool.xa.DruidXADataSource;
import com.alibaba.fastjson.JSONObject;
import org.mybatis.spring.SqlSessionFactoryBean;
import org.mybatis.spring.SqlSessionTemplate;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.support.BeanDefinitionBuilder;
import org.springframework.boot.jta.atomikos.AtomikosDataSourceBean;

import javax.sql.DataSource;
import java.util.Map;

public class DataSourceFactory {

    private static final Logger logger=
            LoggerFactory.getLogger(DataSourceFactory.class);

    public static BeanDefinitionBuilder getXaDataSource(DataSourceBean value, DbConfig dbConfig) {


        DruidXADataSource druidXADataSource=BasicDataSource.createDruidXADataSource(value,dbConfig);
                /*new DruidXADataSource();
        druidXADataSource.setDriverClassName(
                StringUtil.valueOf(value.getDriverClassName(),dbConfig.getDriverClassName()));
        druidXADataSource.setUrl(
                StringUtil.valueOf(value.getUrl(),dbConfig.getUrl()));
        druidXADataSource.setUsername(
                StringUtil.valueOf(value.getUsername(),dbConfig.getUsername()));
        druidXADataSource.setPassword(
                StringUtil.valueOf(value.getPassword(),dbConfig.getPassword()));
        druidXADataSource.setInitialSize(
                StringUtil.integerValueOf(value.getInitialSize(),dbConfig.getInitialSize()));
        druidXADataSource.setMaxActive(
                StringUtil.integerValueOf(value.getMaxActive(),dbConfig.getMaxActive()));
        druidXADataSource.setMinIdle(
                StringUtil.integerValueOf(value.getMinIdle(),dbConfig.getMinIdle()));
        druidXADataSource.setMaxWait(
                StringUtil.longValueOf(value.getMaxWait(),dbConfig.getMaxWait()));
        druidXADataSource.setValidationQuery(
                StringUtil.valueOf(value.getValidationQuery(),dbConfig.getValidationQuery()));
        druidXADataSource.setValidationQueryTimeout(
                StringUtil.integerValueOf(value.getValidationQueryTimeout(),dbConfig.getValidationQueryTimeout()));
        druidXADataSource.setTestWhileIdle(
                StringUtil.booleanValueOf(value.getTestWhileIdle(),dbConfig.getTestWhileIdle()));
        druidXADataSource.setTestOnBorrow(
                StringUtil.booleanValueOf(value.getTestOnBorrow(),dbConfig.getTestOnBorrow()));
        druidXADataSource.setTimeBetweenEvictionRunsMillis(
                StringUtil.integerValueOf(value.getTimeBetweenEvictionRunsMillis(),dbConfig.getTimeBetweenEvictionRunsMillis()));
        druidXADataSource.setRemoveAbandoned(
                StringUtil.booleanValueOf(value.getRemoveAbandoned(),dbConfig.getRemoveAbandoned()));
        druidXADataSource.setRemoveAbandonedTimeout(
                StringUtil.integerValueOf(value.getRemoveAbandonedTimeout(),dbConfig.getRemoveAbandonedTimeout()));*/

        BeanDefinitionBuilder beanDefinitionBuilder = BeanDefinitionBuilder
                .genericBeanDefinition(AtomikosDataSourceBean.class);
        beanDefinitionBuilder.addPropertyValue("xaDataSource",druidXADataSource);
        beanDefinitionBuilder.addPropertyValue("uniqueResourceName",value.getName());
        beanDefinitionBuilder.addPropertyValue("testQuery",
                StringUtil.valueOf(value.getValidationQuery(),dbConfig.getValidationQuery())
        );
        beanDefinitionBuilder.addPropertyValue("maxPoolSize",
                StringUtil.integerValueOf(value.getMaxPoolSize(),dbConfig.getMaxPoolSize())
        );
        beanDefinitionBuilder.addPropertyValue("minPoolSize",
                StringUtil.integerValueOf(value.getMinPoolSize(),dbConfig.getMinPoolSize())
        );
        beanDefinitionBuilder.addPropertyValue("borrowConnectionTimeout",
                StringUtil.integerValueOf(value.getBorrowConnectionTimeout(),dbConfig.getBorrowConnectionTimeout())
        );



        return beanDefinitionBuilder;
    }

    public static BeanDefinitionBuilder getDataSource(DataSourceBean value,DbConfig dbConfig) {
        Boolean pool=StringUtil.booleanValueOf(value.getPool(),true);
        String className=value.getClassName();
        if(className!=null&&!className.isEmpty()){
            BeanDefinitionBuilder beanDefinitionBuilder=BeanDefinitionBuilder.genericBeanDefinition(className);
            Map<String,Object> prop=StringUtil.mapValueOf(value.getProp(),dbConfig.getProp());
            for(Map.Entry<String,Object> entry:prop.entrySet()){
                beanDefinitionBuilder.addPropertyValue(entry.getKey(),entry.getValue());
            }
            return beanDefinitionBuilder;
        }
        if(pool){
            Boolean transaction=StringUtil.booleanValueOf(value.getTransaction(),dbConfig.getTransaction(),true);
            System.out.println("transaction "+transaction);
            if(transaction){
                return getXaDataSource(value,dbConfig);
            }else{
                BeanDefinitionBuilder beanDefinitionBuilder=
                        BasicDataSource.initBean(dbConfig,value);
                return beanDefinitionBuilder;
            }
        }else{
            BeanDefinitionBuilder beanDefinitionBuilder=BeanDefinitionBuilder.genericBeanDefinition(SingleDataSource.class);
            beanDefinitionBuilder.addConstructorArgValue(dbConfig);
            beanDefinitionBuilder.addConstructorArgValue(value);
            return beanDefinitionBuilder;
        }
    }


    public static BasicDataSource basicDataSource(DataSourceBean dataSource,DbConfig db) {
        BasicDataSource basicDataSource=new BasicDataSource();
		basicDataSource.init(db,dataSource);
		return basicDataSource;
    }

    public static DataSource getDefaultSource(DataSourceBean value,DbConfig dbConfig) throws Exception {
        return new SingleDataSource(dbConfig,value);
    }

    public static void main(String[] args) {

    }

    /*public static String dataSourceClass(JSONObject dataSource, JSONObject mapConfig) {
        String className=dataSource.getString("className");
        if(className!=null&&!className.isEmpty()){
            return className;
        }
        Boolean pool=StringUtil.booleanValueOf(dataSource.getBoolean("pool"),
                mapConfig.getBooleanValue("pool"),false);
        if(pool){

            JSONObject transaction=dataSource.getJSONObject("transaction");

            Boolean transaction=StringUtil.booleanValueOf(dataSource.getBoolean("transaction"),
                    mapConfig.getBooleanValue("transaction"),true);
            if (transaction) {
                return xa
            }
        }
        return null;
    }*/
}
