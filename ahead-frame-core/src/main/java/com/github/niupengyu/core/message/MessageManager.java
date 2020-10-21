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

    private boolean stop=false;

    private MessageListener messageListener;

    /**
     * 构造器
     * @param name
     */
    public MessageManager(String name,MessageListener messageListener){
        this.name=name;
        this.messageListener=messageListener;
    }

    /**
     * 构造器
     * @param name
     */
    public MessageManager(String name){
        this.name=name;
        this.messageListener=new MessageListener();
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
            messageListener.addReceiveCount(1);

        }catch(Exception e){
            e.printStackTrace();
        }finally{
            lock.unlock();
        }
    }

    /**
     * 添加消息的方法
     * @param messageList
     * @throws SysException
     */
    public void addList(List<T> messageList) throws SysException {
        lock.lock();
        try {
            message.addAll(messageList);
            messageListener.addReceiveCount(messageList.size());
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
    public T getMessage(){
        lock.lock();
        T obj=null;
        try {
            if(message.size()>0){
                obj=message.get(0);
                message.remove(0);
                messageListener.addSendCount(1);
                logger.debug(name+" 剩余消息 ----->[{}/{}]",message.size(),messageListener.count());
            }
        }catch(Exception e){
            e.printStackTrace();
        }finally{
            lock.unlock();
        }
        return obj;
    }
    /**
     * 读取一条最新的消息
     * @return
     */
    public List<T> getMessageList(int size){
        lock.lock();
        List<T> list=null;
        try {
            int messageSize=message.size();
            int length=0;
            if(messageSize>0){
                if(messageSize>size){
                    list=message.subList(0,size);
                    message=message.subList(size,messageSize);
                    length=size;
                }else{
                    list=message.subList(0,messageSize);
                    message=new ArrayList<>();
                    length=messageSize;
                }
                messageListener.addSendCount(length);
                logger.debug(name+" 剩余消息 ----->[{}/{}]",message.size(),messageListener.count());
            }
        }catch(Exception e){
            e.printStackTrace();
        }finally{
            lock.unlock();
        }
        return list;
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
    /**
     * 获取消息长度
     * @return
     */
    public int messageCount(){

        return messageListener.count();
    }

    public MessageListener getMessageListener() {
        return messageListener;
    }

    public boolean isStop(){
        return stop;
    }

    public void setStop(boolean stop){
        this.stop = stop;
    }

    public static void main(String[] args) {
        List<Integer> list=new ArrayList<>();
        list.add(1);
        list.add(2);
        list.add(3);
        list.add(4);
        list.add(5);

        System.out.println(list.subList(0,3));
        System.out.println(list.subList(3,list.size()));
    }
}
