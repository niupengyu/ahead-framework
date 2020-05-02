/**  
 * 文件名: DaoException.java
 * 包路径: com.github.niupengyu.jdbc.exception
 * 创建描述  
 *        @createPerson：牛鹏宇 
 *        @createDate：2017年9月26日 下午4:28:09
 *        内容描述：
 * 修改描述  
 *        @updatePerson：牛鹏宇
 *        @updateDate：2017年9月26日 下午4:28:09 
 *        修改内容:
 * 版本: V1.0   
 */
package com.github.niupengyu.jdbc.exception;

import java.sql.SQLException;

public class DaoException extends SQLException{
	
	
	public DaoException(String message) {
		super(message);
	}

	private static final long serialVersionUID = 1L;

	
}


