package com.github.niupengyu.jdbc.util;

import com.github.niupengyu.core.util.Content;
import com.github.niupengyu.core.util.StringUtil;
import com.github.niupengyu.jdbc.bean.DataSourceBean;
import com.github.niupengyu.jdbc.bean.DbConfig;
import com.github.niupengyu.jdbc.bean.MybatisConfiguration;
import com.github.niupengyu.jdbc.bean.MybatisInterceptor;
import org.apache.ibatis.plugin.Interceptor;
import org.apache.ibatis.session.Configuration;
import org.mybatis.spring.SqlSessionFactoryBean;
import org.mybatis.spring.mapper.MapperScannerConfigurer;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.support.BeanDefinitionBuilder;
import org.springframework.core.io.Resource;

import java.util.List;
import java.util.Map;
import java.util.Properties;
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
                        configurationSupplier(dbConfig.getMappers(), null, dbConfig));

        Boolean callSettersOnNulls=StringUtil.booleanValueOf(
                dbConfig.getCallSettersOnNulls(),true);

        Boolean useColumnLabel=StringUtil.booleanValueOf(
                dbConfig.getUseColumnLabel(),true);

        Boolean mapUnderscoreToCamelCase=StringUtil.booleanValueOf(
                dbConfig.getMapUnderscoreToCamelCase(),false);
        logger.info("callSettersOnNulls "+callSettersOnNulls +
                " useColumnLabel "+useColumnLabel+
                " mapUnderscoreToCamelCase "+mapUnderscoreToCamelCase);
        setConfigurationProp(configuration,callSettersOnNulls,useColumnLabel,mapUnderscoreToCamelCase, null, dbConfig);
        //TODO 分页插件
        return configuration;
    }

    public BeanDefinitionBuilder createConfiguration(DbConfig dbConfig, DataSourceBean ds){

        BeanDefinitionBuilder configuration = BeanDefinitionBuilder
                .genericBeanDefinition(org.apache.ibatis.session.Configuration.class);
        List<String> mappers=StringUtil.valueOf(ds.getMappers(),dbConfig.getMappers());
        configuration=BeanDefinitionBuilder.genericBeanDefinition(org.apache.ibatis.session.Configuration.class,
                configurationSupplier(mappers,ds,dbConfig));
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

        setConfigurationProp(configuration,callSettersOnNulls,useColumnLabel,mapUnderscoreToCamelCase,ds,dbConfig);
        return configuration;
    }

    public void setConfigurationProp(BeanDefinitionBuilder configuration,
                                     Boolean callSettersOnNulls, Boolean useColumnLabel, Boolean mapUnderscoreToCamelCase,
                                     DataSourceBean ds, DbConfig dbConfig){
        /*configuration.setCallSettersOnNulls(callSettersOnNulls);
        configuration.setUseColumnLabel(useColumnLabel);
        configuration.setMapUnderscoreToCamelCase(mapUnderscoreToCamelCase);*/
        configuration.addPropertyValue("callSettersOnNulls",callSettersOnNulls);
        configuration.addPropertyValue("useColumnLabel",useColumnLabel);
        configuration.addPropertyValue("mapUnderscoreToCamelCase",mapUnderscoreToCamelCase);
        MybatisConfiguration mybatisConfiguration=null;
        if(dbConfig!=null){
            mybatisConfiguration=dbConfig.getConfiguration();
        }
        if(ds!=null&&mybatisConfiguration==null){
            mybatisConfiguration=ds.getConfiguration();
        }
        if(mybatisConfiguration!=null){
            Map<String,Object> prop=mybatisConfiguration.getProp();
            for(Map.Entry<String,Object> entry:prop.entrySet()){
                configuration.addPropertyValue(entry.getKey(),entry.getValue());
            }
        }
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

    private Supplier<Configuration>
    configurationSupplier(List<String> mappers,
                                                                                    DataSourceBean ds, DbConfig dbConfig){
       return new Supplier<Configuration>() {
            @Override
            public Configuration get() {
                Configuration configuration=
                        new Configuration();
                //configuration.getTypeHandlerRegistry().register(JdbcType.CLOB, new OracleClobTypeHandlerCallback());
                if(!StringUtil.listIsNull(mappers)){
                    for(String mapper:mappers){
                        configuration.addMappers(mapper);
                    }
                }
                MybatisConfiguration mybatisConfiguration=null;
                if(dbConfig!=null){
                    mybatisConfiguration=dbConfig.getConfiguration();
                }
                if(ds!=null&&mybatisConfiguration==null){
                    mybatisConfiguration=ds.getConfiguration();
                }
                if(mybatisConfiguration!=null){
                    MybatisInterceptor[] interceptors=mybatisConfiguration.getInterceptor();
                    for(MybatisInterceptor interceptor:interceptors){
                        Interceptor mi= null;
                        String className=interceptor.getClassName();
                        if(StringUtil.isNull(className)){
                            continue;
                        }
                        try {
                            mi = (Interceptor) Class.forName(className).newInstance();
                        } catch (Exception e) {
                            e.printStackTrace();
                        }
                        Properties prop=new Properties();
                        Map<String,Object> props=interceptor.getProp();
                        for(Map.Entry<String,Object> entry:props.entrySet()){
                            prop.put(entry.getKey(),entry.getValue());
                        }
                        mi.setProperties(prop);
                        configuration.addInterceptor(mi);
                    }
                }
                return configuration;
            }
        };
    }
}
