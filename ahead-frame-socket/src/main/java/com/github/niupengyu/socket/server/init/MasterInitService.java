package com.github.niupengyu.socket.server.init;

import com.github.niupengyu.core.annotation.AutoConfig;
import com.github.niupengyu.core.init.InitService;
import com.github.niupengyu.core.message.MessageService;
import com.github.niupengyu.socket.server.config.MasterConfig;
import com.github.niupengyu.socket.server.service.MasterHandler;
import org.apache.mina.core.filterchain.DefaultIoFilterChainBuilder;
import org.apache.mina.transport.socket.nio.NioSocketAcceptor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.boot.ApplicationArguments;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.net.InetSocketAddress;
import java.net.SocketAddress;

//@Service("masterInitService")
//@AutoConfig(name="news.server.enable")
public class MasterInitService{

    private static final Logger logger= LoggerFactory.getLogger(MasterInitService.class);

//    @Resource(name="masterConfig")
    private MasterConfig masterConfig;

//    @Resource(name="masterHandler")
    private MasterHandler myHandler;


//    @Resource(name="filterChainBuilder")
    private DefaultIoFilterChainBuilder ioFilterChainBuilder;

    public void init() {
        NioSocketAcceptor server=nioSocketAcceptor(masterConfig.getPort());
        try {
            server.bind();
            logger.info("服务器启动成功 "+masterConfig.getPort());
            myHandler.exe();
        }catch (Exception e){
            logger.info("服务器启动失败");
        }
    }

    private NioSocketAcceptor nioSocketAcceptor(int port){
        NioSocketAcceptor acceptor=new NioSocketAcceptor();
        //端口号
        SocketAddress address= new InetSocketAddress(port);
        acceptor.setDefaultLocalAddress(address);
        //绑定自己实现的handler
        acceptor.setHandler(myHandler);
        //声明过滤器的集合
        acceptor.setFilterChainBuilder(ioFilterChainBuilder);
        acceptor.setReuseAddress(true);
        return acceptor;
    }

    public void setMasterConfig(MasterConfig masterConfig) {
        this.masterConfig = masterConfig;
    }

    public void setMyHandler(MasterHandler myHandler) {
        this.myHandler = myHandler;
    }

    public void setIoFilterChainBuilder(DefaultIoFilterChainBuilder ioFilterChainBuilder) {
        this.ioFilterChainBuilder = ioFilterChainBuilder;
    }
}
