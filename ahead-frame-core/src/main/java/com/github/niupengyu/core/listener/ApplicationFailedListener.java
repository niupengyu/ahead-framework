package com.github.niupengyu.core.listener;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.boot.context.event.ApplicationFailedEvent;
import org.springframework.context.ApplicationListener;
import org.springframework.stereotype.Component;

import java.util.Random;

public class ApplicationFailedListener implements ApplicationListener<ApplicationFailedEvent> {

  private static final Logger logger = LoggerFactory.getLogger(ApplicationFailedEvent.class);

  private String[] failed=new String[]{
          "启动失败了？没关系 找找问题在哪 再来一次!",
          "启动失败了？别急 先抽根烟冷静一下。。。",
          "启动失败？别着急 休息 休息一下",
          "看成败人生豪迈，大不了从头再来",
          "报错了？重启！ ",
          "失败是成功之母。。",
          "当你试图解决一个你不理解的问题时，复杂化就产成了。",
          "它在我的机器上可以很好运行！",
          "如果你交给某人一个程序，你将折磨他一整天；如果你教某人如何编写程序，你将折磨他一辈子。"
  };

  public void onApplicationEvent(ApplicationFailedEvent event) {
    Throwable throwable = event.getException();
    Random random=new Random();
    int index=random.nextInt(failed.length);
    String message=failed[index];
    logger.error(message, throwable);
    logger.error(message);
  }

}
