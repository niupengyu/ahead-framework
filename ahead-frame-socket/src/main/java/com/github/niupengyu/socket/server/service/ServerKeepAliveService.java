package com.github.niupengyu.socket.server.service;

import com.alibaba.fastjson.JSONObject;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.github.niupengyu.core.util.DateUtil;
import com.github.niupengyu.socket.bean.Message;
import com.github.niupengyu.socket.handler.KeepAliveService;
import com.github.niupengyu.socket.server.config.MasterConfig;
import com.github.niupengyu.socket.util.SocketContent;
import org.apache.mina.core.session.IoSession;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public abstract class ServerKeepAliveService implements KeepAliveService {

    private MasterConfig masterConfig;

    private static final Logger logger= LoggerFactory.getLogger(ServerKeepAliveService.class);

    @Override
    public void heartbeatTimeOut(IoSession session) {
        System.out.println(session.getId()+" timeout");
    }

    @Override
    public boolean isHeartbeatRequest( Message message) {

        return SocketContent.REQUEST.equals(message.getHead())
                && SocketContent.HEARTBEAT.equals(message.getType());

        //return SocketContent.REQUEST.equals(message.getHead());
    }

    @Override
    public Object getResponse( Message request) throws JsonProcessingException {
        long start=(long)(request.getMessage());
        /*Message message=new Message();
        message.setType(SocketContent.HEARTBEAT);
        message.setHead(SocketContent.RESPONSE);
        message.setResponseNode(masterConfig.getName());
        message.setMessage(DateUtil.getTimeDes(System.currentTimeMillis()-start));*/
        Message message=Message.createResponse(request,DateUtil.getTimeDes(System.currentTimeMillis()-start));
        message.setResponseNode(masterConfig.getName());
        return JSONObject.toJSONString(message);
    }

    @Override
    public Object getRequest() throws JsonProcessingException {
        logger.info("server 获取心跳请求内容");
        /*Message message=new Message();
        message.setHead(SocketContent.REQUEST);
        message.setType(SocketContent.HEARTBEAT);
        message.setRequestNode(masterConfig.getName());
        message.setMessage(System.currentTimeMillis());*/
        Message message=Message.createRequest(SocketContent.HEARTBEAT/*,SocketContent.HEARTBEAT*/,System.currentTimeMillis());
        message.setRequestNode(masterConfig.getName());
        return JSONObject.toJSONString(message);
    }

    @Override
    public boolean isHeartbeatResponse(Message message) {
        return SocketContent.RESPONSE.equals(message.getHead());
    }

    public MasterConfig getMasterConfig() {
        return masterConfig;
    }

    public void setMasterConfig(MasterConfig masterConfig) {
        this.masterConfig = masterConfig;
    }
}
