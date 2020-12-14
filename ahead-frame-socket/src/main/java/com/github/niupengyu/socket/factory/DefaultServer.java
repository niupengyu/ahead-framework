package com.github.niupengyu.socket.factory;

import com.github.niupengyu.core.message.MessageManager;
import com.github.niupengyu.core.message.MessageService;
import com.github.niupengyu.core.message.MultipleMessageService;
import com.github.niupengyu.core.message.SimpleMessageService;
import com.github.niupengyu.socket.bean.Message;
import com.github.niupengyu.socket.server.config.MasterConfig;
import com.github.niupengyu.socket.server.config.ServerConfig;
import com.github.niupengyu.socket.server.init.MasterInitService;
import com.github.niupengyu.socket.server.service.MasterHandler;
import com.github.niupengyu.socket.server.service.ServerHandlerService;
import com.github.niupengyu.socket.server.service.ServerKeepAliveService;


public class DefaultServer {

    public void create(ServerHandlerService serverHandlerService,
                       MasterConfig masterConfig, ServerKeepAliveService keepAliveService,
                       SimpleMessageService simpleMessageService,String name,int count) throws Exception {

        MultipleMessageService<Message>
                multipleMessageService=new MultipleMessageService(count,simpleMessageService,name);

        serverHandlerService.setMasterConfig(masterConfig);
        serverHandlerService.setMessageMultipleMessageService(multipleMessageService);

        ServerConfig serverConfig=new ServerConfig();
        MasterHandler masterHandler=new MasterHandler();
        masterHandler.setServerService(serverHandlerService);
        masterHandler.setKeepAliveService(keepAliveService);
        masterHandler.setMasterConfig(masterConfig);
        keepAliveService.setMasterConfig(masterConfig);
        MasterInitService masterInitService=new MasterInitService();
        masterInitService.setIoFilterChainBuilder(serverConfig.filterChainBuilder());
        masterInitService.setMasterConfig(masterConfig);
        masterInitService.setMyHandler(masterHandler);
        serverHandlerService.startMessageManager();
        masterInitService.init();
    }

}