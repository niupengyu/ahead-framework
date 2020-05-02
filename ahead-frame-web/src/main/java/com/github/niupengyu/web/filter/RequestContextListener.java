package com.github.niupengyu.web.filter;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.servlet.*;
import javax.servlet.annotation.WebListener;


@WebListener
public class RequestContextListener implements ServletRequestListener,
    ServletRequestAttributeListener {

  Logger logger = LoggerFactory.getLogger(RequestContextListener.class);


  @Override
  public void attributeAdded(ServletRequestAttributeEvent srae) {
    //logger.info("attributeAdded "+srae.getName());
  }

  @Override
  public void attributeRemoved(ServletRequestAttributeEvent srae) {
    //logger.info("attributeRemoved "+srae.getName());
  }

  @Override
  public void attributeReplaced(ServletRequestAttributeEvent srae) {
    //logger.info("attributeReplaced "+srae.getName());
  }

  @Override
  public void requestDestroyed(ServletRequestEvent sre) {
    //logger.info("requestDestroyed "+sre.getServletRequest());
  }

  @Override
  public void requestInitialized(ServletRequestEvent sre) {
    //logger.info("requestInitialized "+sre.getServletRequest());
  }
}


