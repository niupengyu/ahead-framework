package com.github.niupengyu.core.message;

import java.lang.reflect.Constructor;
import java.util.concurrent.SynchronousQueue;
import java.util.concurrent.ThreadPoolExecutor;
import java.util.concurrent.TimeUnit;

public class MultipleMessageService<T> {

    private int count;

    private int local;

    private MessageManager<T> messageManager;

    private MessageListener messageListener;

    private SimpleMessageService simpleMessageService;

    private Thread[] dataThreads;

    private String name;

    private ThreadPoolExecutor pools;

    public MultipleMessageService(int count,SimpleMessageService simpleMessageService,String name){
        this.count=count;
        this.simpleMessageService=simpleMessageService;
        this.messageListener=new MessageListener();
        this.name=name;
        pools =new ThreadPoolExecutor(1,count,0l, TimeUnit.SECONDS,
                new SynchronousQueue<Runnable>());
    }

    public void start() {
        messageManager=new MessageManager(name,messageListener);
        /*dataThreads=new Thread[count];
        for(int i=0;i<count;i++){
            //Constructor<SimpleMessageService> constructor=serviceClass.getConstructor(MessageManager.class,MultipleMessageService.class);
            simpleMessageService.init(messageManager,this);
            Thread thread=new Thread(simpleMessageService);
            dataThreads[i]=thread;
            pools.execute(thread);
        }*/
        start(messageManager);
    }

    public void start(MessageManager messageManager) {
        dataThreads=new Thread[count];
        for(int i=0;i<count;i++){
            //Constructor<SimpleMessageService> constructor=serviceClass.getConstructor(MessageManager.class,MultipleMessageService.class);
            simpleMessageService.init(messageManager,this);
            Thread thread=new Thread(simpleMessageService);
            dataThreads[i]=thread;
            pools.execute(thread);
        }
    }

    public void endOne(int i) {
        local+=i;
    }

    public void end() throws InterruptedException {
        messageManager.setStop(true);
        while(true){
            if(local>=count){
                break;
            }
            Thread.sleep(1000l);
        }
    }

    public void add(T o){
        this.messageManager.add(o);
    }

}
