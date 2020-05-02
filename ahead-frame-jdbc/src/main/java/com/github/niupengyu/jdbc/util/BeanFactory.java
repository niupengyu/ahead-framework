package com.github.niupengyu.jdbc.util;

import com.github.niupengyu.core.util.Content;
import com.github.niupengyu.core.util.StringUtil;
import com.github.niupengyu.jdbc.bean.DataSourceBean;
import com.github.niupengyu.jdbc.bean.DbConfig;
import com.github.niupengyu.jdbc.handler.OracleClobTypeHandlerCallback;
import org.apache.ibatis.type.JdbcType;
import org.mybatis.spring.SqlSessionFactoryBean;
import org.mybatis.spring.mapper.MapperScannerConfigurer;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.support.BeanDefinitionBuilder;
import org.springframework.core.io.Resource;

import java.util.List;
import java.util.function.Supplier;

public class BeanFactory {

    private static final Logger logger= LoggerFactory.getLogger(BeanFactory.class);

    public BeanDefinitionBuilder registSqlSessionFactory(String dataSourceName,
                                                         DbConfig dbConfig, DataSourceBean ds) throws Exception {
        BeanDefinitionBuilder sqlSessionFactoryBean = BeanDefinitionBuilder
                .genericBeanDefinition(SqlSessionFactoryBean.class);
        sqlSessionFactoryBean.addPropertyReference("dataSource",dataSourceName);
        ResourcesUtil util=new ResourcesUtil();
        Resource[] resources=util.resources(ds,dbConfig);
        sqlSessionFactoryBean.addPropertyValue("mapperLocations",resources);

        sqlSessionFactoryBean.addPropertyReference("configuration",ds.getName()+"Configuration");
        return sqlSessionFactoryBean;
    }

    public BeanDefinitionBuilder createConfiguration(DbConfig dbConfig){

        BeanDefinitionBuilder configuration = BeanDefinitionBuilder
                .genericBeanDefinition(org.apache.ibatis.session.Configuration.class,
                        configurationSupplier(dbConfig.getMappers()));

        Boolean callSettersOnNulls=StringUtil.booleanValueOf(
                dbConfig.getCallSettersOnNulls(),true);

        Boolean useColumnLabel=StringUtil.booleanValueOf(
                dbConfig.getUseColumnLabel(),true);

        Boolean mapUnderscoreToCamelCase=StringUtil.booleanValueOf(
                dbConfig.getMapUnderscoreToCamelCase(),false);
        logger.info("callSettersOnNulls "+callSettersOnNulls +
                " useColumnLabel "+useColumnLabel+
                " mapUnderscoreToCamelCase "+mapUnderscoreToCamelCase);
        setConfigurationProp(configuration,callSettersOnNulls,useColumnLabel,mapUnderscoreToCamelCase);
        //TODO 分页插件
        return configuration;
    }

    public BeanDefinitionBuilder createConfiguration(DbConfig dbConfig, DataSourceBean ds){

        BeanDefinitionBuilder configuration = BeanDefinitionBuilder
                .genericBeanDefinition(org.apache.ibatis.session.Configuration.class);
        List<String> mappers=StringUtil.valueOf(ds.getMappers(),dbConfig.getMappers());
        configuration=BeanDefinitionBuilder.genericBeanDefinition(org.apache.ibatis.session.Configuration.class,
                configurationSupplier(mappers));
//        org.apache.ibatis.session.Configuration configuration1=
//                new org.apache.ibatis.session.Configuration();
//        configuration1.getTypeHandlerRegistry().register(JdbcType.CLOB, new OracleClobTypeHandlerCallback());
        Boolean callSettersOnNulls=StringUtil.booleanValueOf(ds.getCallSettersOnNulls(),
                dbConfig.getCallSettersOnNulls(),true);

        Boolean useColumnLabel=StringUtil.booleanValueOf(ds.getUseColumnLabel(),
                dbConfig.getUseColumnLabel(),true);

        Boolean mapUnderscoreToCamelCase=StringUtil.booleanValueOf(ds.getMapUnderscoreToCamelCase(),
                dbConfig.getMapUnderscoreToCamelCase(),false);

        logger.info("callSettersOnNulls "+callSettersOnNulls +
                " useColumnLabel "+useColumnLabel+
                " mapUnderscoreToCamelCase "+mapUnderscoreToCamelCase);

        setConfigurationProp(configuration,callSettersOnNulls,useColumnLabel,mapUnderscoreToCamelCase);
        return configuration;
    }

    public void setConfigurationProp(BeanDefinitionBuilder configuration,
                                     Boolean callSettersOnNulls,Boolean useColumnLabel,Boolean mapUnderscoreToCamelCase
                                     ){
        /*configuration.setCallSettersOnNulls(callSettersOnNulls);
        configuration.setUseColumnLabel(useColumnLabel);
        configuration.setMapUnderscoreToCamelCase(mapUnderscoreToCamelCase);*/
        configuration.addPropertyValue("callSettersOnNulls",callSettersOnNulls);
        configuration.addPropertyValue("useColumnLabel",useColumnLabel);
        configuration.addPropertyValue("mapUnderscoreToCamelCase",mapUnderscoreToCamelCase);

    }

    public BeanDefinitionBuilder createMapperScanner(List<String> mappers,String dsName) {
        BeanDefinitionBuilder mapperScannerConfigurerBuilder = BeanDefinitionBuilder
                .genericBeanDefinition(MapperScannerConfigurer.class);

        if(!StringUtil.listIsNull(mappers)){
            StringBuilder sb=new StringBuilder();
            for(String mapper:mappers){
                sb.append(Content.COMMA);
                sb.append(mapper);
            }
            if(sb.length()>0){
                sb.deleteCharAt(0);
                mapperScannerConfigurerBuilder.addPropertyValue("basePackage",sb.toString());
            }
        }
        mapperScannerConfigurerBuilder.addPropertyValue("sqlSessionTemplateBeanName",dsName);
        /*beanDefinitionRegistry.registerBeanDefinition
                (dsName+"mc",mapperScannerConfigurerBuilder.getRawBeanDefinition());*/
        return mapperScannerConfigurerBuilder;
    }

    private Supplier<org.apache.ibatis.session.Configuration> configurationSupplier(List<String> mappers){
       return new Supplier<org.apache.ibatis.session.Configuration>() {
            @Override
            public org.apache.ibatis.session.Configuration get() {
                org.apache.ibatis.session.Configuration configuration=
                        new org.apache.ibatis.session.Configuration();
                //configuration.getTypeHandlerRegistry().register(JdbcType.CLOB, new OracleClobTypeHandlerCallback());
                if(!StringUtil.listIsNull(mappers)){
                    for(String mapper:mappers){
                        configuration.addMappers(mapper);
                    }
                }
                return configuration;
            }
        };
    }
}
