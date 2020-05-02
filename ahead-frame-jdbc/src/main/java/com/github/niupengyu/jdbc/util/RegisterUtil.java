package com.github.niupengyu.jdbc.util;

import com.github.niupengyu.core.util.Content;
import com.github.niupengyu.core.util.StringUtil;
import org.apache.ibatis.session.Configuration;
import org.mybatis.spring.mapper.MapperScannerConfigurer;
import org.springframework.beans.factory.support.BeanDefinitionBuilder;
import org.springframework.beans.factory.support.BeanDefinitionRegistry;

import javax.sql.DataSource;
import java.util.List;

public class RegisterUtil {

    public static void registerMapperScanner(BeanDefinitionRegistry beanDefinitionRegistry, StringBuilder sb,
                                              String dsName) {
        BeanDefinitionBuilder mapperScannerConfigurerBuilder = BeanDefinitionBuilder
                .genericBeanDefinition(MapperScannerConfigurer.class);
        mapperScannerConfigurerBuilder.addPropertyValue("sqlSessionTemplateBeanName",dsName);
        mapperScannerConfigurerBuilder.addPropertyValue("basePackage",sb.toString());
        beanDefinitionRegistry.registerBeanDefinition
                (dsName+"mc",mapperScannerConfigurerBuilder.getRawBeanDefinition());

    }

    public static void setMapper(List<String> mappers, Configuration configuration,
                                 String dsName,BeanDefinitionRegistry beanDefinitionRegistry){
        if(!StringUtil.listIsNull(mappers)){
            StringBuilder sb=new StringBuilder();
            for(String mapper:mappers){
                configuration.addMappers(mapper);
                sb.append(Content.COMMA);
                sb.append(mapper);
            }
            if(sb.length()>0){
                sb.deleteCharAt(0);
                registerMapperScanner(beanDefinitionRegistry,sb,dsName);
            }
        }
    }

}
