package com.github.niupengyu.core.bean;

import java.util.List;

public class FactoryConfig {

    private Boolean enable;

    private String[] classPackages;


    public Boolean getEnable() {
        return enable;
    }

    public void setEnable(Boolean enable) {
        this.enable = enable;
    }

    public String[] getClassPackages() {
        return classPackages;
    }

    public void setClassPackages(String[] classPackages) {
        this.classPackages = classPackages;
    }
}
