package com.github.niupengyu.core.message;

import com.github.niupengyu.core.exception.SysException;

import java.util.List;

public abstract class MessageServiceBatch<T> implements Runnable{
    /**
     * 消息管理类
     */
    private MessageManager<T>
            dataManager;

    private int size;

    /**
     * 构造器
     * @param name
     */
    public MessageServiceBatch(String name,int size) {
        dataManager=new MessageManager<>(name);
        this.size=size;
    }
    /**
     * 构造器
     * @param dataManager
     */
    public MessageServiceBatch(MessageManager<T>
            dataManager,int size) {
        this.dataManager=dataManager;
        this.size=size;
    }

    /**
     * 添加多条消息
     * @param messageList
     * @throws SysException
     */
    public void addList(List<T> messageList) throws SysException {
        dataManager.addList(messageList);
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
            List<T> messageBean=dataManager.getMessageList(size);
            if(messageBean==null){
                boolean state=dataManager.isStop();
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

    /**
     * 获取的消息会发生给这个方法  实现这个方法 处理业务逻辑
     * @param messageBean
     */
    public abstract void execute(List<T> messageBean);

    public boolean isStop() {
        return dataManager.isStop();
    }

    public void setStop(boolean stop) {
        dataManager.setStop(stop);
    }

    public void setSize(int size) {
        this.size = size;
    }

    public int messageSize(){

        return dataManager.messageSize();
    }
    /**
     * 获取消息长度
     * @return
     */
    public int messageCount(){

        return dataManager.messageCount();
    }

}
