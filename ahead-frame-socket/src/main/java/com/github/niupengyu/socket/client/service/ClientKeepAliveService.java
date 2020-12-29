package com.github.niupengyu.socket.client.service;

import com.fasterxml.jackson.core.JsonProcessingException;
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
    public void heartbeatTimeOut(IoSession session) throws Exception {
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
    public Object getResponse(Message request) throws JsonProcessingException {
        long start=(long)request.getMessage();
        /*Message message=new Message();
        message.setType(SocketContent.HEARTBEAT);
        message.setHead(SocketContent.RESPONSE);
        message.setResponseNode(this.clientConfig.getId());
        message.setMessage(DateUtil.getTimeDes(System.currentTimeMillis()-start));*/
        Message message=Message.createResponse(request,DateUtil.getTimeDes(System.currentTimeMillis()-start));
        message.setResponseNode(clientConfig.getId());
        return message;
    }

    @Override
    public Object getRequest() throws Exception {
        Message message=this.clientHandlerService.getRequest(SocketContent.HEARTBEAT);
        logger.debug("发送心跳 {}",message);
        return message;
    }

    int i=0;

    @Override
    public boolean isHeartbeatResponse(Message message) {
        boolean flag= SocketContent.RESPONSE.equals(message.getHead())
                && SocketContent.HEARTBEAT.equals(message.getType())
                &&clientConfig.getId().equals(message.getRequestNode());
        //logger.info("isHeartbeatResponse {} {}",(i++),flag);
        //logger.info("isHeartbeatResponse {} {}",Thread.currentThread().getId());
        //logger.info("isHeartbeatResponse {} ",message);
        if(flag){
            //this.clientHandlerService.setResponse(message);
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
