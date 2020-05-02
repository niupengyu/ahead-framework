package com.github.niupengyu.core.init;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.ApplicationArguments;
import org.springframework.boot.ApplicationRunner;
import org.springframework.boot.autoconfigure.condition.ConditionalOnBean;
import org.springframework.core.annotation.Order;
import org.springframework.stereotype.Component;

@Component
@Order(1)
@ConditionalOnBean(InitService.class)
public class DefaultInit implements ApplicationRunner {

    @Autowired
    private InitService initService;

    @Override
    public void run(ApplicationArguments applicationArguments) throws Exception {
        initService.run(applicationArguments);
    }
}
