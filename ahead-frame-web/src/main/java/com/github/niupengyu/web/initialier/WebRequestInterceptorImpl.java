/**
 * 文件名: WebRequestInterceptorImpl.java
 * 包路径: com.github.niupengyu.web.initialier
 * 创建描述
 *
 * @createPerson：牛鹏宇
 * @createDate：2017年8月15日 下午2:28:06 内容描述： 修改描述
 * @updatePerson：牛鹏宇
 * @updateDate：2017年8月15日 下午2:28:06 修改内容: 版本: V1.0
 */
package com.github.niupengyu.web.initialier;

import com.github.niupengyu.core.exception.SysException;
import com.github.niupengyu.web.annotation.Authorization;
import com.github.niupengyu.web.annotation.LoginValidate;
import com.github.niupengyu.web.content.RequestContent;
import com.github.niupengyu.web.service.AuthorizationService;
import com.github.niupengyu.web.service.LoginValidateService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.method.HandlerMethod;
import org.springframework.web.servlet.handler.HandlerInterceptorAdapter;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

public class WebRequestInterceptorImpl extends HandlerInterceptorAdapter {

    private static final Logger logger = LoggerFactory.getLogger(WebRequestInterceptorImpl.class);

    @Autowired
    private AuthorizationService authorizationService;

    @Autowired
    private LoginValidateService loginValidateService;

    @Override
    public boolean preHandle(HttpServletRequest request,
                             HttpServletResponse response, Object handler) throws Exception {
        //配置全局session request response
        if (handler instanceof HandlerMethod) {
            HandlerMethod dwe = (HandlerMethod) handler;
            if (dwe.getBean() instanceof RequestContent) {
                RequestContent cc = (RequestContent) dwe.getBean();

                LoginValidate loginValidate = dwe.getMethodAnnotation(LoginValidate.class);
                if (loginValidate == null || loginValidate.value()) {
                    Object login = loginValidateService.loginValidate(request);
                    cc.initLogin(login);
                }

                Authorization authorization = dwe.getMethodAnnotation(Authorization.class);
                if (authorization == null || authorization.value()) {
                    authorizationService.authorization(request);
                }

            } else {
                logger.warn("控制器" + dwe.getBean().getClass().getName()
                        + "或许没有继承" + RequestContent.class.getName());
            }


        } else {
            logger.error("[1008]有请求跳过登录认证 " + handler.getClass());
            throw new SysException("请求非法", 401);
        }
        return super.preHandle(request, response, handler);
    }


}


