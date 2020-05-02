package com.github.niupengyu.web.service.impl;

import com.github.niupengyu.web.service.LoginValidateService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.boot.autoconfigure.condition.ConditionalOnMissingBean;
import org.springframework.stereotype.Service;

import javax.servlet.http.HttpServletRequest;


public class LoginValidateServiceImpl implements LoginValidateService {

    private static final Logger logger = LoggerFactory.getLogger(LoginValidateServiceImpl.class);

    public LoginValidateServiceImpl(){
        logger.info("加载了默认登录验证");
    }

    @Override
    public <T> T loginValidate(HttpServletRequest request) throws Exception {


        return null;
    }
}

