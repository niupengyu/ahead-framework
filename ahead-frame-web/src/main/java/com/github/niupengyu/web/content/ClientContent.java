/**  
 * 文件名: ClientController.java
 * 包路径: cn.newsframework.sys.controller
 * 创建描述  
 *        @createPerson：牛鹏宇 
 *        @createDate：2015-11-7 下午01:14:42
 *        内容描述：
 * 修改描述  
 *        @updatePerson：牛鹏宇
 *        @updateDate：2015-11-7 下午01:14:42 
 *        修改内容:
 * 版本: V1.0   
 */
package com.github.niupengyu.web.content;

import com.github.niupengyu.web.beans.ResponseData;
import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.github.niupengyu.core.exception.SysException;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.PrintWriter;


public class ClientContent<T> extends RequestContent<T>
{

	private static final long serialVersionUID = 1L;
	
	public static boolean stateSuccess=true;
	
	public static boolean stateError=true;
	
	protected  String josontoString1(String jsonarray){
		
    	return  "{'obj':{'objlist':"+jsonarray+"}}";
    }
	
	protected void jsonSuccess(PrintWriter out){
		
		out.write(json(session().getId(), System.currentTimeMillis(), stateSuccess, SUCCESS, 200));
	}
	

	protected void jsonSuccess(PrintWriter out,JSON data){
		out.write(json(session().getId(), System.currentTimeMillis(), stateSuccess, SUCCESS, 200,data));
	}

	protected void jsonError(PrintWriter out,String message,int code){

		out.write(json(session().getId(), System.currentTimeMillis(), stateError, message, code));
	}
	
	protected String returnSuccess(){
		return json(session().getId(), System.currentTimeMillis(), stateSuccess, SUCCESS, 200);
	}

	protected String returnSuccess(String... data) {
		return json(session().getId(), System.currentTimeMillis(), stateSuccess, SUCCESS, 200,data);
	}

	protected String returnSuccess(Object data){
		return json(session().getId(), System.currentTimeMillis(), stateSuccess, SUCCESS, 200,data);
	}

	protected String returnSuccess(JSON data){
		return json(session().getId(), System.currentTimeMillis(), stateSuccess, SUCCESS, 200,data);
	}
	
	protected void jsonSuccess(PrintWriter out,String message,int code){
		
		out.write(json(session().getId(), System.currentTimeMillis(), stateSuccess, message, code));
	}
	
	protected void jsonSuccess(PrintWriter out,int code){
		
		out.write(json(session().getId(), System.currentTimeMillis(), stateSuccess, SUCCESS, code));
	}
	
	
	
	protected void jsonState(PrintWriter out,boolean state,String message,int code){
		
		out.write(json(session().getId(), System.currentTimeMillis(), state, message, code));
	}
	
	
	

	
	
	protected void jsonError(PrintWriter out,int code){
		
		out.write(json(session().getId(), System.currentTimeMillis(), stateError, MESSAGE, code));
	}

	protected String error(){
		return json(session().getId(), System.currentTimeMillis(), stateError, MESSAGE, 500);
	}

	protected String error(Exception e){
		return json(session().getId(), System.currentTimeMillis(), stateError, e.getMessage(), 500);
	}
	
	protected void jsonError(PrintWriter out,SysException e){
		
		out.write(json(session().getId(), System.currentTimeMillis(), stateError,e.getMessage(), 500));
	}
	
	protected void jsonError(PrintWriter out,String message){
		out.write(json(session().getId(), System.currentTimeMillis(), stateError, message, 500));
	}
	
	protected void jsonError(PrintWriter out){
		out.write(json(session().getId(), System.currentTimeMillis(), stateError, MESSAGE, 500));
	}
	
	protected String returnError(){
		return json(session().getId(), System.currentTimeMillis(), stateError, MESSAGE, 500);
	}
	
	public static String json(String token,long time,boolean state,String message,int code){
		JSONObject njo=new JSONObject();
		njo.put("token", token);
		njo.put("long", time);
		njo.put("state",state);
		njo.put("message",message);
		njo.put("code",code);
		return njo.toJSONString();
	}
	
	public static String json(String token,long time,boolean state,
			String message,int code,JSON data){
		JSONObject njo=new JSONObject();
		njo.put("token", token);
		njo.put("long", time);
		njo.put("state",state);
		njo.put("message",message);
		njo.put("code",code);
		njo.put("data", data);
		return njo.toJSONString();
	}

	public static String json(String token,long time,boolean state,
							  String message,int code,String data){
		JSONObject njo=new JSONObject();
		njo.put("token", token);
		njo.put("long", time);
		njo.put("state",state);
		njo.put("message",message);
		njo.put("code",code);
		njo.put("data", data);
		return njo.toJSONString();
	}

	public static String json(String token,long time,boolean state,
							  String message,int code,Object data){
		JSONObject njo=new JSONObject();
		njo.put("token", token);
		njo.put("long", time);
		njo.put("state",state);
		njo.put("message",message);
		njo.put("code",code);
		njo.put("data", data);
		return njo.toJSONString();
	}

	protected ResponseData rdSuccess(){
		ResponseData responseData=new ResponseData(session().getId(), stateSuccess, SUCCESS, 200);
//		return json(session().getId(), System.currentTimeMillis(), stateSuccess, SUCCESS, 200);
		return responseData;
	}

	protected ResponseData rdError(int code){
		ResponseData responseData=new ResponseData(session().getId(), stateSuccess, MESSAGE, code);
//		return json(session().getId(), System.currentTimeMillis(), stateSuccess, SUCCESS, 200);
		return responseData;
	}

	protected ResponseData rdError(){
		ResponseData responseData=new ResponseData(session().getId(), stateSuccess, MESSAGE, 500);
//		return json(session().getId(), System.currentTimeMillis(), stateSuccess, SUCCESS, 200);
		return responseData;
	}

	protected ResponseData rdSuccess(Object data){
		ResponseData responseData=new ResponseData(session().getId(), stateSuccess, SUCCESS, 200,data);
//		return json(session().getId(), System.currentTimeMillis(), stateSuccess, SUCCESS, 200);
		return responseData;
	}

	protected ResponseData rdSuccess(String data){
		ResponseData responseData=new ResponseData(session().getId(), stateSuccess, SUCCESS, 200,data);
//		return json(session().getId(), System.currentTimeMillis(), stateSuccess, SUCCESS, 200);
		return responseData;
	}

	protected ResponseData rdSuccess(boolean data){
		ResponseData responseData=new ResponseData(session().getId(), stateSuccess, SUCCESS, 200,data);
//		return json(session().getId(), System.currentTimeMillis(), stateSuccess, SUCCESS, 200);
		return responseData;
	}







	protected ResponseData rdError(Object data){
		ResponseData responseData=new ResponseData(session().getId(), stateSuccess, MESSAGE, 201,data);
//		return json(session().getId(), System.currentTimeMillis(), stateSuccess, SUCCESS, 200);
		return responseData;
	}

	protected ResponseData rdError(String message){
		ResponseData responseData=new ResponseData(session().getId(), stateSuccess, message, 201);
//		return json(session().getId(), System.currentTimeMillis(), stateSuccess, SUCCESS, 200);
		return responseData;
	}

	protected ResponseData rdError(String message,Object data){
		ResponseData responseData=new ResponseData(session().getId(), stateSuccess, message, 201,data);
//		return json(session().getId(), System.currentTimeMillis(), stateSuccess, SUCCESS, 200);
		return responseData;
	}


	/*@Override
	public T setLogin(HttpServletRequest request, HttpServletResponse response) {
		return null;
	}*/
}


