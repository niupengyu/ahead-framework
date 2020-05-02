package com.github.niupengyu.jdbc.dao.callback;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.ResultSetMetaData;
import java.sql.SQLException;
import java.util.Map;

public interface QueryCallBack {

    void addMap(ResultSet rs, Map<String, Object> map, ResultSetMetaData rsmd, int size) throws Exception;

}
