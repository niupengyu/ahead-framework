package com.github.niupengyu.jdbc.datasource;

import com.github.niupengyu.core.util.StringUtil;
import com.github.niupengyu.jdbc.bean.DataSourceBean;
import com.github.niupengyu.jdbc.bean.DbConfig;
import com.github.niupengyu.jdbc.dao.JdbcDao;

import javax.sql.DataSource;
import java.io.PrintWriter;
import java.sql.Connection;
import java.sql.SQLException;
import java.sql.SQLFeatureNotSupportedException;
import java.util.logging.Logger;

public class SingleDataSource implements DataSource{

    public SingleDataSource(){

    }

    private Connection connection;

    public SingleDataSource(DbConfig db, DataSourceBean dataSource){
        String driverClassName=StringUtil.valueOf(dataSource.getDriverClassName(),
                db.getDriverClassName());
        String url=StringUtil.valueOf(dataSource.getUrl(),db.getUrl());
        String username=StringUtil.valueOf(dataSource.getUsername(),db.getUsername());
        String password=StringUtil.valueOf(dataSource.getPassword(),db.getPassword());
        connection=JdbcDao.createConn(driverClassName,url,username,password);
    }

    public SingleDataSource(Connection conn){
        connection=conn;
    }

    @Override
    public Connection getConnection() throws SQLException {
        return connection;
    }

    @Override
    public Connection getConnection(String username, String password) throws SQLException {
        return connection;
    }

    @Override
    public <T> T unwrap(Class<T> iface) throws SQLException {
        return null;
    }

    @Override
    public boolean isWrapperFor(Class<?> iface) throws SQLException {
        return false;
    }

    @Override
    public PrintWriter getLogWriter() throws SQLException {
        return null;
    }

    @Override
    public void setLogWriter(PrintWriter out) throws SQLException {

    }

    @Override
    public void setLoginTimeout(int seconds) throws SQLException {

    }

    @Override
    public int getLoginTimeout() throws SQLException {
        return 0;
    }

    @Override
    public Logger getParentLogger() throws SQLFeatureNotSupportedException {
        return null;
    }
}
