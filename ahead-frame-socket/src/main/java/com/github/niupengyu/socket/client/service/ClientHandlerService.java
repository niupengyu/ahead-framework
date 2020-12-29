package com.github.niupengyu.socket.client.service;

import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.github.niupengyu.core.exception.SysException;
import com.github.niupengyu.core.message.MultipleMessageService;
import com.github.niupengyu.core.util.DateUtil;
import com.github.niupengyu.core.util.Hex;
import com.github.niupengyu.socket.bean.Message;
import com.github.niupengyu.socket.client.config.ClientConfig;
import com.github.niupengyu.socket.client.init.ClientInitService;
import com.github.niupengyu.socket.handler.ClientService;
import com.github.niupengyu.socket.server.service.ServerSendService;
import com.github.niupengyu.socket.util.SocketContent;
import org.apache.mina.core.session.IoSession;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.Map;
import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;

public abstract class ClientHandlerService implements ClientService {

    private static final Logger logger= LoggerFactory.getLogger(ClientHandlerService.class);

    private IoSession session;

    private ClientConfig clientConfig;

    private MultipleMessageService<Message> messageManager;

    private ClientInitService clientInitService;

    private final Lock lock = new ReentrantLock();

    private MultipleMessageService<Message> sendMessageService;

    private String status="OFFLINE";

    private boolean flag=false;

    StringBuffer sb=new StringBuffer();
    private Thread thread;

    public void startMessageManager() throws Exception {

        ClientSendService clientSendService=new ClientSendService(this);
        sendMessageService=new MultipleMessageService<>(clientConfig.getSendCount(),clientSendService,"client 发送消息处理");
        sendMessageService.start();
        messageManager.start();
    }

    @Override
    public void sendRequest(String type,Object message) throws Exception {
        Message msg=Message.createRequest(type/*,topic*/,message);
        sendRequest(msg);

    }

    @Override
    public void sendRequest(Message msg) throws SysException {
        createMessage(msg);
        send(msg);
    }

    protected void createMessage(Message msg) {
        msg.setRequestNode(clientConfig.getId());
    }

    @Override
    public void sendResponse(Message request,Object message) throws Exception {
        Message msg=Message.createResponse(request,message);
        //logger.info("response {}",msg.toJsonString());
        sendResponse(msg);
    }

    @Override
    public void sendResponse(Message msg) throws SysException {
        createMessage(msg);
        send(msg);
    }

    public void send(Message message) throws SysException {
        /*lock.lock();
        try {
            if(session==null||!session.isConnected()){
                reconnection();
            }
            //logger.info("response {}",msg.toJsonString());
            session.write(message);
        }catch(Exception e){
            logger.info("socket 异常",e);
            throw new SysException(e.getMessage());
        }finally{
            lock.unlock();
        }*/
        sendMessageService.add(message);
    }

    public void finallySend(Message message) {
        session.write(message);
    }

    @Override
    public synchronized void reconnection() throws Exception {
        status="LOST";
        logger.info("reconnection");
        flag=false;
        while(true){
            if(session!=null&&session.isConnected()){
                break;
            }else{
                create();
            }
            if(flag){
                break;
            }
        }
        //test();
    }


    @Override
    public void setSession(IoSession session) throws Exception {
        logger.info("setSession");
        this.session=session;
        Message message=this.getRequest("INIT");
        sendRequest(message);
        status="NORMAL";
    }

    @Override
    public boolean isHeartBeat(Message msg) {
//        return SocketContent.REQUEST.equals(msg.getHead());
        //System.out.println("isHeartBeat "+SocketContent.HEARTBEAT.equals(msg.getType()));
        logger.info("心跳 {} {}",Thread.currentThread().getId(),msg);
        return SocketContent.HEARTBEAT.equals(msg.getType());
    }

    @Override
    public void heartbeat(IoSession session, Message msg) {
        msg.setHead(SocketContent.RESPONSE);
        //logger.info("heartbeat "+msg);
        //session.write(msg);
    }

    @Override
    public void connectionError() throws Exception {
        reconnection();
        //System.out.println("不连了  自立门户");
        //status="OFFLINE";
        //flag=true;
    }

    @Override
    public void create() throws Exception {
        /*if(session!=null){
            session.closeNow();
        }*/
        session=null;
        status="RECONNECTION";
        session=clientInitService.create(3);
        if(session==null){
            logger.error("连不上服务器");
            connectionError();
        }else{
            //this.setSession(session);
            status="CONNECTED";
        }
    }

    @Override
    public void received(Object str, IoSession session) {
        try {
            //IoBuffer rb = (IoBuffer) str;
            //String hex=Hex.byte2HexStr(rb.array());
            //String json=str.toString();
            Message obj=(Message)str;
            messageManager.add(obj);
        } catch (Exception e){
            e.printStackTrace();
        }
    }



    public void setClientInitService(ClientInitService clientInitService) {
        this.clientInitService = clientInitService;
    }


    public void init() {
        String s="3C7C";
        String e="7C3E";
        try {
            while(true){
                int start=sb.indexOf(s);
                if(start>-1){
                    int end=sb.indexOf(e);
                    if(end>start){
                        String mes=sb.substring(start+4,end);
                        System.out.println(Hex.hexStr2Str(mes));
                        messageManager.add(JSONObject.parseObject(mes,Message.class));
                        sb.delete(0,end+4);
                        //System.out.println(Hex.hexStr2Str(sb.toString()));
                    }
                }
                Thread.sleep(3000);
            }
        } catch (Exception e1) {
            e1.printStackTrace();
        }
    }

    public ClientConfig getClientConfig() {
        return clientConfig;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }
    int i=0;
    public void setResponse(Message message) {
        long time=System.currentTimeMillis();
        //message.setNode(getClientConfig().getId());
        logger.debug("CLIENT 收到心跳回应 {} {}",Thread.currentThread().getId(),message);
        //System.out.println("setResponse "+(i++));
        //this.messageManager.add(message);
        receivedHeartBeatResponse(message);
    }

    protected abstract void receivedHeartBeatResponse(Message message);

    public Message getRequest(String type) throws Exception {
        Message message=Message.createRequest(type/*,SocketContent.HEARTBEAT*/,requestData());
        createMessage(message);
        return message;
    }

    protected abstract Object requestData();

    public void setClientConfig(ClientConfig clientConfig) {
        this.clientConfig = clientConfig;
    }

    public MultipleMessageService<Message> getMessageManager() {
        return messageManager;
    }

    public void setMessageManager(MultipleMessageService<Message> messageManager) {
        this.messageManager = messageManager;
    }

    public Thread getThread() {
        return thread;
    }

}
