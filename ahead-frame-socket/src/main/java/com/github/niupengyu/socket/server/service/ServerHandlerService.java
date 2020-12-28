package com.github.niupengyu.socket.server.service;

import com.alibaba.fastjson.JSONObject;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.github.niupengyu.core.exception.SysException;
import com.github.niupengyu.core.message.MessageService;
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

    private MasterConfig masterConfig;

    //private Map<Long,String> sessionMap=new HashMap<>();

    private final Lock lock = new ReentrantLock();

    public void startMessageManager() throws Exception {
        messageMultipleMessageService.start();
    }

    @Override
    public void messageReceived(Message str, IoSession session) throws Exception {
       //String json=str.toJsonString();
        messageMultipleMessageService.add(str);
    }

    @Override
    public void heartbeat(IoSession session, Message msg) throws Exception {
        logger.debug("SERVICE 接受到心跳信息"+msg);
        //this.receiveHeartBeat(msg);
        this.messageMultipleMessageService.add(msg);
        /*Message message=new Message();
        message.setType(SocketContent.HEARTBEAT);
        message.setHead(SocketContent.RESPONSE);
        message.setResponseNode(getMasterConfig().getName());
        message.setMessage(responseData(msg));
        message.setRequestNode(msg.getRequestNode());*/
        Message message=Message.createResponse(msg,getMasterConfig().getName(),responseData(msg));
        session.write(message);
    }

    protected abstract Object responseData(Message msg);

    @Override
    public void sendRequest(long sessionId, String type,Object msg) throws SysException {
        lock.lock();
        try {
            IoSession session= SessionManager.getSession(sessionId);
            if(session==null){
                throw new SysException("找不到会话 "+sessionId);
            }
            Message message=Message.createRequest(type/*,topic*/,getMasterConfig().getName(),msg);
            session.write(message);
        }catch(Exception e){
            e.printStackTrace();
        }finally{
            lock.unlock();
        }
    }

    @Override
    public void sendRequest(long sessionId, Message message) throws Exception {
        lock.lock();
        try {
            IoSession session= SessionManager.getSession(sessionId);
            if(session==null){
                throw new SysException("找不到会话 "+sessionId);
            }
            message.setRequestNode(getMasterConfig().getName());
            session.write(message);
        }catch(Exception e){
            e.printStackTrace();
        }finally{
            lock.unlock();
        }
    }


    @Override
    public void sendResponse(long sessionId, Message request, Object msg) throws SysException {
        lock.lock();
        try {
            IoSession session= SessionManager.getSession(sessionId);
            if(session==null){
                throw new SysException("找不到会话 "+sessionId);
            }
            Message message=Message.createResponse(request,getMasterConfig().getName(),msg);
            session.write(message);
        }catch(Exception e){
            e.printStackTrace();
        }finally{
            lock.unlock();
        }
    }

    @Override
    public void sendResponse(long sessionId,Message message) throws SysException {
        lock.lock();
        try {
            IoSession session= SessionManager.getSession(sessionId);
            if(session==null){
                throw new SysException("找不到会话 "+sessionId);
            }
            //Message message=Message.createResponse(request,getMasterConfig().getName(),msg);
            message.setResponseNode(getMasterConfig().getName());
            session.write(message);
        }catch(Exception e){
            e.printStackTrace();
        }finally{
            lock.unlock();
        }
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
        logger.info("setSession");
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
}