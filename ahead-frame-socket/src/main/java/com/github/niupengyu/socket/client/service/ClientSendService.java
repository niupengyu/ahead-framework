package com.github.niupengyu.socket.client.service;

import com.github.niupengyu.core.message.SimpleMessageService;
import com.github.niupengyu.socket.bean.Message;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class ClientSendService extends SimpleMessageService<Message> {

    private ClientHandlerService clientHandlerService;

    private Logger logger= LoggerFactory.getLogger(ClientSendService.class);

    public ClientSendService(ClientHandlerService clientHandlerService,String name){
        super(name);
        this.clientHandlerService=clientHandlerService;
    }

    @Override
    public void execute(Message messageBean) {
        try {
            clientHandlerService.finallySend(messageBean);
        } catch (Exception e) {
            logger.error("发送异常 "+e.getMessage(),e);
        }
    }
}
