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
import com.github.niupengyu.jdbc.datasource.TryToReconnectDataSource;
import com.github.niupengyu.jdbc.exception.DaoException;
import com.github.niupengyu.jdbc.util.RsUtil;
import com.alibaba.druid.pool.DruidDataSource;
import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.json.JsonArray;
import javax.json.JsonObject;
import javax.sql.DataSource;
import java.sql.*;
import java.util.*;

//@Repository("jdbcDao")
public class JdbcDao {

//	@Resource(name="multipleDataSource")
	private DataSource dataSource;

	public JdbcDao(){

	}

	public JdbcDao(Connection connection){
		this.dataSource=new SingleDataSource(connection);
	}

	public JdbcDao(DataSource dataSource){
		this.dataSource = dataSource;
	}

	private static final Logger logger= LoggerFactory.getLogger("dataSource");


	public <T> List<T> getListOne(String sql) throws DaoException {
		logger.debug("getListOne {}",sql);
		Connection conn=null;
		Statement stmt=null;
		ResultSet rs=null;
		List<T> list=new ArrayList();
		try {
			conn=dataSource.getConnection();
			stmt=conn.createStatement();
			rs=stmt.executeQuery(sql);
			while(rs.next()){
				Object object=rs.getObject(1);
				list.add((T) object);
			}
		} catch (SQLException e) {
			logger.error("getList:"+sql, e);
			throw new DaoException(e.getMessage());
		}finally{
			rs=closeResultSet(rs);
			stmt=closeStmt(stmt);
			conn=closeConn(conn);
		}
		return list;
	}

	public <T> List<T> getListOne(String sql,Object ... vars) throws DaoException {
		logger.debug("getListOne {}",sql);
		logger.debug("params {}",Arrays.toString(vars));
		Connection conn=null;
		PreparedStatement stmt=null;
		ResultSet rs=null;
		List<T> list=new ArrayList();
		try {
			conn=dataSource.getConnection();
			stmt=conn.prepareStatement(sql);
			int index = 1;
			if(vars!=null&&vars.length != 0){
				for(int i = 0;i <vars.length;i++){
					stmt.setObject(index++, vars[i]);
				}
			}
			rs=stmt.executeQuery();
			while(rs.next()){
				Object object=rs.getObject(1);
				list.add((T) object);
			}
		} catch (SQLException e) {
			logger.error("getList:"+sql, e);
			logger.error("params {}",Arrays.toString(vars));
			throw new DaoException(e.getMessage());
		}finally{
			rs=closeResultSet(rs);
			stmt=closePreStmt(stmt);
			conn=closeConn(conn);
		}
		return list;
	}

	public <T> Set<T> getSetOne(String sql) throws DaoException {
		logger.debug("getSetOne {}",sql);
		Connection conn=null;
		Statement stmt=null;
		ResultSet rs=null;
		Set<T> set=new HashSet<>();
		try {
			conn=dataSource.getConnection();
			stmt=conn.createStatement();
			rs=stmt.executeQuery(sql);
			while(rs.next()){
				Object object=rs.getObject(1);
				set.add((T) object);
			}
		} catch (SQLException e) {
			logger.error("getSet:"+sql, e);
			throw new DaoException(e.getMessage());
		}finally{
			rs=closeResultSet(rs);
			stmt=closeStmt(stmt);
			conn=closeConn(conn);
		}
		return set;
	}

	public static void main(String[] args) throws DaoException {
		JdbcDao jdbcDao=new JdbcDao();
		List<String> list=jdbcDao.getListOne("sql");
	}


	public Map<String,Object> getOne(String sql) throws DaoException{
		logger.debug("getOne {}",sql);
		Connection conn=null;
		ResultSet rs=null;
		Statement stmt=null;
		Map<String,Object> map=new HashMap<String,Object>();
		try {
			conn=dataSource.getConnection();
			stmt=conn.createStatement();
			rs=stmt.executeQuery(sql);
			ResultSetMetaData rsmd = rs.getMetaData();
			int size=rsmd.getColumnCount();

			if(rs.next()){
				for(int i=1;i<=size;i++){
					String name=rsmd.getColumnLabel(i);
					map.put(name.toLowerCase(), rs.getObject(name));
				}
			}
		} catch (SQLException e) {
			logger.error("getOne:"+sql, e);
			throw new DaoException(e.getMessage());
		}finally{
			rs=closeResultSet(rs);
			stmt=closeStmt(stmt);
			conn=closeConn(conn);
		}
		return map;
	}
	
	public int getIntValue(String sql) throws DaoException{
		logger.debug("getIntValue {}",sql);
		int value=0;
		Connection conn=null;
		Statement stmt=null;
		ResultSet rs=null;
		try {
			conn=dataSource.getConnection();
			stmt=conn.createStatement();
			rs=stmt.executeQuery(sql);
			value=addInt(rs);
		} catch (SQLException e) {
			logger.error("getIntValue:"+sql, e);
			throw new DaoException(e.getMessage());
		}finally{
			rs=closeResultSet(rs);
			stmt=closeStmt(stmt);
			conn=closeConn(conn);
		}
		return value;
	}

	public static int addInt(ResultSet rs) throws SQLException {
		if(rs.next()){
			return rs.getInt(1);
		}
		return 0;
	}

	public int getIntValue(String sql,Object ... vars) throws DaoException{
		logger.debug("getIntValue {}",sql);
		logger.debug("params {}",Arrays.toString(vars));
		//logger.info(sql);
		//logger.info(Arrays.toString(vars));
		int value=0;
		Connection conn=null;
		PreparedStatement stmt=null;
		ResultSet rs=null;
		try {
			conn=dataSource.getConnection();
			//stmt=conn.createStatement();
			stmt=conn.prepareStatement(sql);
			int index = 1;
			if(vars!=null&&vars.length != 0){
				for(int i = 0;i <vars.length;i++){
					stmt.setObject(index++, vars[i]);
				}
			}
			rs=stmt.executeQuery();
			if(rs.next()){
				value=rs.getInt(1);
			}
		} catch (SQLException e) {
			logger.error("getIntValue:"+sql, e);
			logger.error("params {}",Arrays.toString(vars));
			throw new DaoException(e.getMessage());
		}finally{
			rs=closeResultSet(rs);
			stmt=closePreStmt(stmt);
			conn=closeConn(conn);
		}
		return value;
	}

	public int getIntValue(String sql,Object var) throws DaoException{
		logger.debug("getIntValue {}",sql);
		logger.debug("params {}",var);
		int value=0;
		Connection conn=null;
		PreparedStatement stmt=null;
		ResultSet rs=null;
		try {
			conn=dataSource.getConnection();
			stmt=conn.prepareStatement(sql);
			stmt.setObject(1, var);
			rs=stmt.executeQuery();
			if(rs.next()){
				value=rs.getInt(1);
			}
		} catch (SQLException e) {
			logger.error("getIntValue:"+sql, e);
			logger.error("params {}",var);
			throw new DaoException(e.getMessage());
		}finally{
			rs=closeResultSet(rs);
			stmt=closePreStmt(stmt);
			conn=closeConn(conn);
		}
		return value;
	}

	public float getFloat(String sql) throws DaoException{
		logger.debug("getFloat {}",sql);
		float value=0f;
		Connection conn=null;
		Statement stmt=null;
		ResultSet rs=null;
		try {
			conn=dataSource.getConnection();
			stmt=conn.createStatement();
			rs=stmt.executeQuery(sql);
			if(rs.next()){
				value=rs.getFloat(1);
			}
		} catch (SQLException e) {
			logger.error("getFloat:"+sql, e);
			throw new DaoException(e.getMessage());
		}finally{
			rs=closeResultSet(rs);
			stmt=closeStmt(stmt);
			conn=closeConn(conn);
		}
		return value;
	}
	
	public Object getObject(String sql) throws DaoException{
		logger.debug("getObject {}",sql);
		Object value=null;
		Connection conn=null;
		Statement stmt=null;
		ResultSet rs=null;
		try {
			conn=dataSource.getConnection();
			stmt=conn.createStatement();
			rs=stmt.executeQuery(sql);
			if(rs.next()){
				value=rs.getObject(1);
			}
		} catch (SQLException e) {
			logger.error("getObject:"+sql, e);
			throw new DaoException(e.getMessage());
		}finally{
			rs=closeResultSet(rs);
			stmt=closeStmt(stmt);
			conn=closeConn(conn);
		}
		return value;
	}

	public Object getObject(String sql,Object ... params) throws DaoException{
		logger.debug("getObject {}",sql);
		logger.debug("params {}",Arrays.toString(params));
		Object value=null;
		Connection conn=null;
		PreparedStatement stmt=null;
		ResultSet rs=null;
		try {
			conn=dataSource.getConnection();
			stmt=conn.prepareStatement(sql);
			int index = 1;
			if(params!=null&&params.length != 0){
				for(int i = 0;i <params.length;i++){
					stmt.setObject(index++, params[i]);
				}
			}
			rs=stmt.executeQuery();
			if(rs.next()){
				value=rs.getObject(1);
			}
		} catch (SQLException e) {
			logger.error("getObject:"+sql, e);
			logger.error("params {}",Arrays.toString(params));
			throw new DaoException(e.getMessage());
		}finally{
			rs=closeResultSet(rs);
			stmt=closePreStmt(stmt);
			conn=closeConn(conn);
		}
		return value;
	}

	public String getString(String sql) throws DaoException{
		logger.debug("getString {}",sql);
		String value="";
		Connection conn=null;
		Statement stmt=null;
		ResultSet rs=null;
		try {
			conn=dataSource.getConnection();
			stmt=conn.createStatement();
			rs=stmt.executeQuery(sql);
			if(rs.next()){
				value=rs.getString(1);
			}
		} catch (SQLException e) {
			logger.error("getString:"+sql, e);
			throw new DaoException(e.getMessage());
		}finally{
			rs=closeResultSet(rs);
			stmt=closeStmt(stmt);
			conn=closeConn(conn);
		}
		return value;
	}

	public String getString(String sql,Object ... params) throws DaoException{
		logger.debug("getString {}",sql);
		logger.debug("params {}",Arrays.toString(params));
		String value="";
		Connection conn=null;
		PreparedStatement stmt=null;
		ResultSet rs=null;
		try {
			conn=dataSource.getConnection();
			stmt=conn.prepareStatement(sql);
			int index = 1;
			if(params!=null&&params.length != 0){
				for(int i = 0;i <params.length;i++){
					stmt.setObject(index++, params[i]);
				}
			}
			rs=stmt.executeQuery();
			if(rs.next()){
				value=rs.getString(1);
			}
		} catch (SQLException e) {
			logger.error("getString:"+sql, e);
			logger.error("params {}",Arrays.toString(params));
			throw new DaoException(e.getMessage());
		}finally{
			rs=closeResultSet(rs);
			stmt=closePreStmt(stmt);
			conn=closeConn(conn);
		}
		return value;
	}

	
	public int executeUpdate(String sql) throws DaoException{
		logger.debug("executeUpdate {}",sql);
		int i=0;
		Connection conn=null;
		Statement stmt=null;
		try {
			conn=dataSource.getConnection();
			stmt=conn.createStatement();
			i=stmt.executeUpdate(sql);
		} catch (SQLException e) {
			logger.error("修改出错:"+sql, e);
			throw new DaoException(e.getMessage());
		}finally{
			stmt=closeStmt(stmt);
			conn=closeConn(conn);
		}
		return i;
	}
	
	public boolean execute(String sql) throws DaoException{
		logger.debug("execute {}",sql);
		boolean i=false;
		Connection conn=null;
		Statement stmt=null;
		try {
			conn=dataSource.getConnection();
			stmt=conn.createStatement();
			i=stmt.execute(sql);
		} catch (SQLException e) {
			logger.error("执行sql出错:"+sql, e);
			throw new DaoException(e.getMessage());
		}finally{
			stmt=closeStmt(stmt);
			conn=closeConn(conn);
		}
		return i;
	}

	public Connection conn() throws SQLException {
		return dataSource.getConnection();
	}
	
	public int[] executeBatch(List<String> sqls) throws DaoException{
		logger.debug("executeBatch {}",sqls);
		int[] i=new int[0];
		Connection conn=null;
		Statement stmt=null;
		try {
			conn=dataSource.getConnection();
			conn.setAutoCommit(false);
			stmt=conn.createStatement();
			for(String sql:sqls){
				stmt.addBatch(sql);
			}
			i=stmt.executeBatch();
			conn.commit();
		} catch (SQLException e) {
			if(conn!=null){
				rollback(conn);
			}
			logger.error("批处理出错:"+sqls, e);
			throw new DaoException(e.getMessage());
		}finally{
			conn=closeConn(conn);
			stmt=closeStmt(stmt);
		}
		return i;
	}
	public int[] executeBatch(String sql,List<Object[]> paramList) throws DaoException{
		logger.debug("executeBatch {}",sql);
		int[] res=new int[0];
		Connection conn=null;
		PreparedStatement stmt=null;
		try {
			conn=dataSource.getConnection();
			conn.setAutoCommit(false);
			stmt=conn.prepareStatement(sql);

			for(Object[] vars:paramList){
				//logger.debug("params {}",Arrays.toString(vars));
				int index = 1;
				if(vars!=null&&vars.length != 0){
					for(int i = 0;i <vars.length;i++){
						stmt.setObject(index++, vars[i]);
					}
				}
				stmt.addBatch();
			}
			res=stmt.executeBatch();
			conn.commit();
		} catch (SQLException e) {
			rollback(conn);
			logger.error("批处理出错:"+sql, e);
			throw new DaoException(e.getMessage());
		}finally{
			conn=closeConn(conn);
			stmt=closePreStmt(stmt);
		}
		return res;
	}

	public int[] execute(JdbcCallBack jdbcCallBack) throws DaoException{
		int[] i=new int[0];
		Connection conn=null;
		Statement stmt=null;
		try {
			conn=dataSource.getConnection();
			conn.setAutoCommit(false);
			stmt=conn.createStatement();
			jdbcCallBack.execute(stmt);
			conn.commit();
		} catch (SQLException e) {
			rollback(conn);
			throw new DaoException(e.getMessage());
		}finally{
			conn=closeConn(conn);
			stmt=closeStmt(stmt);
		}
		return i;
	}

	public static void close(ResultSet rs,PreparedStatement stmt,Connection conn) {
		JdbcDao.closeResultSet(rs);
		JdbcDao.closeStmt(stmt);
		JdbcDao.closeConn(conn);
	}

	public static void close(ResultSet rs,Statement stmt,Connection conn) {
		JdbcDao.closeResultSet(rs);
		JdbcDao.closeStmt(stmt);
		JdbcDao.closeConn(conn);
	}

    public static String addString(ResultSet rs) throws SQLException {
		if(rs.next()){
			return rs.getString(1);
		}
		return "";
	}

    interface JdbcCallBack{
		public void execute(Statement stmt);
	}
	

	
	public List<Map<String, Object>> executeQuery(String sql) throws DaoException {
		logger.debug("execute {}",sql);
		Connection conn=null;
		Statement stmt=null;
		ResultSet rs=null;
		List<Map<String,Object>> list=new ArrayList<>();
		try {
			conn=dataSource.getConnection();
			stmt=conn.createStatement();
			rs=stmt.executeQuery(sql);
			addList(rs,list);
		} catch (SQLException e) {
			logger.error("getList:"+sql, e);
			throw new DaoException(e.getMessage());
		}finally{
			rs=closeResultSet(rs);
			stmt=closeStmt(stmt);
			conn=closeConn(conn);
		}
		return list;
	}




	public Map<String, Object> selectOne(String sql) throws DaoException{
		logger.debug("execute {}",sql);
		List<Map<String, Object>> array=executeQuery(sql);
		return one(array,sql);
	}

	public Map<String, Object> selectOne(String sql,Object ... objects) throws DaoException{
		logger.debug("execute {}",sql);
		logger.debug("params {}",Arrays.toString(objects));
		List<Map<String, Object>> array=executeQuery(sql,objects);
		return one(array,sql);
	}

	public Map<String, Object> selectOne(String sql,Object object) throws DaoException{
		logger.debug("execute {}",sql);
		logger.debug("params {}",object);
		List<Map<String, Object>> array=executeQuery(sql,object);
		return one(array,sql);
	}

	public Map<String, Object> selectOne(String sql,QueryCallBack queryCallBack) throws Exception{
		logger.debug("execute {}",sql);
		List<Map<String, Object>> array=executeQuery(sql,queryCallBack);
		return one(array,sql);
	}

	public Map<String, Object> selectOne(String sql,QueryCallBack queryCallBack,Object[] objects) throws Exception{
		List<Map<String, Object>> array=executeQuery(sql,objects,queryCallBack);
		return one(array,sql);
	}

	public Map<String, Object> selectOne(String sql,Object object,QueryCallBack queryCallBack) throws Exception{
		List<Map<String, Object>> array=executeQuery(sql,object,queryCallBack);
		return one(array,sql);
	}

	public Map<String, Object> selectOne(String sql,String[] queryCallBack) throws Exception{
		List<Map<String, Object>> array=executeQuery(sql,queryCallBack);
		return one(array,sql);
	}

	public Map<String, Object> selectOne(String sql,String[] queryCallBack,Object ... objects) throws Exception{
		List<Map<String, Object>> array=executeQuery(sql,objects,queryCallBack);
		return one(array,sql);
	}

	public Map<String, Object> selectOne(String sql,Object object,String[] queryCallBack) throws Exception{
		List<Map<String, Object>> array=executeQuery(sql,object,queryCallBack);
		return one(array,sql);
	}

	private Map<String, Object> one(List<Map<String, Object>> array,String sql) throws DaoException {
		if(array==null||array.isEmpty()){
			return new HashMap<>();
		}
		int size=0;
		if((size=array.size())>1){
			throw new DaoException("查询一条数据 返回 "+size+" 条:"+sql);
		}
		return array.get(0);
	}

	public List<Map<String,Object>> executeQuery(String sql, Object ... vars) throws DaoException {
		logger.debug("execute {}",sql);
		logger.debug(Arrays.toString(vars));
		Connection conn=null;
		PreparedStatement stmt=null;
		ResultSet rs=null;
		List<Map<String,Object>> list=new ArrayList<Map<String,Object>>();
		try {
			conn=dataSource.getConnection();
			stmt=conn.prepareStatement(sql);
			int index = 1;
			if(vars!=null&&vars.length != 0){
				for(int i = 0;i <vars.length;i++){
					stmt.setObject(index++, vars[i]);
				}
			}
			rs = stmt.executeQuery();
			//ResultSetMetaData rsmd = rs.getMetaData();
			addList(rs,list);
		} catch (SQLException e) {
			logger.error("查询出错:"+sql, e);
			logger.error(Arrays.toString(vars));
			throw new DaoException(e.getMessage());
		} finally{
			stmt=closePreStmt(stmt);
			conn=closeConn(conn);
			rs=closeResultSet(rs);
		}
		return list;
	}

	public List<Map<String, Object>> executeQuery(String sql,QueryCallBack queryCallBack) throws Exception {
		logger.debug("execute {}",sql);
		Connection conn=null;
		Statement stmt=null;
		ResultSet rs=null;
		List<Map<String,Object>> list=new ArrayList<>();
		try {
			conn=dataSource.getConnection();
			stmt=conn.createStatement();
			rs=stmt.executeQuery(sql);
			addList(rs,list,queryCallBack);
		} catch (SQLException e) {
			logger.error("getList:"+sql, e);
			throw new DaoException(e.getMessage());
		}finally{
			rs=closeResultSet(rs);
			stmt=closeStmt(stmt);
			conn=closeConn(conn);
		}
		return list;
	}

	public List<Map<String,Object>> executeQuery(String sql,QueryCallBack queryCallBack, Object[] vars) throws Exception {
		logger.debug("execute {}",sql);
		//logger.info(sql);
		//logger.info(Arrays.toString(vars));
		Connection conn=null;
		PreparedStatement stmt=null;
		ResultSet rs=null;
		List<Map<String,Object>> list=new ArrayList<>();
		try {
			conn=dataSource.getConnection();
			stmt=conn.prepareStatement(sql);
			int index = 1;
			if(vars!=null&&vars.length != 0){
				for(int i = 0;i <vars.length;i++){
					stmt.setObject(index++, vars[i]);
				}
			}
			rs = stmt.executeQuery();
			addList(rs,list,queryCallBack);
		} catch (SQLException e) {
			logger.error("修改出错:"+sql, e);
			logger.error(Arrays.toString(vars));
			throw new DaoException(e.getMessage());
		} finally{
			stmt=closePreStmt(stmt);
			conn=closeConn(conn);
			rs=closeResultSet(rs);
		}
		return list;
	}

	public List<Map<String,Object>> executeQuery(String sql, Object var,QueryCallBack queryCallBack) throws Exception {
		logger.debug("execute {}",sql);
		logger.debug(StringUtil.valueOf(var));
		Connection conn=null;
		PreparedStatement stmt=null;
		ResultSet rs=null;
		List<Map<String,Object>> list=new ArrayList<>();
		try {
			conn=dataSource.getConnection();
			stmt=conn.prepareStatement(sql);
			stmt.setObject(1,var);
			rs = stmt.executeQuery();
			addList(rs,list,queryCallBack);
		} catch (SQLException e) {
			logger.error("修改出错:"+sql, e);
			logger.error(StringUtil.valueOf(var));
			throw new DaoException(e.getMessage());
		}finally{
			stmt=closePreStmt(stmt);
			conn=closeConn(conn);
			rs=closeResultSet(rs);
		}
		return list;
	}

	public List<Map<String, Object>> executeQuery(String sql,String[] vars) throws Exception {
		logger.debug("execute {}",sql);
		logger.debug(Arrays.toString(vars));
		Connection conn=null;
		Statement stmt=null;
		ResultSet rs=null;
		List<Map<String,Object>> list=new ArrayList<>();
		try {
			conn=dataSource.getConnection();
			stmt=conn.createStatement();
			rs=stmt.executeQuery(sql);
			addList(rs,list,vars);
		} catch (SQLException e) {
			logger.error("getList:"+sql, e);
			logger.error(Arrays.toString(vars));
			throw new DaoException(e.getMessage());
		}finally{
			rs=closeResultSet(rs);
			stmt=closeStmt(stmt);
			conn=closeConn(conn);
		}
		return list;
	}

	public List<Map<String,Object>> executeQuery(String sql,String[] queryCallBack, Object ... vars) throws Exception {
		logger.debug("execute {}",sql);
		logger.debug(Arrays.toString(vars));
		//logger.info(sql);
		//logger.info(Arrays.toString(vars));
		Connection conn=null;
		PreparedStatement stmt=null;
		ResultSet rs=null;
		List<Map<String,Object>> list=new ArrayList<>();
		try {
			conn=dataSource.getConnection();
			stmt=conn.prepareStatement(sql);
			int index = 1;
			if(vars!=null&&vars.length != 0){
				for(int i = 0;i <vars.length;i++){
					stmt.setObject(index++, vars[i]);
				}
			}
			rs = stmt.executeQuery();
			addList(rs,list,queryCallBack);
		} catch (SQLException e) {
			logger.error("修改出错:"+sql, e);
			logger.error(Arrays.toString(vars));
			throw new DaoException(e.getMessage());
		} finally{
			stmt=closePreStmt(stmt);
			conn=closeConn(conn);
			rs=closeResultSet(rs);
		}
		return list;
	}

	public List<Map<String,Object>> executeQuery(String sql, Object var,String[] queryCallBack) throws Exception {
		logger.debug("execute {}",sql);
		logger.debug(StringUtil.valueOf(var));
		Connection conn=null;
		PreparedStatement stmt=null;
		ResultSet rs=null;
		List<Map<String,Object>> list=new ArrayList<>();
		try {
			conn=dataSource.getConnection();
			stmt=conn.prepareStatement(sql);
			stmt.setObject(1,var);
			rs = stmt.executeQuery();
			addList(rs,list,queryCallBack);
		} catch (SQLException e) {
			logger.error("修改出错:"+sql, e);
			logger.error(StringUtil.valueOf(var));
			throw new DaoException(e.getMessage());
		}finally{
			stmt=closePreStmt(stmt);
			conn=closeConn(conn);
			rs=closeResultSet(rs);
		}
		return list;
	}

	public List<Map<String,Object>> executeQuery(String sql, Object var) throws DaoException {
		logger.debug("execute {}",sql);
		logger.debug(StringUtil.valueOf(var));
		Connection conn=null;
		PreparedStatement stmt=null;
		ResultSet rs=null;
		List<Map<String,Object>> list=new ArrayList<Map<String,Object>>();
		try {
			conn=dataSource.getConnection();
			stmt=conn.prepareStatement(sql);
			stmt.setObject(1,var);
			rs = stmt.executeQuery();
			addList(rs,list);
		} catch (SQLException e) {
			logger.error("查询出错:"+sql, e);
			logger.error(StringUtil.valueOf(var));
			throw new DaoException(e.getMessage());
		}finally{
			stmt=closePreStmt(stmt);
			conn=closeConn(conn);
			rs=closeResultSet(rs);
		}
		return list;
	}



	public static void addList( ResultSet rs, List<Map<String, Object>> list) throws SQLException {
		ResultSetMetaData rsmd = rs.getMetaData();
		int size=rsmd.getColumnCount();
		while(rs.next()){
			Map<String,Object> map=new HashMap<String,Object>();
			for(int i=1;i<=size;i++){
				String name=rsmd.getColumnLabel(i);
				Object obj=rs.getObject(name);
				map.put(name, obj);
			}
			list.add(map);
		}
	}

	public static void addList( ResultSet rs, List<Map<String, Object>> list ,boolean upper) throws SQLException {
		ResultSetMetaData rsmd = rs.getMetaData();
		int size=rsmd.getColumnCount();
		while(rs.next()){
			Map<String,Object> map=new HashMap<String,Object>();
			for(int i=1;i<=size;i++){
				String name=rsmd.getColumnLabel(i);
				if(upper){
					name=name.toUpperCase();
				}else{
					name=name.toLowerCase();
				}
				Object obj=rs.getObject(name);
				map.put(name, obj);
			}
			list.add(map);
		}
	}

	public static void addList(ResultSet rs, List<Map<String, Object>> list, QueryCallBack queryCallBack) throws Exception {
		ResultSetMetaData rsmd = rs.getMetaData();
		int size=rsmd.getColumnCount();
		while(rs.next()){
			Map<String,Object> map=new HashMap<String,Object>();
			queryCallBack.addMap(rs,map,rsmd,size);
			list.add(map);
		}
	}

	public static void addList(ResultSet rs, List<Map<String,Object>> list, String[] columns) throws Exception {
		while(rs.next()){
			JSONObject map=new JSONObject();
			addMap(rs,map,columns);
			list.add(map);
		}
	}

	public static void addMap(ResultSet rs, Map<String,Object> map,String[] columns) throws Exception {
		for(String column:columns){
			int index=column.indexOf(":");
			if(index>-1){
				String key=column.substring(0,index);
				String type=column.substring(index+1);
				map.put(column, RsUtil.value(rs,type,key));
			}else{
				map.put(column,rs.getString(column));
			}
		}
	}


	/*public static void addList( ResultSet rs, List<Map<String, Object>> list,
								ConvertCallBack convertCallBack) throws Exception {
		ResultSetMetaData rsmd = rs.getMetaData();
		int size=rsmd.getColumnCount();
		while(rs.next()){
			Map<String,Object> map=new HashMap<String,Object>();
			for(int i=1;i<=size;i++){
				String name=rsmd.getColumnLabel(i);
				Object obj=rs.getObject(name);
				map.put(name, obj==null?"":convertCallBack.convert(obj));
			}
			list.add(map);
		}
	}*/

	public static void addMap( ResultSet rs, Map<String, Object> map) throws SQLException {
		ResultSetMetaData rsmd = rs.getMetaData();
		int size=rsmd.getColumnCount();
		if(rs.next()){
			for(int i=1;i<=size;i++){
				String name=rsmd.getColumnLabel(i);
				Object obj=rs.getObject(name);
				map.put(name, obj);
			}
		}
	}

	/*public static void addMap( ResultSet rs, Map<String, Object> map,
							   ConvertCallBack convertCallBack) throws Exception {
		ResultSetMetaData rsmd = rs.getMetaData();
		int size=rsmd.getColumnCount();
		if(rs.next()){
			for(int i=1;i<=size;i++){
				String name=rsmd.getColumnLabel(i);
				Object obj=rs.getObject(name);
				map.put(name, obj==null?"":convertCallBack.convert(obj, defaultValue, type));
			}
		}
	}*/

	public int executeUpdate(String sql, Object ... vars) throws DaoException {
		logger.debug("execute {}",sql);
		logger.debug(Arrays.toString(vars));
		int res=0;
		Connection conn=null;
		PreparedStatement stmt=null;
		try {
			conn=dataSource.getConnection();
			stmt=conn.prepareStatement(sql);
			int index = 1;
			if(vars!=null&&vars.length != 0){
				for(int i = 0;i <vars.length;i++){
					stmt.setObject(index++, vars[i]);
				}
			}
			res = stmt.executeUpdate();
		} catch (SQLException e) {
			logger.error("修改出错:"+sql, e);
			logger.error(Arrays.toString(vars));
			throw new DaoException(e.getMessage());
		}finally{
			stmt=closePreStmt(stmt);
			conn=closeConn(conn);
		}
		return res;
	}
	
	public boolean execute(String sql, Object ... vars) throws DaoException {
		logger.debug("execute {}",sql);
		logger.debug(Arrays.toString(vars));
		boolean res=false;
		Connection conn=null;
		PreparedStatement stmt=null;
		try {
			conn=dataSource.getConnection();
			stmt=conn.prepareStatement(sql);
			int index = 1;
			if(vars!=null&&vars.length != 0){
				for(int i = 0;i <vars.length;i++){
					stmt.setObject(index++, vars[i]);
				}
			}
			res = stmt.execute();
		} catch (SQLException e) {
			logger.error("修改出错:"+sql, e);
			logger.error(Arrays.toString(vars));
			throw new DaoException(e.getMessage());
		}finally{
			stmt=closePreStmt(stmt);
			conn=closeConn(conn);
		}
		return res;
	}

	public int insert(String sql,Map<String, Object> map,InsertCallBack insertCallBack) throws Exception {
		logger.debug("execute {}",sql);
		Connection conn=null;
		PreparedStatement stmt = null;
		int i=-1;
		try {
			conn=dataSource.getConnection();
			stmt = conn.prepareStatement(sql);
			insertCallBack.addStmt(map,stmt);
			stmt.execute();
			i=1;
		} catch (Exception e) {
			logger.error("查询出错", e);
			throw new SysException(e.getMessage());
		} finally {
			JdbcDao.closeStmt(stmt);
			JdbcDao.closeConn(conn);
			stmt = null;
			conn=null;
		}
		return i;
	}

	public int update(String sql,Map<String, Object> map,UpdateCallBack updateCallBack) throws Exception {
		logger.debug("execute {}",sql);
		PreparedStatement stmt=null;
		Connection targetConn=null;
		int i=-1;
		try{
			targetConn=dataSource.getConnection();
			//targetConn.setAutoCommit(false);
			stmt=targetConn.prepareStatement(sql);
			updateCallBack.addStmt(map,stmt);
			stmt.executeUpdate();
			//targetConn.commit();
			i=1;
		}catch (Exception e){
			logger.error("更新出错 "+sql,e);
			throw new SysException(e.getMessage());
		}finally {
			JdbcDao.closeStmt(stmt);
			JdbcDao.closeConn(targetConn);
			stmt=null;
			targetConn=null;
		}
		return i;
	}



	public int executeUpdate(String sql,List<Map<String, Object>> updateList,UpdateCallBack updateCallBack) throws Exception {
		return executeUpdate(false,sql,updateList,updateCallBack);
	}
	public int executeUpdate(boolean autoCommit,String sql,List<Map<String, Object>> updateList,UpdateCallBack updateCallBack) throws Exception {
		logger.debug("execute {}",sql);
		int res=-1;
		PreparedStatement stmt=null;
		Connection targetConn=null;
		try{
			int i=0;
			targetConn=dataSource.getConnection();
			targetConn.setAutoCommit(autoCommit);
			stmt=targetConn.prepareStatement(sql);
			for(Map<String,Object> data:updateList){
				updateCallBack.addStmt(data,stmt);
				stmt.addBatch();
				i++;
			}
			stmt.executeBatch();
			targetConn.commit();
			res=i;
		}catch (Exception e){
			JdbcDao.rollback(targetConn);
			logger.error("修改出错 " +sql,e);
			throw new SysException(e.getMessage());
		}finally {
			JdbcDao.closeStmt(stmt);
			JdbcDao.closeConn(targetConn);
			stmt=null;
			targetConn=null;
		}
		return res;
	}

	public int executeInsert(String sql,List<Map<String, Object>> insertList,InsertCallBack insertCallBack) throws Exception {
		return executeInsert(false,sql,insertList,insertCallBack);
	}

	public int executeInsert(boolean autoCommit,String sql,List<Map<String, Object>> insertList,InsertCallBack insertCallBack) throws Exception {
		logger.debug("execute {}",sql);
		int res=-1;
		PreparedStatement stmt=null;
		Connection targetConn=null;
		try{
			targetConn=dataSource.getConnection();
			targetConn.setAutoCommit(autoCommit);
			stmt=targetConn.prepareStatement(sql);
			int i=0;
			for(Map<String,Object> data:insertList){

				insertCallBack.addStmt(data,stmt);
				stmt.addBatch();
				i++;
			}
			stmt.executeBatch();
			targetConn.commit();
			res=i;
		}catch (Exception e){
			JdbcDao.rollback(targetConn);
			logger.error("插入出错 "+sql,e);
			throw new SysException(e.getMessage());
		}finally {
			JdbcDao.closeStmt(stmt);
			JdbcDao.closeConn(targetConn);
			stmt=null;
			targetConn=null;
		}
		return res;
	}
	public int executeInsertJson(String sql, List<JSONObject> insertList, InsertCallBack insertCallBack) throws Exception {
		logger.debug("execute {}",sql);
		int res=-1;
		PreparedStatement stmt=null;
		Connection targetConn=null;
		try{
			targetConn=dataSource.getConnection();
			targetConn.setAutoCommit(false);
			stmt=targetConn.prepareStatement(sql);
			int i=0;
			for(JSONObject data:insertList){
				insertCallBack.addStmt(data,stmt);
				stmt.addBatch();
				i++;
			}
			stmt.executeBatch();
			targetConn.commit();
			res=i;
		}catch (Exception e){
			JdbcDao.rollback(targetConn);
			logger.error("插入出错 "+sql,e);
			throw new SysException(e.getMessage());
		}finally {
			JdbcDao.closeStmt(stmt);
			JdbcDao.closeConn(targetConn);
			stmt=null;
			targetConn=null;
		}
		return res;
	}
	public int executeInsertJson(String sql, JSONArray insertList, InsertCallBack insertCallBack) throws Exception {
		logger.debug("execute {}",sql);
		int res=-1;
		PreparedStatement stmt=null;
		Connection targetConn=null;
		try{
			targetConn=dataSource.getConnection();
			targetConn.setAutoCommit(false);
			stmt=targetConn.prepareStatement(sql);
			int i=0;
			for(;i<insertList.size();i++){
				JSONObject data=insertList.getJSONObject(i);
				insertCallBack.addStmt(data,stmt);
				stmt.addBatch();
				i++;
			}
			stmt.executeBatch();
			targetConn.commit();
			res=i;
		}catch (Exception e){
			JdbcDao.rollback(targetConn);
			logger.error("插入出错 "+sql,e);
			throw new SysException(e.getMessage());
		}finally {
			JdbcDao.closeStmt(stmt);
			JdbcDao.closeConn(targetConn);
			stmt=null;
			targetConn=null;
		}
		return res;
	}



	public static Connection createConn(String driver,String url,String username,String password){
		Connection conn=null;
		try {
			Class.forName(driver);
			conn=DriverManager.getConnection(url,username,password);
		} catch (Exception e) {
			e.printStackTrace();
		}
		return conn;
	}

	public static DataSource createDataSource(String driver, String url,
											  String username, String password,
											  DataSourceBean dataSourceBean){
		DataSource dataSource=null;
		try {
			dataSource=connectionDataSource(driver,url,username,password,dataSourceBean);
		} catch (Exception e) {
			e.printStackTrace();
		}
		return dataSource;
	}

	public static DataSource createDbcpDataSource(String driver, String url,
											  String username, String password,
											  DataSourceBean dataSourceBean){
		BasicDataSource basicDataSource=null;
		try {
			basicDataSource=new BasicDataSource();
			basicDataSource.setDriverClassName(driver);
			basicDataSource.setUrl(url);
			basicDataSource.setUsername(username);
			basicDataSource.setPassword(password);
			basicDataSource.initPool(new DbConfig(),dataSourceBean);
			//dataSource=connectionDataSource(driver,url,username,password,dataSourceBean);
		} catch (Exception e) {
			e.printStackTrace();
		}
		return basicDataSource;
	}

	public static DataSource createTrDataSource(String driver, String url,
											  String username, String password){
		TryToReconnectDataSource dataSource=
				new TryToReconnectDataSource(driver,url,username,password);
		return dataSource;
	}


	private static DataSource
	connectionDataSource(String driver,
					   String url, String username,
					   String password, DataSourceBean dataSource) throws SQLException {
		/*if(dataSourceMap.containsKey(type)){
			return dataSourceMap.get(type).getConnection();
		}*/
		DruidDataSource druidXADataSource=new DruidDataSource();
		druidXADataSource.setDriverClassName(driver);
		druidXADataSource.setUrl(url);
		druidXADataSource.setUsername(username);
		druidXADataSource.setPassword(password);
		BasicDataSource.initXaPool(druidXADataSource,dataSource,new DbConfig());
		/*BasicDataSource bds=new BasicDataSource();
		bds.setDriverClassName(driver);
		bds.setUrl(url);
		bds.setUsername(username);
		bds.setPassword(password);
		bds.initPool(new DbConfig(),dataSource);*/
		//dataSourceMap.put(type,druidXADataSource);
		return druidXADataSource;
	}


	/**
	 * 功能描述:回滚
	 *
	 * @param conn
	 */
	public static void rollback(Connection conn) {
		if (conn!= null) {
			try {
				conn.rollback();
			} catch (SQLException e) {
				logger.error("数据回滚失败",e);
			}
		}
	}

	/**
	 * 功能描述:关闭连接
	 *
	 * @param conn
	 */
	public static Connection closeConn(Connection conn) {
		if (conn!=null) {
			try {
				conn.close();
			} catch (SQLException e) {
				logger.error("关闭连接失败",e);
			} finally {
				conn = null;
			}
		}
		return conn;
	}

	/**
	 * 功能描述:关闭statement
	 *
	 * @param stmt
	 */
	public static PreparedStatement closePreStmt(PreparedStatement stmt) {
		if (stmt!=null) {
			try {
				stmt.close();
			} catch (SQLException e) {
				logger.error("关闭PreparedStatement失败",e);
			} finally {
				stmt = null;
			}
		}
		return stmt;
	}
	public static void closePreStmt(PreparedStatement ... stmts) {
		for(PreparedStatement stmt:stmts){
			closePreStmt(stmt);
		}
	}

	/**
	 * 功能描述:关闭结果集
	 *
	 * @param rs
	 */
	public static ResultSet closeResultSet(ResultSet rs) {
		if (rs!=null) {
			try {
				rs.close();
			} catch (SQLException e) {
				logger.error("关闭ResultSet失败",e);
			} finally {
				rs = null;
			}
		}
		return rs;
	}

	public static Statement closeStmt(Statement stmt) {
		if (stmt!=null) {
			try {
				stmt.close();
			} catch (SQLException e) {
				logger.error("关闭Statement失败",e);
			} finally {
				stmt = null;
			}
		}
		return stmt;
	}

	public static void closeStmt(Statement ... stmts) {
		for(Statement stmt:stmts){
			closeStmt(stmt);
		}
	}

	public static void commit(Connection conn) throws SQLException {
		logger.info("提交事务");
		if (conn!=null) {
			conn.commit();
		}
	}


	public DataSource getDataSource() {
		return dataSource;
	}

	public void setDataSource(DataSource dataSource) {
		this.dataSource = dataSource;
	}


	public void setConnection(Connection connection){
		this.dataSource=new SingleDataSource(connection);
	}
}


