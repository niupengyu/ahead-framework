package com.aheadframework.web;


import com.github.niupengyu.core.listener.ApplicationFailedListener;
import com.github.niupengyu.core.listener.ApplicationPreparedListener;
import com.github.niupengyu.core.listener.ReadyEvent;
import com.github.niupengyu.core.listener.StartListener;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.jdbc.DataSourceAutoConfiguration;
import org.springframework.boot.autoconfigure.jdbc.DataSourceTransactionManagerAutoConfiguration;
import org.springframework.boot.autoconfigure.orm.jpa.HibernateJpaAutoConfiguration;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.web.bind.annotation.RequestMapping;

//import org.springframework.boot.web.support.SpringBootServletInitializer;

@SpringBootApplication(exclude ={
        DataSourceAutoConfiguration.class,
        DataSourceTransactionManagerAutoConfiguration.class,
        HibernateJpaAutoConfiguration.class
})
@ComponentScan({"com.github.niupengyu"})
public class WebStarter /*extends SpringBootServletInitializer*/ {
    public static void main(String[] args) {
        SpringApplication app = new SpringApplication(WebStarter.class);
        app.addListeners(new ApplicationFailedListener(), new ReadyEvent(),
                new ApplicationPreparedListener(),new StartListener());
        app.run(args);
    }

    @RequestMapping("test")
    public String test(){
        return  "";
    }

    /*@Override
    protected SpringApplicationBuilder configure(SpringApplicationBuilder builder) {
        builder.listeners(new ApplicationFailedListener(), new ReadyEvent(),
                new ApplicationPreparedListener(),new StartListener());
        return builder.sources(WebStarter.class);
    }*/
}