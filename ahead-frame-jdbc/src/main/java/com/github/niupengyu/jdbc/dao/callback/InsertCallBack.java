package com.github.niupengyu.jdbc.dao.callback;

import java.sql.PreparedStatement;
import java.util.Map;

public interface InsertCallBack {

    void addStmt(Map<String, Object> map, PreparedStatement stmt) throws Exception;

}
