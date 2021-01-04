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

public class DataManager<T> implements Runnable{

    private final Lock lock = new ReentrantLock();

    private Logger logger;
    
    private String name;

    private boolean stop=false;

    private ExecutorService pools;

    private DataQueues<T> dataQueues;

    private DataRunner<T> dataRunner;

    public DataManager() {

    }
    
    public void init(String name,int count,DataRunner dataRunner){
        init(name,new DataQueues(),count,dataRunner);
    }

    public void init(String name, DataQueues dataQueues, int count,DataRunner dataRunner){
        init(name,dataRunner,dataQueues,count);
    }

    public void init(String name,DataRunner dataRunner){
        init(name,new DataQueues(),dataRunner);
    }

    public void init(String name, DataQueues dataQueues,DataRunner dataRunner){
        init(name,dataRunner,dataQueues,1);
    }

    public void init(String name, DataRunner dataRunner, DataQueues dataQueues,int count) {
        this.name=name;
        this.dataRunner=dataRunner;
        this.dataQueues=dataQueues;
        logger= LoggerFactory.getLogger(name);
        pools=Executors.newFixedThreadPool(count);
    }

    /**
     * 添加消息的方法
     * @param messageobj
     * @throws SysException
     */
    public void add(T messageobj)  {
        lock.lock();
        try {
            dataQueues.add(messageobj);
            pools.execute(this);
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
            dataQueues.addList(messageList);
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
            obj=dataQueues.getMessage();
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
            list=dataQueues.getMessageList(size);
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
            size=dataQueues.messageSize();
        }catch(Exception e){
            e.printStackTrace();
        }finally{
            lock.unlock();
        }
        return size;
    }

    public boolean isStop(){
        return stop;
    }

    public void setStop(boolean stop){
        this.stop = stop;
    }


    @Override
    public void run() {
        T messageBean=this.getMessage();
        if(messageBean==null){
            return;
        }
        dataRunner.execute(messageBean);
    }


    public void end() {
        setStop(true);
        pools.shutdown();
    }
}
