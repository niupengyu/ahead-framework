package com.github.niupengyu.core.message;

import com.github.niupengyu.core.exception.SysException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;

public class MessageManager<T> {

    private static final Logger logger= LoggerFactory.getLogger(MessageManager.class);
    /**
     * 线程锁
     */
    final Lock lock = new ReentrantLock();
    /**
     * 存放消息队列的类
     */
    private List<T> message=new ArrayList<>();
    /**
     * 消息名称
     */
    private String name;

    /**
     * 构造器
     * @param name
     */
    public MessageManager(String name){
        this.name=name;
    }

    /**
     * 添加消息的方法
     * @param messageobj
     * @throws SysException
     */
    public void add(T messageobj) throws SysException {
        lock.lock();
        try {
            message.add(messageobj);
        }catch(Exception e){
            e.printStackTrace();
        }finally{
            lock.unlock();
        }
    }

    /**
     * 读取一条最新的消息
     * @return
     */
    public  T getMessage() {
        lock.lock();
        T obj=null;
        try {
            if(message.size()>0){
                obj=message.get(0);
                message.remove(0);
                logger.info(name+" 剩余消息 ----->["+message.size()+"]");
            }
        }catch(Exception e){
            e.printStackTrace();
        }finally{
            lock.unlock();
        }
        return obj;
    }

    /**
     * 获取消息长度
     * @return
     */
    public int messageSize(){
        lock.lock();
        int size=0;
        try {
            size=message.size();
        }catch(Exception e){
            e.printStackTrace();
        }finally{
            lock.unlock();
        }
        return size;
    }

}
