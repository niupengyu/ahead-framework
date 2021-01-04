//package com.github.niupengyu.core.message;
//
//public abstract class SimpleMessageService<T>  extends AbstractMessageService<T> implements Runnable{
//
//
//    public SimpleMessageService(String name) {
//        super(name);
//    }
//
//    public SimpleMessageService(MessageManager<T> dataManager) {
//        super(dataManager);
//    }
//
//    @Override
//    public void run() {
//        //while(true){
//            T messageBean=this.getMessage();
//            if(messageBean==null){
//                return;
//            }
//            this.execute(messageBean);
//        //}
//    }
//}
