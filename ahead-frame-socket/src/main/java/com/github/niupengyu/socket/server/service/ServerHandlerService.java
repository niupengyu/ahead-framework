package com.github.niupengyu.socket.server.service;

import com.alibaba.fastjson.JSONObject;
import com.github.niupengyu.core.exception.SysException;
import com.github.niupengyu.core.message.MessageService;
import com.github.niupengyu.core.util.DateUtil;
import com.github.niupengyu.core.util.Hex;
import com.github.niupengyu.socket.bean.Message;
import com.github.niupengyu.socket.handler.ServerService;
import com.github.niupengyu.socket.server.config.MasterConfig;
import com.github.niupengyu.socket.util.SocketContent;
import org.apache.mina.core.session.IoSession;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import java.util.Collection;


public abstract class ServerHandlerService  implements ServerService,Runnable{

    private static final Logger logger= LoggerFactory.getLogger(ServerHandlerService.class);

    StringBuffer sb=new StringBuffer();

    private MessageService<Message> messageManager;

    private MasterConfig masterConfig;

    //private Map<Long,String> sessionMap=new HashMap<>();

    @Override
    public void messageReceived(Message str, IoSession session) throws SysException {
       String json=str.toJsonString();
       System.out.println("------------- "+json);
       messageManager.add(str);
    }

    @Override
    public void heartbeat(IoSession session, Message msg) throws SysException {
        logger.debug("SERVICE 接受到心跳信息"+msg);
        //this.receiveHeartBeat(msg);
        this.messageManager.add(msg);
        Message message=new Message();
        message.setType(SocketContent.HEARTBEAT);
        message.setHead(SocketContent.RESPONSE);
        message.setResponseNode(getMasterConfig().getName());
        long start= (long) msg.getMessage();
        message.setMessage(responseData(msg));
        message.setRequestNode(msg.getRequestNode());
        session.write(message);
    }

    protected abstract Object responseData(Message msg);


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


    public void setMessageManager(MessageService<Message> messageManager) {
        this.messageManager = messageManager;
    }

    public void setMasterConfig(MasterConfig masterConfig) {
        this.masterConfig = masterConfig;
    }

    public MessageService<Message> getMessageManager() {
        return messageManager;
    }

    public MasterConfig getMasterConfig() {
        return masterConfig;
    }
}