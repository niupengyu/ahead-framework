package com.github.niupengyu.socket.code;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.github.niupengyu.socket.bean.Message;
import com.github.niupengyu.socket.handler.KeepAliveService;
import org.apache.mina.core.session.IoSession;
import org.apache.mina.filter.keepalive.KeepAliveMessageFactory;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.annotation.Resource;

public class MyKeepAliveMessageFactory implements KeepAliveMessageFactory {

    private final Logger logger = LoggerFactory.getLogger(MyKeepAliveMessageFactory.class);

//    @Resource(name = "keepAliveService")
    private KeepAliveService keepAliveService;

    public Object getRequest(IoSession session) {
        try {
            return keepAliveService.getRequest();
        } catch (JsonProcessingException e) {
            logger.error("获取心跳请求出错",e);
        }
       return null;
    }

    public Object getResponse(IoSession session, Object message) {
        /** 返回预设语句 */
        Message rb = (Message) message;
        //String hex= Hex.byte2HexStr(rb.array());
        //logger.info("getResponse "+message);
        try {
            return keepAliveService.getResponse(rb);
        } catch (JsonProcessingException e) {
            logger.error("获取心跳响应出错",e);
        }
        return null;
    }

    public boolean isRequest(IoSession session, Object message) {
        Message rb = (Message) message;
        return keepAliveService.isHeartbeatRequest(rb);
    }

    int i=0;
    
    public boolean isResponse(IoSession session, Object message) {
        Message rb = (Message) message;
        //System.out.println("isResponse "+(i++));
        return keepAliveService.isHeartbeatResponse(rb);
    }

    public KeepAliveService getKeepAliveService() {
        return keepAliveService;
    }

    public void setKeepAliveService(KeepAliveService keepAliveService) {
        this.keepAliveService = keepAliveService;
    }
}
