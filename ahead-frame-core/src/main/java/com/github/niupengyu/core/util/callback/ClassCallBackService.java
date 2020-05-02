/**  
 * 文件名: ClassCallBackService.java
 * 包路径: cn.newsframework.core.util.callback
 * 创建描述  
 *        @createPerson：牛鹏宇 
 *        @createDate：2015-6-29 上午10:26:19
 *        内容描述：
 * 修改描述  
 *        @updatePerson：牛鹏宇
 *        @updateDate：2015-6-29 上午10:26:19 
 *        修改内容:
 * 版本: V1.0   
 */
package com.github.niupengyu.core.util.callback;

import com.github.niupengyu.core.util.StringUtil;

public class ClassCallBackService
{
	
	private InvokeCallback invokeCallback;
	
	public ClassCallBackService()
	{
		
	}
	
	public ClassCallBackService(InvokeCallback invokeCallback)
	{
		this.invokeCallback=invokeCallback;
	}
	
	

	public String invokefield(String substring, Object field) throws Exception
	{
		if(StringUtil.isNull(invokeCallback)){
			throw new Exception("未实现"+InvokeCallback.class.getName()+"接口，或未制定实现类");
		}
		int id=Integer.parseInt(substring);
		return invokeCallback.invokefield(id,field);
	}

	public String invokemehod(String substring, Object value) throws Exception
	{
		if(StringUtil.isNull(invokeCallback)){
			throw new Exception("未实现"+InvokeCallback.class.getName()+"接口，或未制定实现类");
		}
		int id=Integer.parseInt(substring);
		return invokeCallback.invokemehod(id,value);
	}

	public String invokeobject(String substring, Object bean) throws Exception
	{
		if(StringUtil.isNull(invokeCallback)){
			throw new Exception("未实现"+InvokeCallback.class.getName()+"接口，或未制定实现类");
		}
		int id=Integer.parseInt(substring);
		return invokeCallback.invokeobject(id,bean);
	}
	
}


