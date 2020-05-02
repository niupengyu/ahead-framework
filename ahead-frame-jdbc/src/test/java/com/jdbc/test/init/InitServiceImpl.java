package com.jdbc.test.init;

import com.github.niupengyu.core.init.InitService;
import com.jdbc.test.service.ServiceImpl;
import org.springframework.boot.ApplicationArguments;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;


@Service
public class InitServiceImpl implements InitService {

    @Resource(name = "serviceImpl")
    private ServiceImpl service;

    @Override
    public void run(ApplicationArguments applicationArguments) {
        //TODO
        System.out.println("run init.................");
        //System.out.println(mapConfig.getDbcp());
        service.test();
    }


}
