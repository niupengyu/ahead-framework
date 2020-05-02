package com.github.niupengyu.web.config;

import com.github.niupengyu.core.util.StringUtil;
import com.github.niupengyu.web.beans.AllowOrigin;
import com.github.niupengyu.web.filter.AuthorityFilter;
import com.alibaba.fastjson.JSONArray;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.boot.web.servlet.FilterRegistrationBean;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import javax.annotation.Resource;
import java.util.HashSet;
import java.util.Set;

@Configuration
public class AllowOriginConfiguration {

    @Resource(name = "allowOrigin")
    private AllowOrigin allowOrigin;

    private static final Logger logger = LoggerFactory.getLogger(AllowOriginConfiguration.class);

    @Bean
    public FilterRegistrationBean authFilterRegistration() {
        FilterRegistrationBean registration = new FilterRegistrationBean(new AuthorityFilter());
        registration.addUrlPatterns("/*"); //
        if(allowOrigin!=null){
            /*registration.addInitParameter("allowOrigin",
                    StringUtil.valueOf(allowOrigin.getAllowOrigin(),""));*/ //
            Set<String> allowOrigins=allowOrigin.getAllowOrigins();
            System.out.println("allowOrigin "+allowOrigin);
            System.out.println("allowOrigins "+allowOrigins);
            if(allowOrigins!=null){
                registration.addInitParameter("allowOrigins",
                        StringUtil.valueOf(JSONArray.toJSONString(allowOrigins),"")); //
            }
            registration.addInitParameter("allowMethods",
                    StringUtil.valueOf(allowOrigin.getAllowMethods(),"")); //
            registration.addInitParameter("maxAge",
                    StringUtil.valueOf(allowOrigin.getMaxAge(),"")); //
            registration.addInitParameter("allowHeaders",
                    StringUtil.valueOf(allowOrigin.getAllowHeaders(),"")); //
            registration.addInitParameter("allowCredentials",
                    StringUtil.valueOf(allowOrigin.getAllowCredentials(),"")); //
            registration.setName("authorityFilter");
            registration.setOrder(0);
        }
        return registration;
    }

    public static void main(String[] args) {
        Set<String> set=new HashSet<>();
        set.add("1");
        set.add("2");
        set.add("3");
        set=null;
        String json=JSONArray.toJSONString(set);
        Set<String> s=JSONArray.parseObject(json,Set.class);
        System.out.println(StringUtil.isNull(s));
    }

}
