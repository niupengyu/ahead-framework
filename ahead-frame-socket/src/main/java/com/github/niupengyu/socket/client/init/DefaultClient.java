package com.github.niupengyu.socket.client.init;

import com.github.niupengyu.core.exception.SysException;
import com.github.niupengyu.core.util.IdGeneratorUtil;
import com.github.niupengyu.core.util.StringUtil;
import com.github.niupengyu.socket.client.config.ClientConfig;
import com.github.niupengyu.socket.client.config.ClientSocketConfig;
import com.github.niupengyu.socket.client.service.ClientHandler;
import com.github.niupengyu.socket.client.service.ClientHandlerService;
import com.github.niupengyu.socket.client.service.ClientKeepAliveService;
import com.github.niupengyu.socket.handler.KeepAliveService;


public class DefaultClient {



    //@PostConstruct
    public void create(ClientKeepAliveService keepAliveService, ClientHandlerService clientHandlerService, ClientConfig clientConfig) throws SysException {

        //clientConfig.setId(IdGeneratorUtil.uuid32());
        if(StringUtil.isNull(clientConfig.getId())){
            throw new SysException("socket 客户端 没有配置唯一标识 [news.client.id]");
        }

        //ClientHandlerService clientHandlerService=new ClientHandlerService();
        clientHandlerService.setClientConfig(clientConfig);

        keepAliveService.setClientConfig(clientConfig);
        keepAliveService.setClientHandlerService(clientHandlerService);


        ClientSocketConfig clientSocketConfig =new ClientSocketConfig(keepAliveService,clientConfig);
        //clientSocketConfig.setKeepAliveService(keepAliveService);

        ClientHandler clientHandler=new ClientHandler();
        clientHandler.setClientService(clientHandlerService);

        ClientInitService clientInitService=new ClientInitService();
        clientInitService.setClientConfig(clientConfig);
        clientInitService.setClientHandler(clientHandler);
        clientInitService.setIoFilterChainBuilder(clientSocketConfig.filterChainBuilder());

        clientHandlerService.setClientInitService(clientInitService);
        clientHandlerService.reconnection();
        //clientInitService.create();
    }
}
