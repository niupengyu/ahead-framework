package com.github.niupengyu.core.message;

import com.github.niupengyu.core.exception.SysException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.lang.reflect.Constructor;
import java.util.List;
import java.util.concurrent.*;

public class MultipleMessageService<T> {


    //private MessageManager<T> messageManager;

    private Logger logger= LoggerFactory.getLogger(MultipleMessageService.class);


    private DataManager dataManager=new DataManager<>();


    public MultipleMessageService(int count,DataRunner dataRunner,String name){
        dataManager.init(name,count,dataRunner);
    }

    public MultipleMessageService(){

    }


    public void init(int count,DataRunner dataRunner,String name){
        dataManager.init(name,count,dataRunner);
    }



    public void end() throws Exception {
        dataManager.end();
    }

    public void add(T o){
        dataManager.add(o);
    }

    public void addList(List<T> o) throws Exception {
        dataManager.addList(o);
    }


    public static void main(String[] args) throws Exception {
        MultipleMessageService<String> multipleMessageService=
                new MultipleMessageService(3, new DataRunner() {
                    @Override
                    public void execute(Object messageBean) {
                        System.out.println(messageBean);
                        try {
                            Thread.sleep(1000);
                        } catch (InterruptedException e) {
                            e.printStackTrace();
                        }
                    }
                }, "TEST");
        for(int i=0;i<10;i++){
            multipleMessageService.add("1"+i);
        }
        multipleMessageService.end();
    }
}
