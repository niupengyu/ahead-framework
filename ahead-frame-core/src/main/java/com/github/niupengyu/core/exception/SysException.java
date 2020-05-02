/**  
 * 文件名: SysException.java
 * 包路径: com.github.niupengyu.core.exception
 * 创建描述  
 *        @createPerson：牛鹏宇 
 *        @createDate：2017年7月21日 下午12:51:44
 *        内容描述：
 * 修改描述  
 *        @updatePerson：牛鹏宇
 *        @updateDate：2017年7月21日 下午12:51:44 
 *        修改内容:
 * 版本: V1.0   
 */
package com.github.niupengyu.core.exception;

public class SysException extends Exception{
	
	/**
	 * @Fields serialVersionUID : 
	 */
	private static final long serialVersionUID = 1L;

	private String message="ERROR";

	private int code=201;

	public SysException()
	{
	}

	public SysException(String message){
		this.message=message;
	}

	public SysException(String message,int code){
		this.message=message;
		this.code=code;
	}

	public SysException(String message, Throwable cause) {
		super(message, cause);
	}

	public SysException(String message, Throwable cause, boolean enableSuppression, boolean
			writableStackTrace) {
		super(message, cause, enableSuppression, writableStackTrace);
	}

	public SysException(Throwable cause) {
		super(cause);
	}

	@Override
	public String getMessage()
	{
		return message;
	}

	public int getCode() {
		return code;
	}

}


