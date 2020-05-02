package com.github.niupengyu.jdbc.dao.callback;

import java.sql.PreparedStatement;
import java.util.Map;

public interface UpdateCallBack {
    void addStmt(Map<String, Object> map, PreparedStatement stmt) throws Exception;
}
