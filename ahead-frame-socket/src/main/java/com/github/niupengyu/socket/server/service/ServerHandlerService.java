package com.github.niupengyu.socket.server.service;

import com.alibaba.fastjson.JSONObject;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.github.niupengyu.core.exception.SysException;
import com.github.niupengyu.core.message.MultipleMessageService;
import com.github.niupengyu.core.util.DateUtil;
import com.github.niupengyu.core.util.Hex;
import com.github.niupengyu.socket.bean.Message;
import com.github.niupengyu.socket.handler.ServerService;
import com.github.niupengyu.socket.server.config.MasterConfig;
import com.github.niupengyu.socket.util.SessionManager;
import com.github.niupengyu.socket.util.SocketContent;
import org.apache.mina.core.session.IoSession;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import java.util.Collection;
import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;


public abstract class ServerHandlerService  implements ServerService,Runnable{

    private static final Logger logger= LoggerFactory.getLogger(ServerHandlerService.class);

    StringBuffer sb=new StringBuffer();

    private MultipleMessageService<Message> messageMultipleMessageService;

    private MultipleMessageService<Message> sendMessageService;

    private MasterConfig masterConfig;

    //private Map<Long,String> sessionMap=new HashMap<>();

    private final Lock lock = new ReentrantLock();

    public void startMessageManager() throws Exception {
        ServerSendService serverSendService=new ServerSendService(this,"server 发送队列");
        sendMessageService=new MultipleMessageService<>(masterConfig.getSendCount(),serverSendService,"server 发送消息处理");
    }

    @Override
    public void messageReceived(Message str, IoSession session) throws Exception {
       //String json=str.toJsonString();
        if("INIT".equals(str.getType())){
            SessionManager.putNode(str.getRequestNode(),session.getId());
        }
        messageMultipleMessageService.add(str);
    }

    @Override
    public void heartbeat(IoSession session, Message msg) throws Exception {
        //logger.info("SERVICE 接受到心跳信息"+msg);
        //this.receiveHeartBeat(msg);
        this.messageMultipleMessageService.add(msg);
        Message message=Message.createResponse(msg,responseData(msg));
        createResponse(message);
        session.write(message);
    }

    protected abstract Object responseData(Message msg);

    @Override
    public void sendRequest(long sessionId, String type,Object msg) throws Exception {
        Message message=Message.createRequest(type/*,topic*/,msg);
        sendRequest(sessionId,message);
    }

    @Override
    public void sendRequest(long sessionId, Message message) throws Exception {
        createRequest(message);
        send(sessionId,message);
    }

    protected void createRequest(Message message) {
        message.setRequestNode(getMasterConfig().getName());
    }

    protected void createResponse(Message message) {

        message.setResponseNode(getMasterConfig().getName());
    }

    @Override
    public void sendResponse(long sessionId, Message request, Object msg) throws Exception {
        Message message=Message.createResponse(request,msg);
        sendResponse(sessionId,message);
    }

    @Override
    public void sendResponse(long sessionId,Message message) throws Exception {
        createResponse(message);
        send(sessionId,message);
    }

    public void send(long sessionId,Message message) throws SysException {
        /*lock.lock();
        try {
            IoSession session= SessionManager.getSession(sessionId);
            if(session==null){
                throw new SysException("找不到会话 "+sessionId);
            }
            message.setRequestSession(sessionId);
            session.write(message);
        }catch(Exception e){
            logger.info("socket 异常",e);
            throw new SysException(e.getMessage());
        }finally{
            lock.unlock();
        }*/
        message.setRequestSession(sessionId);
        sendMessageService.add(message);
    }

    @Override
    public void heartbeatTimeOut(IoSession session) {
        logger.info("heartbeatTimeOut1111111111111");
    }

    @Override
    public void heartbeatTimeOut(IoSession session, String disconnect) {
        Long id=session.getId();
        String clientIP = (String)session.getAttribute(SocketContent.KEY_SESSION_CLIENT_IP);
        logger.info(id+" 客户端("+clientIP+")失去连接 heartbeatTimeOut");
    }

    public void setSession(IoSession session) {
        logger.info("server setSession");
    }

    @Override
    public void closed(IoSession session) {

    }

    @Override
    public void run() {
        String s="3C7C";
        String e="7C3E";
        try {
            while(true){
                int start=sb.indexOf(s);
                if(start>-1){
                    int end=sb.indexOf(e);
                    if(end>start){
                        String mes=sb.substring(start+4,end);
                        //System.out.println(Hex.hexStr2Str(mes));
                        //System.out.println("================================");
                        messageMultipleMessageService.add(JSONObject.parseObject(mes,Message.class));
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

    public MultipleMessageService<Message> getMessageMultipleMessageService() {
        return messageMultipleMessageService;
    }

    public void setMessageMultipleMessageService(MultipleMessageService<Message> messageMultipleMessageService) {
        this.messageMultipleMessageService = messageMultipleMessageService;
    }

    public void setMasterConfig(MasterConfig masterConfig) {
        this.masterConfig = masterConfig;
    }


    public MasterConfig getMasterConfig() {
        return masterConfig;
    }

    public void send(Message message) throws SysException {
        long sessionId=message.getRequestSession();
        IoSession session= SessionManager.getSession(sessionId);
        if(session==null){
            throw new SysException("找不到会话 "+sessionId);
        }
        session.write(message);
    }
}