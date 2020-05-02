/**  
 * 文件名: InvokeCallback.java
 * 包路径: cn.newsframework.core.util.callback
 * 创建描述  
 *        @createPerson：牛鹏宇 
 *        @createDate：2015-6-29 上午10:15:01
 *        内容描述：
 * 修改描述  
 *        @updatePerson：牛鹏宇
 *        @updateDate：2015-6-29 上午10:15:01 
 *        修改内容:
 * 版本: V1.0   
 */
package com.github.niupengyu.core.util.callback;


public interface InvokeCallback
{
	
	/**
	 * 功能描述:属性回调
	 * @param id
	 * @param bean
	 * @return
	 */
	public String invokefield(int id, Object field) throws Exception;

	/**
	 * 功能描述:方法回调
	 * @param id
	 * @param bean
	 * @return
	 */
	public String invokemehod(int id, Object value) throws Exception;

	/**
	 * 功能描述:对象回调
	 * @param id
	 * @param bean
	 * @return
	 */
	public String invokeobject(int id, Object bean) throws Exception;
}


