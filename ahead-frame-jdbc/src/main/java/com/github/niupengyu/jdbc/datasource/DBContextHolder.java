package com.github.niupengyu.jdbc.datasource;


import com.github.niupengyu.jdbc.bean.DataSourceType;

public class DBContextHolder {

  private static String DB_TYPE_RW = "dataSource";
  private static ThreadLocal<DataSourceType> contextHolder = new ThreadLocal<DataSourceType>();

  /**
   * 线程threadlocal
   */


  public static String getDbType() {
    chekDataSrouceType();
    DataSourceType dt = contextHolder.get();
    return dt.getDataSource();
  }

  /**
   * 设置本线程的dbtype
   *
   * @see [相关类/方法](可选)
   * @since [产品/模块版本](可选)
   */
  public static void setDbType(String str, boolean flag) {
    DataSourceType dt = new DataSourceType(str, flag);
    contextHolder.set(dt);
  }

  /**
   * 设置本线程的dbtype
   *
   * @see [相关类/方法](可选)
   * @since [产品/模块版本](可选)
   */
  public static void setDbType(String str) {
    DataSourceType dt = new DataSourceType(str, true);
    contextHolder.set(dt);
  }

  /**
   * clearDBType
   *
   * @Title: clearDBType
   * @Description: 清理连接类型
   */
  public static void clearDBType() {
    contextHolder.remove();
  }

  public static boolean getAutoCommit() {
    chekDataSrouceType();
    return contextHolder.get().isAutoCommit();
  }

  public static void chekDataSrouceType() {
    DataSourceType dt = contextHolder.get();
    if (dt == null) {
      dt = new DataSourceType(DB_TYPE_RW, true);// 默认是读写库
      contextHolder.set(dt);
    }
  }
}