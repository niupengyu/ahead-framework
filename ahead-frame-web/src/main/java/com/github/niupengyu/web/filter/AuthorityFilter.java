/**
 * 文件名: MobileClientFilter.java
 * 包路径: cn.newsframework.sys.filter
 * 创建描述
 *
 * @createPerson：牛鹏宇
 * @createDate：2015-11-9 上午10:50:10 内容描述： 修改描述
 * @updatePerson：牛鹏宇
 * @updateDate：2015-11-9 上午10:50:10 修改内容: 版本: V1.0
 */
package com.github.niupengyu.web.filter;


import com.github.niupengyu.core.util.StringUtil;
import com.alibaba.fastjson.JSONArray;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.servlet.*;
import javax.servlet.annotation.WebFilter;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.HashSet;
import java.util.Set;


//@WebFilter(filterName = "authorityFilter", urlPatterns = "/*")
public class AuthorityFilter implements Filter {


  //private String allowOrigin;
  private Set<String> allowOriginsSet=new HashSet<>();
  private String allowMethods;
  private String maxAge;
  private String allowHeaders ;
  private String allowCredentials;


  private static final Logger logger = LoggerFactory.getLogger(AuthorityFilter.class);

  public void init(FilterConfig config) throws ServletException {
   // this.init(config);
    //allowOrigin = StringUtil.valueOf(config.getInitParameter("allowOrigin"),"");
    allowMethods = StringUtil.valueOf(config.getInitParameter("allowMethods"),"");
    maxAge = StringUtil.valueOf(config.getInitParameter("maxAge"),"");
    allowHeaders = StringUtil.valueOf(config.getInitParameter("allowHeaders"),"");
    allowCredentials = StringUtil.valueOf(config.getInitParameter("allowCredentials"),"");
    String allowOrigins=StringUtil.valueOf(config.getInitParameter("allowOrigins"),"");
    if(StringUtil.notNull(allowOrigins)){
      allowOriginsSet=JSONArray.parseObject(allowOrigins,Set.class);
    }
  }



  public void doFilter(ServletRequest request, ServletResponse response, FilterChain chain)
      throws IOException, ServletException {
    // 得到HttpServletRequest
    HttpServletRequest req = (HttpServletRequest) request;
    // 得到HttpServletResponse
    HttpServletResponse res = (HttpServletResponse) response;

    String type=req.getMethod();

     if (type.toUpperCase().equals("OPTIONS")==true) {
       String referer=req.getHeader("Referer");
       if(referer.endsWith("/")){
         referer=referer.substring(0,referer.length()-1);
       }
       res.setHeader("Access-Control-Allow-Origin", referer);
     }else{
       String origin=req.getHeader("origin");
       boolean flag=StringUtil.notNull(origin)&&allowOriginsSet.contains(origin);
       if(flag){
         res.setHeader("Access-Control-Allow-Origin", origin);
       }
     }

    //else{
    //  res.setHeader("Access-Control-Allow-Origin", allowOrigin);
    //}
    res.setHeader("Access-Control-Allow-Methods", allowMethods);
    res.setHeader("Access-Control-Max-Age", maxAge);
    res.setHeader("Access-Control-Allow-Headers", allowHeaders);
    res.setHeader("Access-Control-Allow-Credentials", allowCredentials);
    chain.doFilter(req, res);

  }


  @Override
  public void destroy() {
    logger.info("销毁 " + this.getClass().getName());
  }

  public static void main(String[] args) {
    System.out.println("sdfsdf/".substring(0,"sdfsdf/".length()-1));
  }
}


