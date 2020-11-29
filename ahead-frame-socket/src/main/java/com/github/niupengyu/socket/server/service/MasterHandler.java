package com.github.niupengyu.socket.server.service;

import com.github.niupengyu.core.annotation.AutoConfig;
import com.github.niupengyu.core.message.MessageService;
import com.github.niupengyu.socket.bean.Message;
import com.github.niupengyu.socket.bean.SessionInfo;
import com.github.niupengyu.socket.handler.KeepAliveService;
import com.github.niupengyu.socket.handler.ServerService;
import com.github.niupengyu.socket.server.config.MasterConfig;
import com.github.niupengyu.socket.util.SessionManager;
import com.github.niupengyu.socket.util.SocketContent;
import org.apache.mina.core.service.IoHandlerAdapter;
import org.apache.mina.core.session.IdleStatus;
import org.apache.mina.core.session.IoSession;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.net.InetSocketAddress;

//@Service("masterHandler")
//@AutoConfig(name="news.server.enable")
public class MasterHandler extends IoHandlerAdapter {

//    @Resource(name="serverHandlerService")
    private ServerService serverService;

//    @Resource(name = "serverKeepAliveService")
    private KeepAliveService keepAliveService;

    private MasterConfig masterConfig;

    private final Logger LOG = LoggerFactory.getLogger(MasterHandler.class);
    @Override
    public void exceptionCaught(IoSession session, Throwable cause) throws Exception {
        cause.printStackTrace();
        session.closeOnFlush();
        //LOG.warn("session occured exception, so close it." + cause.getMessage());
    }
    @Override
    public void messageReceived(IoSession session, Object message) throws Exception {
        //String str = message.toString();
        //LOG.info("messageReceived:" + str);
        Message obj=(Message)message;

        if(keepAliveService.isHeartbeatRequest(obj)){
            SessionManager.putNode(obj.getRequestNode(),session.getId());
            serverService.heartbeat(session,obj);
        }else{
            serverService.messageReceived(obj,session);
        }
    }

    @Override
    public void messageSent(IoSession session, Object message) throws Exception {
        //LOG.warn("messageSent:" + message);
    }

    @Override
    public void sessionCreated(IoSession session) throws Exception {
        LOG.warn("remote client [" + session.getRemoteAddress().toString() + "] connected.");
        // 设置IoSession闲置时间，参数单位是秒
        session.getConfig().setIdleTime(IdleStatus.BOTH_IDLE, this.masterConfig.getIdeTime());
        String clientIP = ((InetSocketAddress)session.getRemoteAddress()).getAddress().getHostAddress();
        session.setAttribute(SocketContent.KEY_SESSION_CLIENT_IP, clientIP);
        LOG.info("sessionCreated, client IP: " + clientIP);
        SessionManager.sessionsConcurrentHashMap.put(session.getId(), new SessionInfo(session));
        LOG.info("-IoSession实例:" + session.toString());
        serverService.setSession(session);
    }

    @Override
    public void sessionClosed(IoSession session) throws Exception {
        LOG.warn("sessionClosed. "+session);
        serverService.closed(session);
        SessionManager.clear(session.getId());
        serverService.heartbeatTimeOut(session,"disconnect");
        session.closeOnFlush();
        // my
    }

    @Override
    public void sessionIdle(IoSession session, IdleStatus status) throws Exception {
        LOG.warn("session idle, so disconnecting......");
        session.closeOnFlush();
        LOG.warn("disconnected.");
    }
    @Override
    public void sessionOpened(IoSession session) throws Exception {
        LOG.warn("sessionOpened.");
        //
        //session.getConfig().setIdleTime(IdleStatus.BOTH_IDLE, IDLE);
    }

    public void exe(){
       //new Thread(runnable).start();
       //new Thread(messageManager).start();
    }

    public ServerService getServerService() {
        return serverService;
    }

    public void setServerService(ServerService serverService) {
        this.serverService = serverService;
    }

    public void setKeepAliveService(KeepAliveService keepAliveService) {
        this.keepAliveService = keepAliveService;
    }

    public MasterConfig getMasterConfig() {
        return masterConfig;
    }

    public void setMasterConfig(MasterConfig masterConfig) {
        this.masterConfig = masterConfig;
    }
}