package com.github.niupengyu.jdbc.dao.impl;

import com.github.niupengyu.core.util.StringUtil;
import com.github.niupengyu.jdbc.dao.callback.InsertCallBack;
import com.github.niupengyu.jdbc.dao.callback.UpdateCallBack;

import java.sql.PreparedStatement;
import java.util.Map;

public class UpdateCallBackImpl implements UpdateCallBack {

    private String[] columns;

    public UpdateCallBackImpl(String[] columns) {
        this.columns=columns;
    }

    @Override
    public void addStmt(Map<String, Object> map, PreparedStatement preparedStatement) throws Exception {
        int i=1;
        for(String key:columns){
            preparedStatement.setObject(i, StringUtil.mapValue(map,key));
            i++;
        }
    }
}
