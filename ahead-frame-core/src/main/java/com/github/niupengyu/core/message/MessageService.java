package com.github.niupengyu.core.message;

import com.github.niupengyu.core.exception.SysException;

public abstract class MessageService<T> implements Runnable{
    /**
     * 消息管理类
     */
    private MessageManager<T>
            dataManager;

    /**
     * 构造器
     * @param name
     */
    public MessageService(String name) {
        dataManager=new MessageManager<>(name);
    }

    /**
     * 添加一条消息
     * @param messageBean
     * @throws SysException
     */
    public void add(T messageBean) throws SysException {
        dataManager.add(messageBean);
    }

    /**
     * 实现 runnable 接口 需要手动创建一个线程 运行起来 他会 不断的获取最新的消息
     */
    @Override
    public void run() {
        while(true){
            T messageBean=dataManager.getMessage();
            if(messageBean==null){
                continue;
            }
            this.execute(messageBean);
        }
    }

    /**
     * 获取的消息会发生给这个方法  实现这个方法 处理业务逻辑
     * @param messageBean
     */
    public abstract void execute(T messageBean);


}
