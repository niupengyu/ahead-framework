package com.github.niupengyu.core.listener;

import com.github.niupengyu.core.util.Hello;
import com.github.niupengyu.core.util.word.Started;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.boot.context.event.ApplicationReadyEvent;
import org.springframework.context.ApplicationListener;

import java.time.LocalDateTime;
import java.util.Random;

public class ReadyEvent implements ApplicationListener<ApplicationReadyEvent> {

    private static final Logger logger= LoggerFactory.getLogger(ReadyEvent.class);

    @Override
    public void onApplicationEvent(ApplicationReadyEvent applicationReadyEvent) {
        logger.info("========================================");
        logger.info("");
        logger.info(Hello.word());
        logger.info("");
        logger.info(Started.say());
        logger.info("");
        logger.info("应用启动完成! 本次启动击败了全球99.99%的服务器,[0]项需优化,[0]项需加速!");
        logger.info("");
        logger.info("好棒棒！");
        logger.info("========================================");
    }

    public static void main(String[] args) {
        LocalDateTime localDateTime=LocalDateTime.now();
        int h=localDateTime.getHour();
        int w=localDateTime.getDayOfWeek().getValue();
        System.out.println(h+" "+w);
        System.out.println(new Random().nextInt(3));
        System.out.println(Hello.word());
    }




}
