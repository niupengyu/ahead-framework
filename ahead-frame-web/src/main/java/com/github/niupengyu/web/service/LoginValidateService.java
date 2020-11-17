package com.github.niupengyu.web.service;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

public interface LoginValidateService {

    public <T> T loginValidate(HttpServletRequest request, HttpServletResponse response, boolean rest) throws Exception;

}
