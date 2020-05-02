package com.github.niupengyu.socket.bean;

import com.github.niupengyu.core.util.StringUtil;
import com.alibaba.fastjson.JSON;

public class Message{

    public Message(){

    }

    public Message(String head, String type, Object message){
        this.head=head;
        this.type=type;
        this.message=message;
    }

    private long request=System.currentTimeMillis();

    private long response;

    private String node;

    private String head;

    private String type;

    //private String message;

    private Object message;

    public String getNode() {
        return node;
    }

    public void setNode(String node) {
        this.node = node;
    }

    public String getHead() {
        return head;
    }

    public void setHead(String head) {
        this.head = head;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public Object getMessage() {
        return message;
    }

    public String getMessageStr() {
        return StringUtil.valueOf(message);
    }

    public void setMessage(Object message) {
        this.message = message;
    }

    public String toJsonString(){
        return JSON.toJSONString(this);
    }

    public long getRequest() {
        return request;
    }

    public void setRequest(long request) {
        this.request = request;
    }

    public long getResponse() {
        return response;
    }

    public void setResponse(long response) {
        this.response = response;
    }

    @Override
    public String toString() {
        String data=toJsonString();
        String s=new StringBuilder()
                //.append("<|")
//                .append(type)
//                .append("|")
//                .append(head)
//                .append("|")
//                .append(node)
//                .append("|")
//                .append(message)
//                .append("|")
                .append(data)
                //.append("|>")
                .toString();
        return s;
    }
}
