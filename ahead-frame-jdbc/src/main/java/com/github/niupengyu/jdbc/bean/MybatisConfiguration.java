package com.github.niupengyu.jdbc.bean;

import java.util.HashMap;
import java.util.Map;

public class MybatisConfiguration {

    private Map<String,Object> prop=new HashMap<>();

    private MybatisInterceptor[] interceptor=new MybatisInterceptor[0];

    public Map<String, Object> getProp() {
        return prop;
    }

    public void setProp(Map<String, Object> prop) {
        this.prop = prop;
    }

    public MybatisInterceptor[] getInterceptor() {
        return interceptor;
    }

    public void setInterceptor(MybatisInterceptor[] interceptor) {
        this.interceptor = interceptor;
    }
}
