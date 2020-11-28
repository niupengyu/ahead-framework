package com.github.niupengyu.socket.client.init;

import com.github.niupengyu.socket.server.config.MasterConfig;
import com.github.niupengyu.socket.server.config.ServerConfig;
import com.github.niupengyu.socket.server.init.MasterInitService;
import com.github.niupengyu.socket.server.service.MasterHandler;
import com.github.niupengyu.socket.server.service.ServerHandlerService;
import com.github.niupengyu.socket.server.service.ServerKeepAliveService;


public class DefaultServer {

    public void create(ServerHandlerService serverHandlerService, MasterConfig masterConfig, ServerKeepAliveService keepAliveService) {

        serverHandlerService.setMasterConfig(masterConfig);
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
        masterInitService.init();
    }

}
