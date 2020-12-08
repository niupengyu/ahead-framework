/**
 * 文件名: JdbcDao.java
 * 包路径: com.github.niupengyu.jdbc.dao
 * 创建描述
 *        @createPerson：牛鹏宇
 *        @createDate：2017年9月26日 下午4:05:28
 *        内容描述：
 * 修改描述
 *        @updatePerson：牛鹏宇
 *        @updateDate：2017年9月26日 下午4:05:28
 *        修改内容:
 * 版本: V1.0
 */
package com.github.niupengyu.jdbc.dao;

import com.github.niupengyu.core.exception.SysException;
import com.github.niupengyu.core.util.StringUtil;
import com.github.niupengyu.jdbc.annotation.DataSourceManager;
import com.github.niupengyu.jdbc.bean.DataSourceBean;
import com.github.niupengyu.jdbc.bean.DbConfig;
import com.github.niupengyu.jdbc.dao.callback.InsertCallBack;
import com.github.niupengyu.jdbc.dao.callback.QueryCallBack;
import com.github.niupengyu.jdbc.dao.callback.UpdateCallBack;
import com.github.niupengyu.jdbc.datasource.BasicDataSource;
import com.github.niupengyu.jdbc.datasource.SingleDataSource;
import com.github.niupengyu.jdbc.exception.DaoException;
import com.alibaba.druid.pool.DruidDataSource;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.sql.DataSource;
import java.sql.*;
import java.util.*;

//@Repository("jdbcDao")
public class JdbcUtil {

    //	@Resource(name="multipleDataSource")
    private Connection conn;

    //private List<PreparedStatement> preparedStatementMap;

    private List<Statement> statementMap=new ArrayList<>();

    public JdbcUtil(){

    }

    public JdbcUtil(Connection connection) throws SQLException {
        this.conn=connection;
        conn.setAutoCommit(false);
    }

    private static final Logger logger= LoggerFactory.getLogger("dataSource");

    public int executeUpdate(String sql) throws DaoException{
        logger.debug("execute {}",sql);
        int i=0;
        Statement stmt=null;
        try {
            stmt=this.createStatement();
            i=stmt.executeUpdate(sql);
        } catch (SQLException e) {
            logger.error("修改出错:"+sql, e);
            throw new DaoException(e.getMessage());
        }finally {

        }
        return i;
    }

    private Statement createStatement() throws SQLException {
        Statement stmt=conn.createStatement();
        statementMap.add(stmt);
        return stmt;
    }

    private PreparedStatement prepareStatement(String sql) throws SQLException {
        logger.debug("execute {}",sql);
        PreparedStatement stmt=conn.prepareStatement(sql);
        statementMap.add(stmt);
        return stmt;
    }

    public boolean execute(String sql) throws DaoException{
        logger.debug("execute {}",sql);
        boolean i=false;
        Statement stmt=null;
        try {
            stmt=this.createStatement();
            i=stmt.execute(sql);
        } catch (SQLException e) {
            logger.error("执行sql出错:"+sql, e);
            throw new DaoException(e.getMessage());
        }
        return i;
    }

    public int[] executeBatch(List<String> sqls) throws DaoException{
        logger.debug("execute {}",sqls);
        int[] i=new int[0];
        Statement stmt=null;
        try {
            stmt=this.createStatement();
            for(String sql:sqls){
                stmt.addBatch(sql);
            }
            i=stmt.executeBatch();
        } catch (SQLException e) {
            logger.error("批处理出错:"+sqls, e);
            throw new DaoException(e.getMessage());
        }
        return i;
    }

    public int[] executeBatch(String sql,List<Object[]> paramList) throws DaoException{
        logger.debug("execute {}",sql);
        int[] res=new int[0];
        PreparedStatement stmt=null;
        try {
            stmt=this.prepareStatement(sql);

            for(Object[] vars:paramList){
                int index = 1;
                if(vars!=null&&vars.length != 0){
                    for(int i = 0;i <vars.length;i++){
                        stmt.setObject(index++, vars[i]);
                    }
                }
                stmt.addBatch();
            }
            res=stmt.executeBatch();
        } catch (SQLException e) {
            logger.error("批处理出错:"+sql, e);
            throw new DaoException(e.getMessage());
        }
        return res;
    }

    public int[] executeOneBatch(String sql,List<?> paramList) throws DaoException{
        logger.debug("execute {}",sql);
        int[] res=new int[0];
        PreparedStatement stmt=null;
        try {
            stmt=this.prepareStatement(sql);

            for(Object vars:paramList){
                //int index = 1;
                stmt.setObject(1, vars);
                stmt.addBatch();
            }
            res=stmt.executeBatch();
        } catch (SQLException e) {
            logger.error("批处理出错:"+sql, e);
            throw new DaoException(e.getMessage());
        }
        return res;
    }


    public void close() {
        Iterator<Statement> statementIterator=statementMap.iterator();
        while(statementIterator.hasNext()){
            JdbcDao.closeStmt(statementIterator.next());
        }
        statementMap.clear();
        JdbcDao.closeConn(conn);
    }

    public static void close(JdbcUtil jdbcUtil) {
        if(jdbcUtil!=null){
            jdbcUtil.close();
        }
    }


    public int executeUpdate(String sql, Object ... vars) throws DaoException {
        int res=0;
        PreparedStatement stmt=null;
        logger.debug("executeUpdate "+sql);
        logger.debug("params "+Arrays.toString(vars));
        try {
            stmt=this.prepareStatement(sql);
            int index = 1;
            if(vars!=null&&vars.length != 0){
                for(int i = 0;i <vars.length;i++){
                    stmt.setObject(index++, vars[i]);
                }
            }
            res = stmt.executeUpdate();
        } catch (SQLException e) {
            logger.error("修改出错:"+sql, e);
            logger.error("params "+Arrays.toString(vars));
            throw new DaoException(e.getMessage());
        }
        return res;
    }

    public int executeUpdate(String sql, Object vars) throws DaoException {
        int res=0;
        PreparedStatement stmt=null;
        logger.debug("execute "+sql);
        logger.debug("params "+vars);
        try {
            stmt=this.prepareStatement(sql);
            int index = 1;
            stmt.setObject(index++, vars);
            res = stmt.executeUpdate();
        } catch (SQLException e) {
            logger.error("修改出错:"+sql, e);
            logger.error("params "+vars);
            throw new DaoException(e.getMessage());
        }
        return res;
    }

    public boolean execute(String sql, Object ... vars) throws DaoException {
        logger.debug("execute "+sql);
        logger.debug("params "+Arrays.toString(vars));
        boolean res=false;
        PreparedStatement stmt=null;
        try {
            stmt=this.prepareStatement(sql);
            int index = 1;
            if(vars!=null&&vars.length != 0){
                for(int i = 0;i <vars.length;i++){
                    stmt.setObject(index++, vars[i]);
                }
            }
            res = stmt.execute();
        } catch (SQLException e) {
            logger.error("修改出错:"+sql, e);
            logger.error("params "+Arrays.toString(vars));
            throw new DaoException(e.getMessage());
        }
        return res;
    }

    public int insert(String sql,Map<String, Object> map,InsertCallBack insertCallBack) throws Exception {
        logger.debug("execute {}",sql);
        PreparedStatement stmt = null;
        int i=-1;
        try {
            stmt = this.prepareStatement(sql);
            insertCallBack.addStmt(map,stmt);
            stmt.execute();
            i=1;
        } catch (Exception e) {
            logger.error("查询出错", e);
            throw new SysException(e.getMessage());
        }
        return i;
    }

    public int update(String sql,Map<String, Object> map,UpdateCallBack updateCallBack) throws Exception {
        logger.debug("execute {}",sql);
        PreparedStatement stmt=null;
        int i=-1;
        try{
            stmt=this.prepareStatement(sql);
            updateCallBack.addStmt(map,stmt);
            stmt.executeUpdate();
            i=1;
        }catch (Exception e){
            logger.error("更新出错 "+sql,e);
            throw new SysException(e.getMessage());
        }
        return i;
    }



    public int executeUpdate(String sql,List<Map<String, Object>> updateList,UpdateCallBack updateCallBack) throws Exception {
        logger.debug("execute {}",sql);
        int res=-1;
        PreparedStatement stmt=null;
        try{
            int i=0;
            for(Map<String,Object> data:updateList){
                updateCallBack.addStmt(data,stmt);
                stmt.addBatch();
                i++;
            }
            stmt.executeBatch();
            res=i;
        }catch (Exception e){
            logger.error("修改出错 " +sql,e);
            throw new SysException(e.getMessage());
        }
        return res;
    }

    public int executeInsert(String sql,List<Map<String, Object>> insertList,InsertCallBack insertCallBack) throws Exception {
        logger.debug("execute {}",sql);
        int res=-1;
        PreparedStatement stmt=null;
        try{
            stmt=this.prepareStatement(sql);
            int i=0;
            for(Map<String,Object> data:insertList){
                insertCallBack.addStmt(data,stmt);
                stmt.addBatch();
                i++;
            }
            stmt.executeBatch();
            res=i;
        }catch (Exception e){
            logger.error("插入出错 "+sql,e);
            throw new SysException(e.getMessage());
        }
        return res;
    }



    public void setConnection(Connection connection) throws SQLException {
        connection.setAutoCommit(false);
        this.conn=connection;
    }

    public void commit() throws SQLException {
        conn.commit();
    }

    public void rollBack() {
        JdbcDao.rollback(conn);
    }

    public static void rollBack(JdbcUtil jdbcUtil) {
        if(jdbcUtil!=null){
            jdbcUtil.close();
        }
    }

    public static String join(List<Map<String,Object>> list,String key,String sp){
        StringBuilder str=new StringBuilder();
        for(Map<String,Object> map:list){
            String id= StringUtil.mapValueString(map,key);
            str.append(",").append(sp).append(id).append(sp);
        }
        str.deleteCharAt(0);
        return str.toString();
    }
}


