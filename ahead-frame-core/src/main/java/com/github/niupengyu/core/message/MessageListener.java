//package com.github.niupengyu.core.message;
//
//import java.util.ArrayList;
//import java.util.List;
//
//public class MessageListener {
//
//    private int receiveCount=0;
//
//    private int sendCount=0;
//
//    public void addReceiveCount(int i) {
//        receiveCount+=i;
//    }
//
//    public void addSendCount(int i) {
//        sendCount+=i;
//    }
//
//
//    public int count(){
//        return receiveCount-sendCount;
//    }
//
//    public boolean complete(){
//        return receiveCount==sendCount&&sendCount>0;
//    }
//
//    public int getReceiveCount() {
//        return receiveCount;
//    }
//
//    public int getSendCount() {
//        return sendCount;
//    }
//
//    @Override
//    public String toString() {
//        return "MessageListener{" +
//                "receiveCount=" + receiveCount +
//                ", sendCount=" + sendCount +
//                '}';
//    }
//
//}
