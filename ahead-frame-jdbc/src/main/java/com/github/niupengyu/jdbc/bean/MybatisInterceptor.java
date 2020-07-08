package com.github.niupengyu.jdbc.bean;

import java.util.Map;

public class MybatisInterceptor {

    private Map<String,Object> prop;

    private String className;

    public Map<String, Object> getProp() {
        return prop;
    }

    public void setProp(Map<String, Object> prop) {
        this.prop = prop;
    }

    public String getClassName() {
        return className;
    }

    public void setClassName(String className) {
        this.className = className;
    }
}
