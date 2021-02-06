package com.github.niupengyu.jdbc.datasource;

import com.github.niupengyu.jdbc.dao.JdbcDao;
import com.github.niupengyu.jdbc.dao.JdbcDaoFace;
import org.slf4j.LoggerFactory;

import javax.sql.DataSource;
import java.io.PrintWriter;
import java.lang.reflect.InvocationHandler;
import java.lang.reflect.Method;
import java.lang.reflect.Proxy;
import java.sql.Connection;
import java.sql.SQLException;
import java.sql.SQLFeatureNotSupportedException;
import java.util.ArrayList;
import java.util.List;
import java.util.logging.Logger;

public class TryToReconnectDataSource implements DataSource{


    private String driverClassName;
    private String url;
    private String username;
    private String password;
    private long createTime;
    private List<ConnectionInfo> connectionInfoList=new ArrayList<>();

    private static final org.slf4j.Logger logger= LoggerFactory.getLogger(TryToReconnectDataSource.class);

    public TryToReconnectDataSource(){

    }

    private Connection connection;


    public TryToReconnectDataSource(String driverClassName, String url, String username, String password) throws Exception {
        this.driverClassName = driverClassName;
        this.url = url;
        this.username = username;
        this.password = password;
        createConnection();
    }

    public TryToReconnectDataSource(Connection conn){
        connection=conn;
    }

    @Override
    public Connection getConnection() throws SQLException {
        if(connection==null||connection.isClosed()){
            reconnection();
        }
        Connection proxyConn =(Connection) Proxy.newProxyInstance(connection.getClass().getClassLoader(),
                new Class[] { Connection.class }, new InvocationHandler() {
                @Override
                public Object invoke(Object proxy, Method method, Object[] args)
                        throws Throwable {
                    //logger.info("local conn is {}",connection);
                    if("close".equals(method.getName())){
                        return connection;
                    }else {
                        return method.invoke(connection, args);
                    }
                }
            });
        return proxyConn;
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

    public void reconnection()  {
        logger.info("reconnection");
        ConnectionInfo connectionInfo=new ConnectionInfo(System.currentTimeMillis());
        try{
            while(true){
                if(connection==null||connection.isClosed()){
                    createConnection();
                }else{
                    break;
                }
                Thread.sleep(5000l);
            }
        }catch (Exception e){
            logger.error("重连数据库异常",e);
        }
        connectionInfo.setEndLost(System.currentTimeMillis());
        connectionInfoList.add(connectionInfo);
        logger.info("连接成功！{}", connectionInfo.timeDes());
        //test();
    }


    public void close(){
        JdbcDao.closeConn(connection);
        connection=null;
        logger.info("关闭连接!");
    }


    public void createConnection() throws Exception {
        this.connection= JdbcDao.createConn(driverClassName,url,username,password);
        logger.info("创建连接 {},{}",driverClassName,username);
    }

    public List<ConnectionInfo> getConnectionInfoList() {
        return connectionInfoList;
    }


    public static List<ConnectionInfo> close(DataSource dataSource){
        if(dataSource!=null&&dataSource instanceof TryToReconnectDataSource){
            TryToReconnectDataSource tryToReconnectDataSource= (TryToReconnectDataSource) dataSource;
            tryToReconnectDataSource.close();
            return tryToReconnectDataSource.getConnectionInfoList();
        }
        return new ArrayList<>();
    }

    /*public static void main(String[] args) throws SQLException {
        TryToReconnectDataSource dataSource=new TryToReconnectDataSource(
                "com.mysql.jdbc.Driver",
                "jdbc:mysql://192.168.64.128:3306/eps_schedule?characterEncoding=utf8&zeroDateTimeBehavior=convertToNull&autoReconnect=true&serverTimezone=GMT%2B8&useSSL=false&allowPublicKeyRetrieval=true",
                "root","123456");
        dataSource.getConnection();
        System.out.println(TryToReconnectDataSource.close(dataSource));
    }*/
}
