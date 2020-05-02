/**  
  * 文件名: MyInitializer.java
 * 包路径: cn.newsframework.sys
 * 创建描述  
 *        @createPerson：尹志辉 
 *        @createDate：2013-4-25 上午11:08:37
 *        内容描述：操作次数
 * 修改描述  
 *        @updatePerson：
 *        @updateDate：
 *        修改内容:
 * 版本: V1.0   
 */
package com.github.niupengyu.web.initialier;

import com.github.niupengyu.web.content.ContentController;
import com.github.niupengyu.web.content.RequestContent;
import com.alibaba.fastjson.JSON;
import com.github.niupengyu.core.util.Content;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.web.method.HandlerMethod;
import org.springframework.web.servlet.handler.HandlerInterceptorAdapter;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.Map;


public class BaseInitializer extends HandlerInterceptorAdapter {
	
	private static final Logger logger= LoggerFactory.getLogger(BaseInitializer.class);
	
	@Override
	public boolean preHandle(HttpServletRequest request,
			HttpServletResponse response, Object handler) throws Exception {
		//配置全局session request response 
		logger.debug("url:" +request.getRequestURL());
		/*Enumeration<String> enumeration=request.getParameterNames();
		Map<String,String[]> paramMapenumeration=new HashMap<>();
		while(enumeration.hasMoreElements()){
			String param=enumeration.nextElement();
			paramMapenumeration.put(param,request.getParameterValues(param));
		}*/
		if(logger.isDebugEnabled()){
			Map<String,String[]> paramMapenumeration=request.getParameterMap();
			logger.debug("param["+ JSON.toJSONString(paramMapenumeration)+"]");
			logger.debug("以上为请求头内容");
		}
		request.setCharacterEncoding(Content.UTF8);
		if(handler instanceof HandlerMethod){
			HandlerMethod dwe=(HandlerMethod)handler;
			if(dwe.getBean() instanceof RequestContent){
				RequestContent cc=(RequestContent)dwe.getBean();
				cc.setServletContent(request, response);
			}else{
				logger.warn("控制器"+dwe.getBean().getClass().getName()
						+"或许没有继承"+RequestContent.class.getName());
			}
		}else{
			return false;
		}
		return super.preHandle(request, response, handler);
	}
	
	@Override
	public void afterCompletion(HttpServletRequest request,
			HttpServletResponse response, Object handler, Exception ex)
			throws Exception {
		response.setCharacterEncoding(Content.UTF8);
//		response.setContentType(ClientContent.CONTENT_JSON);
		super.afterCompletion(request, response, handler, ex);
	}
	
	
	
}


