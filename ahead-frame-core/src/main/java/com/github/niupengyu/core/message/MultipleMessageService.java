package com.github.niupengyu.core.message;

import java.lang.reflect.Constructor;
import java.util.concurrent.SynchronousQueue;
import java.util.concurrent.ThreadPoolExecutor;
import java.util.concurrent.TimeUnit;

public class MultipleMessageService<T> {

    private int count;

    private int local;

    //private MessageManager<T> messageManager;

    private MessageListener messageListener;

    private SimpleMessageService<T> simpleMessageService;

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
        start(new MessageManager(name,messageListener));
    }

    public void start(MessageManager messageManager) {
        simpleMessageService.init(messageManager,this);
    }

    public void endOne(int i) {
        local+=i;
    }

    public void end() throws InterruptedException {
        simpleMessageService.setStop(true);
        pools.shutdown();
    }

    public void add(T o){
        this.simpleMessageService.add(o);
        if(pools.getActiveCount()<count){
            pools.execute(simpleMessageService);
        }
    }


    public static void main(String[] args) throws InterruptedException {
        MultipleMessageService<String> multipleMessageService=
                new MultipleMessageService(3, new SimpleMessageService<String>() {

                    @Override
                    public void execute(String messageBean) {
                        System.out.println(messageBean);
                        try {
                            Thread.sleep(1000);
                        } catch (InterruptedException e) {
                            e.printStackTrace();
                        }
                    }
                },"TEST");
        multipleMessageService.start(new MessageManager(""));
        for(int i=0;i<10;i++){
            multipleMessageService.add("1"+i);
        }
        multipleMessageService.end();
    }
}
