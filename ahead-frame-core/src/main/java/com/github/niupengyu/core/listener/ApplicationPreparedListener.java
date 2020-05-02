package com.github.niupengyu.core.listener;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.boot.context.event.ApplicationPreparedEvent;
import org.springframework.context.ApplicationListener;
import org.springframework.context.ConfigurableApplicationContext;

public class ApplicationPreparedListener implements ApplicationListener<ApplicationPreparedEvent> {

  private static Logger logger = LoggerFactory.getLogger(ApplicationPreparedListener.class);

  public void onApplicationEvent(ApplicationPreparedEvent event) {

    ConfigurableApplicationContext cac = event.getApplicationContext();
    logger.info("========ApplicationPreparedEvent on execute" + cac.getApplicationName());
  }

}
