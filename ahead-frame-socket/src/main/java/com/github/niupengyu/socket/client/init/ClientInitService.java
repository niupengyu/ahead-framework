package com.github.niupengyu.socket.client.init;

import com.github.niupengyu.core.annotation.AutoConfig;
import com.github.niupengyu.socket.client.config.ClientConfig;
import com.github.niupengyu.socket.client.config.ClientSocketConfig;
import com.github.niupengyu.socket.client.service.ClientHandler;
import com.github.niupengyu.socket.code.ByteArrayCodecFactory;
import com.github.niupengyu.socket.handler.ClientService;
import org.apache.mina.core.filterchain.DefaultIoFilterChainBuilder;
import org.apache.mina.core.future.ConnectFuture;
import org.apache.mina.core.session.IoSession;
import org.apache.mina.filter.codec.ProtocolCodecFilter;
import org.apache.mina.filter.codec.serialization.ObjectSerializationCodecFactory;
import org.apache.mina.transport.socket.SocketSessionConfig;
import org.apache.mina.transport.socket.nio.NioSocketConnector;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.net.InetSocketAddress;

//@Service("slaveInitSerivce")
//@AutoConfig(name="news.slave.enable")
public class ClientInitService {

    private ClientHandler clientHandler;

    private ClientSocketConfig clientSocketConfig;

    private ClientConfig clientConfig;

    private static final Logger logger= LoggerFactory.getLogger(ClientInitService.class);

    public IoSession create() {
        return create(clientConfig.getCount());
    }

    private NioSocketConnector connector;

    IoSession session;

    public IoSession create(int count) {

        long mill= clientConfig.getMill();
        for(int i=0;i<count;i++){
            String server= clientConfig.getServer();
            int port= clientConfig.getPort();
            connector=nioSocketConnector();
            ConnectFuture cf = connector.connect(new InetSocketAddress(server, port));//建立连接
            cf.awaitUninterruptibly();//等待连接创建完成
            logger.info("第 "+(i+1)+" 次连接");
            if(cf.isConnected()){
                session=cf.getSession();
                break;
            }else{
                try {
                    Thread.sleep(mill);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }
        }
        /*if(session==null||!session.isConnected()){
            this.clientHandler.connectionError();
        }*/
        return session;
    }

    private NioSocketConnector nioSocketConnector(){
        NioSocketConnector connector = new NioSocketConnector();
        connector.setFilterChainBuilder(clientSocketConfig.filterChainBuilder());

        /*DefaultIoFilterChainBuilder chain = connector.getFilterChain();

        chain.addLast("myChain", new ProtocolCodecFilter(new ByteArrayCodecFactory()));*/

        connector.setHandler(clientHandler);//设置事件处理器
        SocketSessionConfig cfg=connector.getSessionConfig();
        //cfg.setUseReadOperation(true);
        return connector;
    }

    public void setClientHandler(ClientHandler clientHandler) {
        this.clientHandler = clientHandler;
    }


    public void setClientConfig(ClientConfig clientConfig) {
        this.clientConfig = clientConfig;
    }

    public void close() {
        if(session != null && session.isConnected()){
            System.out.println(session.isConnected());
            session.getService().dispose();//add by czp 20181207
            session.closeOnFlush();
            connector.dispose();
            session=null;
            connector=null;
        }
    }

    public void setClientSocketConfig(ClientSocketConfig clientSocketConfig) {
        this.clientSocketConfig =clientSocketConfig;
    }
}
