package com.github.niupengyu.jdbc.datasource;

import org.springframework.jdbc.datasource.lookup.AbstractRoutingDataSource;


public class DynamicDataSource extends AbstractRoutingDataSource {

  /**
   * override determineCurrentLookupKey
   * <p>
   * Title: determineCurrentLookupKey
   * </p>
   * <p>
   * Description: 自动查找datasource
   * </p>
   */

  protected Object determineCurrentLookupKey() {

    String dataSource=DBContextHolder.getDbType();
    return dataSource;
  }

}