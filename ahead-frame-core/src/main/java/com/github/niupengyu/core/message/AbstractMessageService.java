package com.github.niupengyu.core.message;

import com.github.niupengyu.core.exception.SysException;

import java.util.List;

public abstract class AbstractMessageService<T>{
    /**
     * 消息管理类
     */
    private MessageManager<T>
            dataManager;

    /**
     * 构造器
     * @param name
     */
    public AbstractMessageService(String name) {
        dataManager=new MessageManager<>(name);
    }
    /**
     * 构造器
     * @param dataManager
     */
    public AbstractMessageService(MessageManager<T>
            dataManager) {
        this.dataManager=dataManager;
    }
    /*public AbstractMessageService() {

    }*/

    /**
     * 初始化
     * @param dataManager
     */
    /*public void init(MessageManager dataManager) {
        this.dataManager=dataManager;
    }*/



    /**
     * 添加一条消息
     * @param messageBean
     * @throws SysException
     */
    public void add(T messageBean) {
        dataManager.add(messageBean);
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
     * 获取的消息会发生给这个方法  实现这个方法 处理业务逻辑
     * @param messageBean
     */
    public abstract void execute(T messageBean);

    public boolean isStop() {
        return dataManager.isStop();
    }

    public void setStop(boolean stop) {
        dataManager.setStop(stop);
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

    public MessageListener getMessageListener() {
        return dataManager.getMessageListener();
    }

    protected T getMessage() {
        return dataManager.getMessage();
    }
}
