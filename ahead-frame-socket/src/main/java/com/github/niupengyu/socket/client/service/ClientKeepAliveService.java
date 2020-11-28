package com.github.niupengyu.socket.client.service;

import com.github.niupengyu.core.util.DateUtil;
import com.github.niupengyu.socket.bean.Message;
import com.github.niupengyu.socket.client.config.ClientConfig;
import com.github.niupengyu.socket.handler.KeepAliveService;
import com.github.niupengyu.socket.util.SocketContent;
import org.apache.mina.core.session.IoSession;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public abstract class ClientKeepAliveService implements KeepAliveService {

    private ClientHandlerService clientHandlerService;

    private ClientConfig clientConfig;

    private static final Logger logger= LoggerFactory.getLogger(ClientKeepAliveService.class);

    @Override
    public void heartbeatTimeOut(IoSession session) {
        System.out.println("失去心跳");
        clientHandlerService.reconnection();
    }

    @Override
    public boolean isHeartbeatRequest( Message message) {
        return SocketContent.REQUEST.equals(message.getHead())
                && SocketContent.HEARTBEAT.equals(message.getType())
                && clientConfig.getId().equals(message.getRequestNode());
    }

    @Override
    public Object getResponse(Message request) {
        //request.setHead(SocketContent.RESPONSE);
        //request.setNode(clientConfig.getId());
        Message message=new Message();
        message.setType(SocketContent.HEARTBEAT);
        message.setHead(SocketContent.RESPONSE);
        message.setResponseNode(this.clientConfig.getId());
        long start= (long) request.getMessage();
        message.setMessage(DateUtil.getTimeDes(System.currentTimeMillis()-start));

        return request;
    }

    @Override
    public Object getRequest() {
        Message message=this.clientHandlerService.getRequest();
        logger.debug("发送心跳 {}",message);
        return message;
    }

    int i=0;

    @Override
    public boolean isHeartbeatResponse(Message message) {
        boolean flag= SocketContent.RESPONSE.equals(message.getHead())
                && SocketContent.HEARTBEAT.equals(message.getType())
                &&clientConfig.getId().equals(message.getRequestNode());
        System.out.println("isHeartbeatResponse "+(i++));
        if(flag){
            this.clientHandlerService.setResponse(message);
        }
        return flag;
    }

    public ClientHandlerService getClientHandlerService() {
        return clientHandlerService;
    }

    public void setClientHandlerService(ClientHandlerService clientHandlerService) {
        this.clientHandlerService = clientHandlerService;
    }

    public void setClientConfig(ClientConfig clientConfig) {
        this.clientConfig = clientConfig;
    }
}
