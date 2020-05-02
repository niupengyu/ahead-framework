package com.github.niupengyu.web.service.impl;

import com.github.niupengyu.web.initialier.WebRequestInterceptorImpl;
import com.github.niupengyu.web.service.AuthorizationService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.boot.autoconfigure.condition.ConditionalOnMissingBean;
import org.springframework.stereotype.Service;

import javax.servlet.http.HttpServletRequest;


public class AuthorizationServiceImpl implements AuthorizationService {

    private static final Logger logger = LoggerFactory.getLogger(AuthorizationServiceImpl.class);

    public AuthorizationServiceImpl(){
        logger.info("加载了默认权限验证");
    }

    @Override
    public void authorization(HttpServletRequest request) throws Exception {

    }


}