package com.github.niupengyu.core.message;

public class MessageListener {

    private int receiveCount=0;

    private int sendCount=0;

    public void addReceiveCount(int i) {
        receiveCount+=i;
    }

    public void addSendCount(int i) {
        sendCount+=i;
    }


    public int count(){
        return receiveCount=sendCount;
    }

    public int getReceiveCount() {
        return receiveCount;
    }

    public int getSendCount() {
        return sendCount;
    }
}
