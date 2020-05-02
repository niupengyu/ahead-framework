/**
 * 文件名: DivDisposableBean.java
 * 包路径: com.github.niupengyu.core.destory
 * 创建描述
 *
 * @createPerson：牛鹏宇
 * @createDate：2017年10月16日 下午4:43:44 内容描述： 修改描述
 * @updatePerson：牛鹏宇
 * @updateDate：2017年10月16日 下午4:43:44 修改内容: 版本: V1.0
 */
package com.github.niupengyu.core.destory;

import com.github.niupengyu.core.util.DateUtil;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.DisposableBean;
import org.springframework.boot.ExitCodeGenerator;
import org.springframework.core.annotation.Order;
import org.springframework.stereotype.Component;

import java.util.Timer;
import java.util.TimerTask;

@Component
@Order(7)
public class DivDisposableBean implements DisposableBean, ExitCodeGenerator {

    private static final Logger logger = LoggerFactory.getLogger(DivDisposableBean.class);

    int m = 3;

    @Override
    public void destroy() throws Exception {
        System.out.print("正在关闭");
        Timer timer = new Timer();
        TimerTask timerTask = new TimerTask() {
            @Override
            public void run() {
                System.out.print(m);
                m--;
            }
        };
        timer.schedule(timerTask, 0, 1000);
        Thread.sleep(m*1000);
        timer.cancel();
        System.out.println();
        logger.info(DateUtil.dateFormat() + "<<<<<<<<<<<......end......>>>>>>>>>>>>>>>");
    }

    @Override
    public int getExitCode() {
        return 5;
    }

    public static void main(String[] args) throws Exception {
//    FutureTask<Integer> fatherObjTask=new FutureTask<Integer>(new DistoryCall<>(5));
//    fatherObjTask.run();
//    fatherObjTask.get();

    }
}