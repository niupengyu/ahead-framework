package com.github.niupengyu.core.listener;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.context.event.ApplicationStartingEvent;
import org.springframework.context.ApplicationListener;

public class StartListener implements ApplicationListener<ApplicationStartingEvent> {

  public static final Logger logger = LoggerFactory.getLogger(StartListener.class);

  @Override
  public void onApplicationEvent(ApplicationStartingEvent event) {

    SpringApplication app = event.getSpringApplication();
//		app.setShowBanner(false);
    logger.info("==MyApplicationStartedEventListener==");

  }

}
