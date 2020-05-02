package com.github.niupengyu.web.service;

import javax.servlet.http.HttpServletRequest;

public interface LoginValidateService {

    public <T> T loginValidate(HttpServletRequest request) throws Exception;

}
