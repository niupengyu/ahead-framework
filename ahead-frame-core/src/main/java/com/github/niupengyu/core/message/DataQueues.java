package com.github.niupengyu.core.message;

import com.github.niupengyu.core.exception.SysException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;

public class DataQueues<T>{



    private List<T> message=new ArrayList<>();



    public DataQueues() {
        
    }
    
    /**
     * 添加消息的方法
     * @param messageobj
     * @throws SysException
     */
    public void add(T messageobj)  {
        message.add(messageobj);
    }

    /**
     * 添加消息的方法
     * @param messageList
     * @throws SysException
     */
    public void addList(List<T> messageList) throws SysException {
        message.addAll(messageList);
    }

    /**
     * 读取一条最新的消息
     * @return
     */
    public T getMessage(){
        T obj=null;
        if(message.size()>0){
            obj=message.get(0);
            message.remove(0);
        }
        return obj;
    }
    /**
     * 读取一条最新的消息
     * @return
     */
    public List<T> getMessageList(int size){
        List<T> list=null;
        int messageSize=message.size();
        int length;
        if(messageSize>0){
            if(messageSize>size){
                length=size;
                list=new ArrayList<>(message.subList(0,length));
                message=new ArrayList<>(message.subList(length,messageSize));
            }else{
                length=messageSize;
                list=new ArrayList<>(message.subList(0,length));
                message=new ArrayList<>();
            }
        }
        return list;
    }

    /**
     * 获取消息长度
     * @return
     */
    public int messageSize(){
        int size=0;
        size=message.size();
        return size;
    }

}
