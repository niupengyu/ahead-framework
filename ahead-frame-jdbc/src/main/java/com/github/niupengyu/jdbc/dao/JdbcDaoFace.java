/**  
 * 文件名: JdbcDao.java
 * 包路径: com.github.niupengyu.jdbc.da
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

import com.alibaba.druid.pool.DruidDataSource;
import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.github.niupengyu.core.exception.SysException;
import com.github.niupengyu.core.util.StringUtil;
import com.github.niupengyu.jdbc.bean.DataSourceBean;
import com.github.niupengyu.jdbc.bean.DbConfig;
import com.github.niupengyu.jdbc.dao.callback.InsertCallBack;
import com.github.niupengyu.jdbc.dao.callback.QueryCallBack;
import com.github.niupengyu.jdbc.dao.callback.UpdateCallBack;
import com.github.niupengyu.jdbc.datasource.BasicDataSource;
import com.github.niupengyu.jdbc.datasource.SingleDataSource;
import com.github.niupengyu.jdbc.datasource.TryToReconnectDataSource;
import com.github.niupengyu.jdbc.exception.DaoException;
import com.github.niupengyu.jdbc.util.RsUtil;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.sql.DataSource;
import java.sql.*;
import java.util.*;

//@Repository("jdbcDao")
public interface JdbcDaoFace {





	public <T> List<T> getListOne(String sql) throws DaoException ;

	public <T> List<T> getListOne(String sql,Object ... vars) throws DaoException ;

	public <T> Set<T> getSetOne(String sql) throws DaoException ;

	public DataSource getDataSource();

	public Map<String,Object> getOne(String sql) throws DaoException;
	
	public int getIntValue(String sql) throws DaoException;


	public int getIntValue(String sql,Object ... vars) throws DaoException;

	public int getIntValue(String sql,Object var) throws DaoException;

	public float getFloat(String sql) throws DaoException;
	
	public Object getObject(String sql) throws DaoException;

	public Object getObject(String sql,Object ... params) throws DaoException;

	public String getString(String sql) throws DaoException;

	public String getString(String sql,Object ... params) throws DaoException;

	
	public int executeUpdate(String sql) throws DaoException;
	
	public boolean execute(String sql) throws DaoException;

	public Connection conn() throws SQLException ;
	
	public int[] executeBatch(List<String> sqls) throws DaoException;
	public int[] executeBatch(String sql,List<Object[]> paramList) throws DaoException;

	public int[] execute(JdbcCallBack jdbcCallBack) throws DaoException;




    interface JdbcCallBack{
		public void execute(Statement stmt);
	}



	public List<Map<String, Object>> executeQuery(String sql) throws DaoException ;




	public Map<String, Object> selectOne(String sql) throws DaoException;

	public Map<String, Object> selectOne(String sql,Object ... objects) throws DaoException;

	public Map<String, Object> selectOne(String sql,Object object) throws DaoException;

	public Map<String, Object> selectOne(String sql,QueryCallBack queryCallBack) throws Exception;

	public Map<String, Object> selectOne(String sql,QueryCallBack queryCallBack,Object[] objects) throws Exception;

	public Map<String, Object> selectOne(String sql,Object object,QueryCallBack queryCallBack) throws Exception;

	public Map<String, Object> selectOne(String sql,String[] queryCallBack) throws Exception;

	public Map<String, Object> selectOne(String sql,String[] queryCallBack,Object ... objects) throws Exception;

	public Map<String, Object> selectOne(String sql,Object object,String[] queryCallBack) throws Exception;

	public Map<String, Object> one(List<Map<String, Object>> array,String sql) throws DaoException ;

	public List<Map<String,Object>> executeQuery(String sql, Object ... vars) throws DaoException ;

	public List<Map<String, Object>> executeQuery(String sql,QueryCallBack queryCallBack) throws Exception ;

	public List<Map<String,Object>> executeQuery(String sql,QueryCallBack queryCallBack, Object[] vars) throws Exception ;

	public List<Map<String,Object>> executeQuery(String sql, Object var,QueryCallBack queryCallBack) throws Exception ;

	public List<Map<String, Object>> executeQuery(String sql,String[] vars) throws Exception ;

	public List<Map<String,Object>> executeQuery(String sql,String[] queryCallBack, Object ... vars) throws Exception ;

	public List<Map<String,Object>> executeQuery(String sql, Object var,String[] queryCallBack) throws Exception ;

	public List<Map<String,Object>> executeQuery(String sql, Object var) throws DaoException ;



	public int executeUpdate(String sql, Object ... vars) throws DaoException ;
	
	public boolean execute(String sql, Object ... vars) throws DaoException ;

	public int insert(String sql,Map<String, Object> map,InsertCallBack insertCallBack) throws Exception ;

	public int update(String sql,Map<String, Object> map,UpdateCallBack updateCallBack) throws Exception ;



	public int executeUpdate(String sql,List<Map<String, Object>> updateList,UpdateCallBack updateCallBack) throws Exception ;
	public int executeUpdateFalse(String sql,List<Map<String, Object>> updateList,UpdateCallBack updateCallBack) throws Exception ;

	public int executeInsert(String sql,List<Map<String, Object>> insertList,InsertCallBack insertCallBack) throws Exception ;

	public int executeInsertFalse(String sql,List<Map<String, Object>> insertList,InsertCallBack insertCallBack) throws Exception ;


	public int executeInsertJson(String sql, List<JSONObject> insertList, InsertCallBack insertCallBack) throws Exception ;
	public int executeInsertJson(String sql, JSONArray insertList, InsertCallBack insertCallBack) throws Exception ;


}


