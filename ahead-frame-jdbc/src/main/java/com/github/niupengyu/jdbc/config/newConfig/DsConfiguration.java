package com.github.niupengyu.jdbc.config.newConfig;

import com.github.niupengyu.jdbc.bean.DbConfig;
import com.github.niupengyu.jdbc.dao.ConnectionFactory;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Import;

import javax.annotation.Resource;

@Configuration
//@Import(DataSourceConfig.class)
public class DsConfiguration {

    @Resource(name="db")
    private DbConfig db;


    @Bean("connectionFactory")
    public ConnectionFactory connectionFactory() {
        ConnectionFactory connectionFactory=new ConnectionFactory();
        connectionFactory.init(db);
        return connectionFactory;
    }

}
