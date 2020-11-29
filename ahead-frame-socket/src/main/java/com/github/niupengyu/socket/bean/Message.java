package com.github.niupengyu.socket.bean;

import com.github.niupengyu.core.util.IdGeneratorUtil;
import com.github.niupengyu.core.util.StringUtil;
import com.alibaba.fastjson.JSON;
import com.github.niupengyu.socket.util.SocketContent;

public class Message{

    public Message(){

    }

    /*public Message(String head, String type, Object message){
        this.head=head;
        this.type=type;
        this.message=message;
    }*/

    public Message(String head, String type,String id,long request,String requestNode,Object data){
        this.head=head;
        this.type=type;
        this.id=id;
        this.message=data;
        this.request=request;
        this.requestNode=requestNode;
    }

    public Message(String head, String type,String id,long request,String requestNode,long response,String responseNode,Object data){
        this.head=head;
        this.type=type;
        this.message=data;
        this.id=id;
        this.request=request;
        this.requestNode=requestNode;
        this.response=response;
        this.responseNode=responseNode;
    }

    private String id;

    private long request;

    private long response;

    private String responseNode;

    private String requestNode;

    private String head;

    private String type;

    //private String message;

    private Object message;

    public String getResponseNode() {
        return responseNode;
    }

    public void setResponseNode(String responseNode) {
        this.responseNode = responseNode;
    }

    public String getRequestNode() {
        return requestNode;
    }

    public void setRequestNode(String requestNode) {
        this.requestNode = requestNode;
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

    public String messageStr() {
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

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
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

    public static Message createRequest(String type,String requestNode,Object data){
        Message message=new Message(SocketContent.REQUEST, type, IdGeneratorUtil.uuid32(),System.currentTimeMillis(),requestNode,data);
        return message;
    }

    public static Message createResponse(Message message,String responseNode,Object data){
        Message message1=new Message(SocketContent.RESPONSE,message.getType(), message.getId(),
                message.getRequest(),message.getRequestNode(),
                System.currentTimeMillis(),responseNode,data);
        /*message1.setId(message.getId());
        message1.setRequest(message.getRequest());
        message1.setResponse(System.currentTimeMillis());
        message1.setRequestNode(message.getRequestNode());
        message1.setResponseNode(responseNode);*/
        return message1;
    }
}
