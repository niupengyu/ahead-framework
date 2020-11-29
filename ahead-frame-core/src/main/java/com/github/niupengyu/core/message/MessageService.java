package com.github.niupengyu.core.message;

import com.github.niupengyu.core.exception.SysException;

import java.util.List;

public abstract class MessageService<T> extends AbstractMessageService<T> implements Runnable{

    public MessageService(){

    }

    public MessageService(String name) {
        super(name);
    }

    public MessageService(MessageManager<T> dataManager) {
        super(dataManager);
    }

    /**
     * 实现 runnable 接口 需要手动创建一个线程 运行起来 他会 不断的获取最新的消息
     */
    @Override
    public void run() {
        while(true){
            T messageBean=this.getMessage();
            if(messageBean==null){
                boolean state=this.isStop();
                if(state){
                    break;
                }else{
                    continue;
                }
            }
            this.execute(messageBean);

        }
        this.endExecute();
    }

    protected abstract void endExecute();


    public static void main(String[] args) {
        new MessageService(new MessageManager("")) {
            @Override
            public void execute(Object messageBean) {

            }

            @Override
            protected void endExecute() {

            }
        };
    }

}
