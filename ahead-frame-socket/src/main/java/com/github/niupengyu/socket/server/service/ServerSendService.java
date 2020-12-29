package com.github.niupengyu.socket.server.service;

import com.github.niupengyu.core.exception.SysException;
import com.github.niupengyu.core.message.SimpleMessageService;
import com.github.niupengyu.socket.bean.Message;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class ServerSendService extends SimpleMessageService<Message> {

    private ServerHandlerService serverHandlerService;

    private Logger logger= LoggerFactory.getLogger(ServerSendService.class);

    public ServerSendService(ServerHandlerService serverHandlerService){
        this.serverHandlerService=serverHandlerService;
    }

    @Override
    public void execute(Message messageBean) {
        try {
            serverHandlerService.send(messageBean);
        } catch (SysException e) {
            logger.error("发送异常 "+e.getMessage(),e);
        }
    }
}
