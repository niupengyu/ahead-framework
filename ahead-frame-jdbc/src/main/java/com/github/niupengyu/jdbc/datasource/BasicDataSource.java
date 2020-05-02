/**
 * 文件名: BasicDataSource.java
 * 包路径: cn.newsframework.aop.factory
 * 创建描述
 *
 * @createPerson：牛鹏宇
 * @createDate：2016-11-28 下午5:03:44 内容描述： 修改描述
 * @updatePerson：牛鹏宇
 * @updateDate：2016-11-28 下午5:03:44 修改内容: 版本: V1.0
 */
package com.github.niupengyu.jdbc.datasource;

import com.github.niupengyu.core.util.StringUtil;
import com.github.niupengyu.jdbc.bean.DataSourceBean;
import com.github.niupengyu.jdbc.bean.DbConfig;
import com.alibaba.druid.pool.DruidDataSource;
import com.alibaba.druid.pool.xa.DruidXADataSource;
import org.apache.commons.dbcp2.PoolableConnectionFactory;
import org.springframework.beans.factory.support.BeanDefinitionBuilder;

import javax.sql.DataSource;
import java.sql.Connection;
import java.sql.SQLException;
import java.util.logging.Logger;


public class BasicDataSource extends org.apache.commons.dbcp2.BasicDataSource {

    private static final Logger logger = Logger.getLogger("datasource");


    @Override
    protected DataSource createDataSource() throws SQLException {
        DataSource dataSource = super.createDataSource();
        return dataSource;
    }

    @Override
    protected void createConnectionPool(PoolableConnectionFactory factory) {
        super.createConnectionPool(factory);
    }

    public Connection getConnection() throws SQLException {
        Connection conn = super.getConnection();
        conn.setAutoCommit(DBContextHolder.getAutoCommit());
        return conn;
    }

    public void init(DbConfig db, int i) {
        DataSourceBean dataSource = db.getDataSources().get(i);
        init(db, dataSource);
    }

    public void init(DbConfig db, DataSourceBean dataSource) {

        this.setDriverClassName(
                StringUtil.valueOf(dataSource.getDriverClassName(), db.getDriverClassName()));
        this.setUrl(
                StringUtil.valueOf(dataSource.getUrl(), db.getUrl()));
        this.setUsername(
                StringUtil.valueOf(dataSource.getUsername(), db.getUsername()));
        this.setPassword(
                StringUtil.valueOf(dataSource.getPassword(), db.getPassword()));
        initPool(db, dataSource);
    }

    public void initPool(DbConfig db, DataSourceBean dataSource) {
        this.setInitialSize(
                StringUtil.integerValueOf(dataSource.getInitialSize(), db.getInitialSize()));
        this.setMaxTotal(
                StringUtil.integerValueOf(dataSource.getMaxActive(), db.getMaxActive()));
        this.setMaxIdle(
                StringUtil.integerValueOf(dataSource.getMaxIdle(), db.getMaxIdle()));
        this.setMinIdle(
                StringUtil.integerValueOf(dataSource.getMinIdle(), db.getMinIdle()));
        this.setMaxWaitMillis(
                StringUtil.longValueOf(dataSource.getMaxWait(), db.getMaxWait()));
        this.setValidationQuery(
                StringUtil.valueOf(dataSource.getValidationQuery(), db.getValidationQuery()));

        Integer validationQueryTimeout = StringUtil.integerValueOf(dataSource.getValidationQueryTimeout(), db.getValidationQueryTimeout());
        if (validationQueryTimeout != null) {
            this.setValidationQueryTimeout(validationQueryTimeout);
        }
        this.setTestWhileIdle(
                StringUtil.booleanValueOf(dataSource.getTestWhileIdle(), db.getTestWhileIdle()));
        this.setTestOnBorrow(
                StringUtil.booleanValueOf(dataSource.getTestOnBorrow(), db.getTestOnBorrow()));
        this.setTimeBetweenEvictionRunsMillis(
                StringUtil.integerValueOf(dataSource.getTimeBetweenEvictionRunsMillis(), db.getTimeBetweenEvictionRunsMillis()));
        this.setNumTestsPerEvictionRun(
                StringUtil.integerValueOf(dataSource.getNumTestsPerEvictionRun(), db.getNumTestsPerEvictionRun()));
        this.setMinEvictableIdleTimeMillis(
                StringUtil.integerValueOf(dataSource.getMinEvictableIdleTimeMillis(), db.getMinEvictableIdleTimeMillis()));
        this.setRemoveAbandonedOnBorrow(
                StringUtil.booleanValueOf(dataSource.getRemoveAbandoned(), db.getRemoveAbandoned()));
        this.setRemoveAbandonedTimeout(
                StringUtil.integerValueOf(dataSource.getRemoveAbandonedTimeout(), db.getRemoveAbandonedTimeout()));
    }


    public static Integer valueOf(Integer value, Integer defaults) {
        if (StringUtil.isNull(value)) {
            return defaults;
        }
        return value;
    }

    public static Long valueOf(Long value, Long defaults) {
        if (StringUtil.isNull(value)) {
            return defaults;
        }
        return value;
    }

    public static Boolean valueOf(Boolean value, Boolean defaults) {
        if (StringUtil.isNull(value)) {
            return defaults;
        }
        return value;
    }

    @Override
    public synchronized void close() throws SQLException {
        super.close();
    }

    public static BeanDefinitionBuilder initBean(DbConfig db, DataSourceBean dataSource) {
        BeanDefinitionBuilder beanDefinitionBuilder = BeanDefinitionBuilder
                .genericBeanDefinition(BasicDataSource.class);
        beanDefinitionBuilder.addPropertyValue("driverClassName",
                StringUtil.valueOf(dataSource.getDriverClassName(), db.getDriverClassName()));
        beanDefinitionBuilder.addPropertyValue("url",
                StringUtil.valueOf(dataSource.getUrl(), db.getUrl()));
        beanDefinitionBuilder.addPropertyValue("username",
                StringUtil.valueOf(dataSource.getUsername(), db.getUsername()));
        beanDefinitionBuilder.addPropertyValue("password",
                StringUtil.valueOf(dataSource.getPassword(), db.getPassword()));
        beanDefinitionBuilder.addPropertyValue("initialSize",
                StringUtil.integerValueOf(dataSource.getInitialSize(), db.getInitialSize(), 5));
        beanDefinitionBuilder.addPropertyValue("maxTotal",
                StringUtil.integerValueOf(dataSource.getMaxActive(), db.getMaxActive()));
        beanDefinitionBuilder.addPropertyValue("maxIdle",
                StringUtil.integerValueOf(dataSource.getMaxIdle(), db.getMaxIdle()));
        beanDefinitionBuilder.addPropertyValue("minIdle",
                StringUtil.integerValueOf(dataSource.getMinIdle(), db.getMinIdle()));
        beanDefinitionBuilder.addPropertyValue("maxWaitMillis",
                StringUtil.longValueOf(dataSource.getMaxWait(), db.getMaxWait()));
        beanDefinitionBuilder.addPropertyValue("validationQuery",
                StringUtil.valueOf(dataSource.getValidationQuery(), db.getValidationQuery()));

        Integer validationQueryTimeout = StringUtil.integerValueOf(dataSource.getValidationQueryTimeout(), db.getValidationQueryTimeout());
        if (validationQueryTimeout != null) {
            beanDefinitionBuilder.addPropertyValue("validationQueryTimeout", validationQueryTimeout);
        }

        beanDefinitionBuilder.addPropertyValue("testWhileIdle",
                StringUtil.booleanValueOf(dataSource.getTestWhileIdle(), db.getTestWhileIdle()));
        beanDefinitionBuilder.addPropertyValue("testOnBorrow",
                StringUtil.booleanValueOf(dataSource.getTestOnBorrow(), db.getTestOnBorrow()));
        beanDefinitionBuilder.addPropertyValue("timeBetweenEvictionRunsMillis",
                StringUtil.integerValueOf(dataSource.getTimeBetweenEvictionRunsMillis(), db.getTimeBetweenEvictionRunsMillis()));
        beanDefinitionBuilder.addPropertyValue("numTestsPerEvictionRun",
                StringUtil.integerValueOf(dataSource.getNumTestsPerEvictionRun(), db.getNumTestsPerEvictionRun()));
        beanDefinitionBuilder.addPropertyValue("minEvictableIdleTimeMillis",
                StringUtil.integerValueOf(dataSource.getMinEvictableIdleTimeMillis(), db.getMinEvictableIdleTimeMillis()));
        beanDefinitionBuilder.addPropertyValue("removeAbandonedOnBorrow",
                StringUtil.booleanValueOf(dataSource.getRemoveAbandoned(), db.getRemoveAbandoned()));
        beanDefinitionBuilder.addPropertyValue("removeAbandonedTimeout",
                StringUtil.integerValueOf(dataSource.getRemoveAbandonedTimeout(), db.getRemoveAbandonedTimeout()));
        return beanDefinitionBuilder;
    }

    public static DruidXADataSource createDruidXADataSource(DataSourceBean value, DbConfig dbConfig) {

        /*BeanDefinitionBuilder beanDefinitionBuilder = BeanDefinitionBuilder
                .genericBeanDefinition(DruidXADataSource.class);
        beanDefinitionBuilder.addPropertyValue()*/
        DruidXADataSource druidXADataSource = new DruidXADataSource();
        druidXADataSource.setDriverClassName(
                StringUtil.valueOf(value.getDriverClassName(), dbConfig.getDriverClassName()));
        druidXADataSource.setUrl(
                StringUtil.valueOf(value.getUrl(), dbConfig.getUrl()));
        druidXADataSource.setUsername(
                StringUtil.valueOf(value.getUsername(), dbConfig.getUsername()));
        druidXADataSource.setPassword(
                StringUtil.valueOf(value.getPassword(), dbConfig.getPassword()));
        initXaPool(druidXADataSource,value,dbConfig);
        return druidXADataSource;

    }

    public static void initXaPool(DruidDataSource druidDataSource, DataSourceBean value, DbConfig dbConfig) {
        druidDataSource.setInitialSize(
                StringUtil.integerValueOf(value.getInitialSize(), dbConfig.getInitialSize()));
        druidDataSource.setMaxActive(
                StringUtil.integerValueOf(value.getMaxActive(), dbConfig.getMaxActive()));
        druidDataSource.setMinIdle(
                StringUtil.integerValueOf(value.getMinIdle(), dbConfig.getMinIdle()));
        druidDataSource.setMaxWait(
                StringUtil.longValueOf(value.getMaxWait(), dbConfig.getMaxWait()));
        druidDataSource.setValidationQuery(
                StringUtil.valueOf(value.getValidationQuery(), dbConfig.getValidationQuery()));
        druidDataSource.setValidationQueryTimeout(
                StringUtil.integerValueOf(value.getValidationQueryTimeout(), dbConfig.getValidationQueryTimeout()));
        druidDataSource.setTestWhileIdle(
                StringUtil.booleanValueOf(value.getTestWhileIdle(), dbConfig.getTestWhileIdle()));
        druidDataSource.setTestOnBorrow(
                StringUtil.booleanValueOf(value.getTestOnBorrow(), dbConfig.getTestOnBorrow()));
        druidDataSource.setTimeBetweenEvictionRunsMillis(
                StringUtil.integerValueOf(value.getTimeBetweenEvictionRunsMillis(), dbConfig.getTimeBetweenEvictionRunsMillis()));
        druidDataSource.setRemoveAbandoned(
                StringUtil.booleanValueOf(value.getRemoveAbandoned(), dbConfig.getRemoveAbandoned()));
        druidDataSource.setRemoveAbandonedTimeout(
                StringUtil.integerValueOf(value.getRemoveAbandonedTimeout(), dbConfig.getRemoveAbandonedTimeout()));
    }
}


