package com.github.niupengyu.socket.client.service;

import com.github.niupengyu.socket.bean.Message;
import com.github.niupengyu.socket.handler.ClientService;
import org.apache.mina.core.service.IoHandlerAdapter;
import org.apache.mina.core.session.IdleStatus;
import org.apache.mina.core.session.IoSession;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class ClientHandler extends IoHandlerAdapter{
    private static final Logger logger = LoggerFactory.getLogger(ClientHandler.class);

//    @Resource(name="clientHandlerService")
    private ClientService clientService;

    StringBuffer sb=new StringBuffer();

    @Override
    public void messageReceived(IoSession session, Object message) throws Exception {
        //String str = message.toString();
        //LOG.info("messageReceived:" + str);
        Message obj=(Message)message;
        //if(clientService.isHeartBeat(obj)){
        //    clientService.heartbeat(session,obj);
        //}else{
            clientService.received(obj,session);
        //}
    }

    @Override
    public void messageSent(IoSession session, Object message) throws Exception {
        // TODO Auto-generated method stub
        //System.out.println("send");
        //super.messageSent(session, message);
        //session.write(message);
        clientService.setSession(session);
    }

    @Override
    public void sessionClosed(IoSession session) throws Exception {
        // TODO Auto-generated method stub
        //super.sessionClosed(session);
        logger.info("连接关闭");
        //clientService.reconnection();
        clientService.connectionError();
    }

    @Override
    public void sessionCreated(IoSession session) throws Exception {
        // TODO Auto-generated method stub
        //super.sessionCreated(session);
        logger.info("sessionCreated");
        this.clientService.setSession(session);
    }

    @Override
    public void sessionIdle(IoSession session, IdleStatus status) throws Exception {
        // TODO Auto-generated method stub
        //super.sessionIdle(session, status);
    }

    @Override
    public void sessionOpened(IoSession session) throws Exception {
        // TODO Auto-generated method stub
    }

    @Override
    public void exceptionCaught(IoSession session, Throwable cause) throws Exception {
        logger.error(session.getId()+" ",cause);
        logger.error("错误信息 "+cause.getMessage());
        //clientService.reconnection();
    }

    public ClientService getClientService() {
        return clientService;
    }

    public void setClientService(ClientService clientService) {
        this.clientService = clientService;
    }

    public void connectionError() {
        this.clientService.connectionError();
    }
}