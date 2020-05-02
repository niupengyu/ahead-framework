/**
 * 文件名: DataSourceType.java
 * 包路径: cn.newsframework.aop.factory
 * 创建描述
 *
 * @createPerson：牛鹏宇
 * @createDate：2016-11-28 下午5:31:33 内容描述： 修改描述
 * @updatePerson：牛鹏宇
 * @updateDate：2016-11-28 下午5:31:33 修改内容: 版本: V1.0
 */
package com.github.niupengyu.jdbc.bean;

public class DataSourceType {

  private String dataSource;

  private boolean autoCommit;

//	public static ThreadLocal<ZeroTransactionalManager> contextHolders = new ThreadLocal<ZeroTransactionalManager>();

  public DataSourceType(String dataSource, boolean flag) {
    this.dataSource = dataSource;
    this.autoCommit = flag;
  }

  public String getDataSource() {
    return dataSource;
  }

  public boolean isAutoCommit() {
    return autoCommit;
  }

}


