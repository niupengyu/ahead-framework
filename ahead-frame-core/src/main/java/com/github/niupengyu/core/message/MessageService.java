//package com.github.niupengyu.core.message;
//
//import com.github.niupengyu.core.exception.SysException;
//
//import java.util.List;
//import java.util.concurrent.ExecutorService;
//import java.util.concurrent.Executors;
//
//public abstract class MessageService<T> extends AbstractMessageService<T> implements Runnable{
//
//    /*public MessageService(){
//
//    }*/
//
//    public MessageService(String name) {
//        super(name);
//        pools =Executors.newSingleThreadExecutor();
//    }
//
//    public MessageService(MessageManager<T> dataManager) {
//        super(dataManager);
//        pools =Executors.newSingleThreadExecutor();
//    }
//
//    public MessageService(String name,int count) {
//        super(name);
//        pools=Executors.newFixedThreadPool(count);
//    }
//
//    public MessageService(MessageManager<T> dataManager,int count) {
//        super(dataManager);
//        pools=Executors.newFixedThreadPool(count);
//    }
//
//    private ExecutorService pools;
//
//    @Override
//    public void add(T messageBean) {
//        super.add(messageBean);
//        pools.execute(this);
//    }
//
//
//
//    /**
//     * 实现 runnable 接口 需要手动创建一个线程 运行起来 他会 不断的获取最新的消息
//     */
//    @Override
//    public void run() {
//        while(true){
//            T messageBean=this.getMessage();
//            if(messageBean==null){
//                boolean state=this.isStop();
//                if(state){
//                    break;
//                }else{
//                    continue;
//                }
//            }
//            this.execute(messageBean);
//
//        }
//        this.endExecute();
//    }
//
//    protected abstract void endExecute();
//
//
//
//}
