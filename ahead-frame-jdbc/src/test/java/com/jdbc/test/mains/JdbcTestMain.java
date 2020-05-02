package com.jdbc.test.mains;


import com.github.niupengyu.core.listener.ApplicationFailedListener;
import com.github.niupengyu.core.listener.ApplicationPreparedListener;
import com.github.niupengyu.core.listener.ReadyEvent;
import com.github.niupengyu.core.listener.StartListener;
import org.mybatis.spring.boot.autoconfigure.MybatisAutoConfiguration;
import org.slf4j.LoggerFactory;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.jdbc.DataSourceAutoConfiguration;
import org.springframework.boot.autoconfigure.jdbc.DataSourceTransactionManagerAutoConfiguration;
import org.springframework.boot.autoconfigure.jdbc.XADataSourceAutoConfiguration;
import org.springframework.boot.autoconfigure.orm.jpa.HibernateJpaAutoConfiguration;
import org.springframework.context.annotation.ComponentScan;

import java.util.ArrayList;
import java.util.List;

@SpringBootApplication(exclude ={
        DataSourceAutoConfiguration.class,
        DataSourceTransactionManagerAutoConfiguration.class,
        HibernateJpaAutoConfiguration.class,
        MybatisAutoConfiguration.class,
        XADataSourceAutoConfiguration.class
})
@ComponentScan({"com.github.niupengyu","com.jdbc.test"})
public class JdbcTestMain implements CommandLineRunner {

    @Override
    public void run(String... strings) throws Exception {
        List list=new ArrayList<>();
    }

    public static void main(String[] args) {
        //LoggerContext loggerContext= (LoggerContext) LoggerFactory.getILoggerFactory();
        //loggerContext.getLogger("root").setLevel(Level.OFF);
        SpringApplication app = new SpringApplication(JdbcTestMain.class);
        app.addListeners(new ApplicationFailedListener(), new ReadyEvent(),
                new ApplicationPreparedListener(),new StartListener());
        app.run(args);
    }

}