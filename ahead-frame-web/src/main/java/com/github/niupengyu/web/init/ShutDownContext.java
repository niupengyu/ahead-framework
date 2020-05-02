package com.github.niupengyu.web.init;

import com.github.niupengyu.core.destory.DivDisposableBean;
import org.springframework.beans.BeansException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.SpringApplication;
import org.springframework.context.ApplicationContext;
import org.springframework.context.ApplicationContextAware;
import org.springframework.context.ConfigurableApplicationContext;
import org.springframework.stereotype.Component;

@Component("shutDownContext")
public class ShutDownContext{

    @Autowired
    private ApplicationContext context;;

    public void showdown(){
        if (null != context){
//            SpringApplication.exit(context,new DivDisposableBean());
        }
    }


}
