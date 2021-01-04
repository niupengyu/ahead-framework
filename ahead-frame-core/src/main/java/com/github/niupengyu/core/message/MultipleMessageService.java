package com.github.niupengyu.core.message;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.lang.reflect.Constructor;
import java.util.concurrent.*;

public class MultipleMessageService<T> {

    private int count;

    //private MessageManager<T> messageManager;

    private Logger logger= LoggerFactory.getLogger(MultipleMessageService.class);

    private MessageListener messageListener;

    private SimpleMessageService<T> simpleMessageService;

    private String name;

    private ExecutorService pools;

    public MultipleMessageService(int count,SimpleMessageService simpleMessageService,String name){
        this.count=count;
        this.simpleMessageService=simpleMessageService;
        this.messageListener=new MessageListener();
        this.name=name;
        pools =Executors.newSingleThreadExecutor();
    }

    public MultipleMessageService(){

    }


    public void init(int count,SimpleMessageService simpleMessageService,String name){
        this.count=count;
        this.simpleMessageService=simpleMessageService;
        this.messageListener=new MessageListener();
        this.name=name;
        pools=Executors.newFixedThreadPool(count);
    }



    public void end() throws InterruptedException {
        simpleMessageService.setStop(true);
        pools.shutdown();
    }

    public void add(T o){
        this.simpleMessageService.add(o);
        pools.execute(simpleMessageService);
    }


    public static void main(String[] args) throws InterruptedException {
        MultipleMessageService<String> multipleMessageService=
                new MultipleMessageService(3, new SimpleMessageService<String>(new MessageManager("")) {

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
        for(int i=0;i<10;i++){
            multipleMessageService.add("1"+i);
        }
        multipleMessageService.end();
    }
}
