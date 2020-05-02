package com.github.niupengyu.jdbc.dao.callback;

public interface ConvertCallBack {

    public Object convert(Object value,
                          String defaultValue, String type) throws Exception;

}
