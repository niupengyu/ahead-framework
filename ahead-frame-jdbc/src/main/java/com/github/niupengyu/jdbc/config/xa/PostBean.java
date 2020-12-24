package com.github.niupengyu.jdbc.config.xa;

import com.github.niupengyu.jdbc.bean.DataSourceBean;
import com.github.niupengyu.jdbc.datasource.DataSourceManager;
import com.github.niupengyu.jdbc.util.BeanFactory;
import com.github.niupengyu.jdbc.util.DataSourceFactory;
import com.github.niupengyu.jdbc.util.RegisterUtil;
import com.alibaba.druid.pool.xa.DruidXADataSource;
import com.github.niupengyu.core.annotation.AutoConfig;
import com.github.niupengyu.core.filter.FileFilterForName;
import com.github.niupengyu.core.util.Content;
import com.github.niupengyu.core.util.FileUtil;
import com.github.niupengyu.core.util.StringUtil;
import com.github.niupengyu.jdbc.bean.DbConfig;
import com.github.niupengyu.jdbc.util.ResourcesUtil;
import org.mybatis.spring.SqlSessionTemplate;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.support.BeanDefinitionBuilder;
import org.springframework.beans.factory.support.BeanDefinitionRegistry;
//import org.springframework.boot.bind.RelaxedPropertyResolver;
import org.springframework.boot.context.properties.bind.BindResult;
import org.springframework.boot.context.properties.bind.Bindable;
import org.springframework.boot.context.properties.bind.Binder;
import org.springframework.boot.jta.atomikos.AtomikosDataSourceBean;
import org.springframework.context.EnvironmentAware;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.ImportBeanDefinitionRegistrar;
import org.springframework.core.env.Environment;
import org.springframework.core.io.Resource;
import org.springframework.core.type.AnnotationMetadata;

import java.util.*;

//@Configuration
//@AutoConfig(name = "news.xaJdbc.enable")
public class PostBean
        implements ImportBeanDefinitionRegistrar, EnvironmentAware {

    DbConfig dbConfig=new DbConfig();

    private Logger logger= LoggerFactory.getLogger(PostBean.class);

    @Override
    public void setEnvironment(Environment environment) {
        Binder binder = Binder.get(environment);
        BindResult<DbConfig> bindResult=binder.bind("dbcp", Bindable.of(DbConfig.class));
        if(bindResult.isBound()){
            dbConfig = bindResult.get();
        }
        //dbConfig = bindResult.get();
    }

   /* @Override
    public void setEnvironment(Environment environment) {

        RelaxedPropertyResolver propertyResolver = new RelaxedPropertyResolver(environment,"dbcp.");
        int i=0;
        String driverClassName=propertyResolver.getProperty("driverClassName");

        List<DataSource> dataSources=new ArrayList<>();
        while(true){
            RelaxedPropertyResolver list = new RelaxedPropertyResolver(propertyResolver,"dataSources["+i+"].");
            if(StringUtil.isNull(list.getProperty("name"))){
                break;
            }
            DataSource dataSource=new DataSource();
            dataSource.setDriverClassName(list.getProperty("driverClassName",driverClassName));
            dataSource.setMappers(mappers(propertyResolver,"dataSources["+i+"].mappers"));
            dataSource.setName(list.getProperty("name"));
            dataSource.setUrl(list.getProperty("url"));
            dataSource.setPassword(list.getProperty("password"));
            dataSource.setUsername(list.getProperty("username"));
            dataSource.setInitialSize(getProperty(list,propertyResolver,"initialSize",Integer.TYPE));
            dataSource.setMaxActive(getProperty(list,propertyResolver,"maxActive",Integer.TYPE));
            dataSource.setMaxIdle(getProperty(list,propertyResolver,"maxIdle",Integer.TYPE));
            dataSource.setMaxWait(getProperty(list,propertyResolver,"maxWait",Long.TYPE));
            dataSource.setMinEvictableIdleTimeMillis(getProperty(list,propertyResolver,"minEvictableIdleTimeMillis",Integer.TYPE));
            dataSource.setMinIdle(getProperty(list,propertyResolver,"minIdle",Integer.TYPE));
            dataSource.setNumTestsPerEvictionRun(getProperty(list,propertyResolver,"numTestsPerEvictionRun",Integer.TYPE));
            dataSource.setRemoveAbandoned(getProperty(list,propertyResolver,"removeAbandoned",Boolean.TYPE));
            dataSource.setRemoveAbandonedTimeout(getProperty(list,propertyResolver,"removeAbandonedTimeout",Integer.TYPE));
            dataSource.setTestOnBorrow(getProperty(list,propertyResolver,"testOnBorrow",Boolean.TYPE));
            dataSource.setTestWhileIdle(getProperty(list,propertyResolver,"testWhileIdle",Boolean.TYPE));
            dataSource.setTimeBetweenEvictionRunsMillis(getProperty(list,propertyResolver,"timeBetweenEvictionRunsMillis",Integer.TYPE));
            dataSource.setValidationQuery(getProperty(list,propertyResolver,"validationQuery"));
            dataSource.setValidationQueryTimeout(getProperty(list,propertyResolver,"validationQueryTimeout",Integer.TYPE));
            dataSources.add(dataSource);
            i++;
        }

        dbConfig.setDataSources(dataSources);
    }

    private <T> T getProperty(RelaxedPropertyResolver list,
                              RelaxedPropertyResolver defaults,
                              String key,Class<T> targetType) {
        return list.getProperty(key,targetType,defaults.getProperty(key,targetType));
    }

    private String getProperty(RelaxedPropertyResolver list,
                              RelaxedPropertyResolver defaults,
                              String key) {
        return list.getProperty(key,defaults.getProperty(key));
    }


    private List<String> mappers(RelaxedPropertyResolver list,String s) {
        int i=0;
        List<String> mappers=new ArrayList<>();
        while(true) {
            String prop=list.getProperty(s+"["+i+"]");
            if(StringUtil.isNull(prop)){
                break;
            }
            mappers.add(prop);
            i++;
        }
        return mappers;
    }*/


    @Override
    public void registerBeanDefinitions(AnnotationMetadata annotationMetadata, BeanDefinitionRegistry beanDefinitionRegistry) {

        List<DataSourceBean> dataSources=dbConfig.getDataSources();
        if(StringUtil.listIsNull(dataSources)){
            logger.info("没有发现数据源配置");
            return;
        }
        logger.info("数据源数量 "+dataSources.size());
        try {
            for (DataSourceBean ds:dataSources) {
                BeanDefinitionBuilder beanDefinitionBuilder = BeanDefinitionBuilder
                        .genericBeanDefinition(SqlSessionTemplate.class);

                String dsName=ds.getName();
                String dsBeanName=dsName+"DataSource";
                String myBatisConfiguration=dsName+"Configuration";
                String myBatisFactoryName=dsName+"Factory";
                String myBatisScanner=dsName+"mc";

                BeanDefinitionBuilder dataSourceBean = DataSourceFactory.getXaDataSource(ds,dbConfig);
                BeanFactory beanFactory=new BeanFactory();

                List<String> mappers=StringUtil.valueOf(ds.getMappers(),dbConfig.getMappers());
                BeanDefinitionBuilder mc=beanFactory.createMapperScanner(mappers,dsName);

                BeanDefinitionBuilder sqlSessionFactoryBean=beanFactory.registSqlSessionFactory(dsBeanName,dbConfig,ds);

                beanDefinitionRegistry .registerBeanDefinition(dsBeanName,dataSourceBean.getRawBeanDefinition());

                beanDefinitionRegistry.registerBeanDefinition(myBatisConfiguration,
                        beanFactory.createConfiguration(dbConfig,ds).getRawBeanDefinition());


                beanDefinitionRegistry.registerBeanDefinition(myBatisFactoryName,sqlSessionFactoryBean.getRawBeanDefinition());
                beanDefinitionRegistry.registerBeanDefinition(myBatisScanner,mc.getRawBeanDefinition());

                beanDefinitionBuilder.addConstructorArgReference(myBatisFactoryName);
                beanDefinitionRegistry.registerBeanDefinition(dsName,beanDefinitionBuilder.getRawBeanDefinition());
            }
        } catch (Exception e) {
            e.printStackTrace();
        }

    }

 /*   private static void registerMapperScanner(BeanDefinitionRegistry beanDefinitionRegistry, StringBuilder sb,
                                       String dsName) {

        BeanDefinitionBuilder mapperScannerConfigurerBuilder = BeanDefinitionBuilder
                .genericBeanDefinition(MapperScannerConfigurer.class);
        mapperScannerConfigurerBuilder.addPropertyValue("sqlSessionTemplateBeanName",dsName);
        mapperScannerConfigurerBuilder.addPropertyValue("basePackage",sb.toString());
        beanDefinitionRegistry.registerBeanDefinition
                (dsName+"mc",mapperScannerConfigurerBuilder.getRawBeanDefinition());

    }*/




}
