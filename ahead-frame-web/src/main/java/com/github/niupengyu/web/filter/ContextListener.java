package com.github.niupengyu.web.filter;

import com.github.niupengyu.core.util.Hello;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.servlet.ServletContextAttributeEvent;
import javax.servlet.ServletContextAttributeListener;
import javax.servlet.ServletContextEvent;
import javax.servlet.ServletContextListener;
import javax.servlet.annotation.WebListener;


@WebListener
public class ContextListener implements ServletContextListener,
    ServletContextAttributeListener {

  Logger logger = LoggerFactory.getLogger(ContextListener.class);

  @Override
  public void contextDestroyed(ServletContextEvent arg0) {
    //logger.info("【监听到】应用被关闭!");
  }


  @Override
  public void contextInitialized(ServletContextEvent arg0) {
//    logger.info("========================================");
//    logger.info("");
//    logger.info(Hello.word());
//    logger.info("");
//    logger.info("应用启动完成! 本次启动击败了全球99.99%的服务器,[0]项需优化,[0]项需加速!");
//    logger.info("好棒棒！");
//    logger.info("========================================");
  }


  @Override
  public void attributeAdded(ServletContextAttributeEvent arg0) {
//    log.info("【监听到】ServletContext对象中新增一名为" + arg0.getName()
//        + "的属性,其属性值为:" + arg0.getValue());
  }


  @Override
  public void attributeRemoved(ServletContextAttributeEvent arg0) {
//    log.info("【监听到】ServletContext对象中一名为" + arg0.getName()
//        + "的属性被删除!");
  }


  @Override
  public void attributeReplaced(ServletContextAttributeEvent arg0) {
//    log.info("【监听到】ServletContext对象中一名为" + arg0.getName()
//        + "的属性被更新!");
  }
}


