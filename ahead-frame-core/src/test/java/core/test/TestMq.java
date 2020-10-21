package core.test;

import com.github.niupengyu.core.exception.SysException;
import com.github.niupengyu.core.message.MessageService;

import java.util.Arrays;
import java.util.List;


public class TestMq extends MessageService<String>/* 这里定义消息的数据类型 可以是自定义的bean */ {

    /**
     * 构造器
     *
     * @param name
     */
    public TestMq(String name) {
        super(name);
    }

    @Override
    protected void endExecute() {

    }

    public static void main(String[] args) throws SysException {
        TestMq testMq=new TestMq("消息测试");
        //初始化 消息队列并启动
        Thread thread=new Thread(testMq);
        thread.start();

        //模拟生产者 发送消息
        for(int i=0;i<3;i++){
            testMq.add("test message"+3);
        }
    }

    /**
     * 处理消息的实现类
     * @param messageBean
     */
    @Override
    public void execute(String messageBean) {
        System.out.println("收到了消息 "+messageBean);
    }
}
