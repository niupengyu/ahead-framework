package com.github.niupengyu.web.service.impl;

import com.github.niupengyu.web.service.LoginValidateService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;


public class LoginValidateServiceImpl implements LoginValidateService {

    private static final Logger logger = LoggerFactory.getLogger(LoginValidateServiceImpl.class);

    public LoginValidateServiceImpl(){
        logger.info("加载了默认登录验证");
    }

    @Override
    public <T> T loginValidate(HttpServletRequest request, HttpServletResponse response, boolean rest) throws Exception {


        return null;
    }
}

