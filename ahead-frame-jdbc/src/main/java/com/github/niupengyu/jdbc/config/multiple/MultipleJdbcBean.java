package com.github.niupengyu.jdbc.config.multiple;

import com.github.niupengyu.core.annotation.AutoConfig;
import com.github.niupengyu.core.util.StringUtil;
import com.github.niupengyu.jdbc.bean.DataSourceBean;
import com.github.niupengyu.jdbc.bean.DbConfig;
import com.github.niupengyu.jdbc.datasource.BasicDataSource;
import com.github.niupengyu.jdbc.datasource.DataSourceManager;
import com.github.niupengyu.jdbc.datasource.DynamicDataSource;
import com.github.niupengyu.jdbc.util.BeanFactory;
import com.github.niupengyu.jdbc.util.DataSourceFactory;
import com.github.niupengyu.jdbc.util.RegisterUtil;
import com.github.niupengyu.jdbc.util.ResourcesUtil;
import com.alibaba.druid.pool.xa.DruidXADataSource;
import org.mybatis.spring.SqlSessionFactoryBean;
import org.mybatis.spring.SqlSessionTemplate;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.support.BeanDefinitionBuilder;
import org.springframework.beans.factory.support.BeanDefinitionRegistry;
import org.springframework.boot.autoconfigure.condition.ConditionalOnBean;
import org.springframework.boot.context.properties.bind.BindResult;
import org.springframework.boot.context.properties.bind.Bindable;
import org.springframework.boot.context.properties.bind.Binder;
import org.springframework.boot.jta.atomikos.AtomikosDataSourceBean;
import org.springframework.context.EnvironmentAware;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.ImportBeanDefinitionRegistrar;
import org.springframework.core.annotation.Order;
import org.springframework.core.env.Environment;
import org.springframework.core.io.Resource;
import org.springframework.core.type.AnnotationMetadata;
import org.springframework.jdbc.datasource.DataSourceTransactionManager;
import org.springframework.transaction.PlatformTransactionManager;

import javax.sql.DataSource;
import javax.sql.XADataSource;
import java.util.ArrayList;
import java.util.List;

//import org.springframework.boot.bind.RelaxedPropertyResolver;

//@Configuration
//@AutoConfig(name = "news.multipleJdbc.enable")
public class MultipleJdbcBean
        implements ImportBeanDefinitionRegistrar, EnvironmentAware {

    DbConfig dbConfig=new DbConfig();

    private Logger logger = LoggerFactory.getLogger(MultipleJdbcBean.class);

    @Override
    public void setEnvironment(Environment environment) {
        Binder binder = Binder.get(environment);
        BindResult<DbConfig> bindResult=binder.bind("dbcp", Bindable.of(DbConfig.class));
        if(bindResult.isBound()){
            dbConfig = bindResult.get();
        }
    }



    @Override
    public void registerBeanDefinitions(AnnotationMetadata annotationMetadata, BeanDefinitionRegistry beanDefinitionRegistry) {
        List<DataSourceBean> dataSources=dbConfig.getDataSources();
        if(StringUtil.listIsNull(dataSources)){
            logger.info("Multiple 没有发现数据源配置");
            return;
        }
        logger.info("Multiple 数据源数量 "+dataSources.size());
        try {
            for (DataSourceBean ds:dataSources) {
                BeanDefinitionBuilder beanDefinitionBuilder = BeanDefinitionBuilder
                        .genericBeanDefinition(SqlSessionTemplate.class);

                String dsName=ds.getName();
                String dsBeanName=dsName+"DataSource";
                String myBatisConfiguration=dsName+"Configuration";
                String myBatisFactoryName=dsName+"Factory";
                String myBatisScanner=dsName+"mc";

                BeanDefinitionBuilder dataSourceBean = DataSourceFactory.getDataSource(ds,dbConfig);
                BeanFactory beanFactory=new BeanFactory();

                List<String> mappers=StringUtil.valueOf(ds.getMappers(),dbConfig.getMappers());
                BeanDefinitionBuilder mc=beanFactory.createMapperScanner(mappers,dsName);

                BeanDefinitionBuilder sqlSessionFactoryBean=beanFactory.registSqlSessionFactory(dsBeanName,dbConfig,ds);

                beanDefinitionRegistry.registerBeanDefinition(dsBeanName,dataSourceBean.getRawBeanDefinition());

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


}
