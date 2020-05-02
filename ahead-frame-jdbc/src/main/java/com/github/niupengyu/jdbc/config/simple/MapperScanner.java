package com.github.niupengyu.jdbc.config.simple;

import com.github.niupengyu.core.annotation.AutoConfig;
import com.github.niupengyu.core.util.Content;
import org.mybatis.spring.mapper.MapperScannerConfigurer;
import org.springframework.boot.context.properties.bind.BindResult;
import org.springframework.boot.context.properties.bind.Bindable;
import org.springframework.boot.context.properties.bind.Binder;
import org.springframework.context.EnvironmentAware;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.env.Environment;

import java.util.ArrayList;
import java.util.List;

@Configuration
@AutoConfig(name = "news.defaultJdbc.enable")
public class MapperScanner implements EnvironmentAware {

    List<String> db=new ArrayList<>();

    @Override
    public void setEnvironment(Environment environment) {
        Binder binder = Binder.get(environment);
        BindResult<List> bindResult=binder.bind("dbcp.mappers", Bindable.of(List.class));
        if(bindResult.isBound()){
            db = bindResult.get();
        }
    }

    public MapperScanner(){


    }

    @Bean("mapperScannerConfigurer")
    public MapperScannerConfigurer mapperScannerConfigurer(){
        MapperScannerConfigurer mapperScannerConfigurer=new MapperScannerConfigurer();
        mapperScannerConfigurer.setSqlSessionTemplateBeanName("sqlSessionTemplate");
        if(db!=null){
            List<String> mappers=db;
            if(mappers!=null){
                StringBuilder sb=new StringBuilder();
                for(String mapper:mappers){
                    sb.append(Content.COMMA);
                    sb.append(mapper);
                }
                if(sb.length()>0){
                    sb.deleteCharAt(0);
                    mapperScannerConfigurer.setBasePackage(sb.toString());
                }
            }
        }
        return mapperScannerConfigurer;
    }

}
