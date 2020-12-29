package com.github.niupengyu.socket.bean;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.github.niupengyu.core.util.IdGeneratorUtil;
import com.github.niupengyu.core.util.StringUtil;
import com.alibaba.fastjson.JSON;
import com.github.niupengyu.socket.util.SocketContent;

import java.util.List;

public class Message{

    public Message(){

    }

    public Message(String head, String type,String id,long request,
                   /*String requestNode,*/Object data/*,String topic*/) throws JsonProcessingException {
        this.head=head;
        this.type=type;
        this.id=id;
        this.message=data;
        //this.message=objectMapper.writeValueAsString(data);
        this.request=request;
        //this.requestNode=requestNode;
        //this.topic=topic;
    }

    public Message(String head, String type,String id,long request,String requestNode,
                   long response,/*String responseNode,*/Object data/*,String topic*/) throws JsonProcessingException {
        this.head=head;
        this.type=type;
        this.message=data;
        //this.message=objectMapper.writeValueAsString(data);
        this.id=id;
        this.request=request;
        this.requestNode=requestNode;
        this.response=response;
        //this.responseNode=responseNode;
        //this.topic=topic;
    }

    private String id;

    private long request;

    private long response;

    private String responseNode;

    private String send;

    private String receive;

    private String receiveType;

    private String requestNode;

    private String head;

    private String type;

    //private String message;

    private Object message;

    private long requestSession;

    private long length;

    public long getLength() {
        return length;
    }

    public void setLength(long length) {
        this.length = length;
    }

    public long getRequestSession() {
        return requestSession;
    }

    public void setRequestSession(long requestSession) {
        this.requestSession = requestSession;
    }

    private ObjectMapper objectMapper=new ObjectMapper();

    public String getReceiveType() {
        return receiveType;
    }

    public void setReceiveType(String receiveType) {
        this.receiveType = receiveType;
    }

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

    public void setMessage(Object message) {
        this.message = message;
    }

    public String messageStr() {
        return StringUtil.valueOf(message);
    }


    public String toJsonString() throws JsonProcessingException {
        //return JSON.toJSONString(this);
        return objectMapper.writeValueAsString(this);
    }

    public <T> T toObject(Class<T> c) throws JsonProcessingException {
        return objectMapper.convertValue(message,c);
    }

    public <T> T toList( TypeReference<T> tr) throws JsonProcessingException {
        return objectMapper.convertValue(message, tr);
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

    public String getSend() {
        return send;
    }

    public void setSend(String send) {
        this.send = send;
    }

    public String getReceive() {
        return receive;
    }

    public void setReceive(String receive) {
        this.receive = receive;
    }

    @Override
    public String toString() {
        String data= null;
        try {
            data = toJsonString();
        } catch (JsonProcessingException e) {
            e.printStackTrace();
        }
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

    /*public static Message createRequest(String type,*//*String topic,*//*String requestNode,Object data) throws JsonProcessingException {
        Message message=new Message(SocketContent.REQUEST, type,
                IdGeneratorUtil.uuid32(),System.currentTimeMillis(),requestNode,data*//*,topic*//*);
        return message;
    }

    public static Message createResponse(Message message,String responseNode,Object data) throws JsonProcessingException {
        Message message1=new Message(SocketContent.RESPONSE,message.getType(), message.getId(),
                message.getRequest(),message.getRequestNode(),
                System.currentTimeMillis(),responseNode,data*//*,message.getTopic()*//*);
        return message1;
    }*/

    public static Message createRequest(String type,Object data) throws JsonProcessingException {
        Message message=new Message(SocketContent.REQUEST, type,
                IdGeneratorUtil.uuid32(),System.currentTimeMillis(),data/*,topic*/);
        return message;
    }

    public static Message createResponse(Message message,Object data) throws JsonProcessingException {
        Message message1=new Message(SocketContent.RESPONSE,message.getType(), message.getId(),
                message.getRequest(),message.getRequestNode(),
                System.currentTimeMillis(),data/*,message.getTopic()*/);
        return message1;
    }

    public static Message createResponse(String type,Message message,Object data) throws JsonProcessingException {
        Message message1=new Message(SocketContent.RESPONSE,type, message.getId(),
                message.getRequest(),message.getRequestNode(),
                System.currentTimeMillis(),data/*,message.getTopic()*/);
        return message1;
    }
}
