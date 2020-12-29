package com.github.niupengyu.socket.factory;

import com.github.niupengyu.core.exception.SysException;
import com.github.niupengyu.core.message.MessageManager;
import com.github.niupengyu.core.message.MessageService;
import com.github.niupengyu.core.message.MultipleMessageService;
import com.github.niupengyu.core.message.SimpleMessageService;
import com.github.niupengyu.core.util.IdGeneratorUtil;
import com.github.niupengyu.core.util.StringUtil;
import com.github.niupengyu.socket.bean.Message;
import com.github.niupengyu.socket.client.config.ClientConfig;
import com.github.niupengyu.socket.client.config.ClientSocketConfig;
import com.github.niupengyu.socket.client.init.ClientInitService;
import com.github.niupengyu.socket.client.service.ClientHandler;
import com.github.niupengyu.socket.client.service.ClientHandlerService;
import com.github.niupengyu.socket.client.service.ClientKeepAliveService;
import com.github.niupengyu.socket.handler.KeepAliveService;


public class DefaultClient {



    //@PostConstruct
    public void create(ClientKeepAliveService keepAliveService, ClientHandlerService clientHandlerService, ClientConfig clientConfig,
                       SimpleMessageService simpleMessageService, String name) throws Exception {

        //clientConfig.setId(IdGeneratorUtil.uuid32());
        if(StringUtil.isNull(clientConfig.getId())){
            throw new SysException("socket 客户端 没有配置唯一标识 [news.client.id]");
        }
        MultipleMessageService<Message>
                multipleMessageService=new MultipleMessageService(clientConfig.getThreadCount(),simpleMessageService,name);

        //ClientHandlerService clientHandlerService=new ClientHandlerService();
        clientHandlerService.setClientConfig(clientConfig);
        clientHandlerService.setMessageManager(multipleMessageService);

        keepAliveService.setClientConfig(clientConfig);
        keepAliveService.setClientHandlerService(clientHandlerService);


        ClientSocketConfig clientSocketConfig =new ClientSocketConfig(keepAliveService,clientConfig);
        //clientSocketConfig.setKeepAliveService(keepAliveService);

        ClientHandler clientHandler=new ClientHandler();
        clientHandler.setClientService(clientHandlerService);

        ClientInitService clientInitService=new ClientInitService();
        clientInitService.setClientConfig(clientConfig);
        clientInitService.setClientHandler(clientHandler);
        clientInitService.setClientSocketConfig(clientSocketConfig);

        clientHandlerService.setClientInitService(clientInitService);
        clientHandlerService.startMessageManager();
        clientHandlerService.reconnection();
        //clientInitService.create();
    }

    public static void main(String[] args) {
        SimpleMessageService<String> stringSimpleMessageService=new SimpleMessageService<String>("") {

            @Override
            public void execute(String messageBean) {
                System.out.println(messageBean+" "+Thread.currentThread().getId());
            }
        };
        MultipleMessageService<String>
                multipleMessageService=new MultipleMessageService(3,stringSimpleMessageService,"哈哈哈");
        multipleMessageService.add("111111111111");
        multipleMessageService.add("111111111112");
        multipleMessageService.add("111111111113");
        multipleMessageService.add("111111111114");
        multipleMessageService.add("111111111115");
        multipleMessageService.add("111111111116");

    }
}
