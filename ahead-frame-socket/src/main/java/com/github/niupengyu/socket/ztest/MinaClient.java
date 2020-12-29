package com.github.niupengyu.socket.ztest;



import java.io.IOException;

import java.net.InetSocketAddress;



import org.apache.mina.core.filterchain.DefaultIoFilterChainBuilder;

import org.apache.mina.core.future.ConnectFuture;

import org.apache.mina.filter.codec.ProtocolCodecFilter;

import org.apache.mina.filter.codec.serialization.ObjectSerializationCodecFactory;

import org.apache.mina.transport.socket.nio.NioSocketConnector;

/**

 * @author html580

 * @site http://www.html580.com

 */

public class MinaClient {



        private static int PORT = 8991;



        /**

         * @param args

         * @throws IOException

         */

        public static void main(String[] args) throws IOException {

                System.out.println("开始接服务器");

                // 创建TCP/IP的连接

                NioSocketConnector connector = new NioSocketConnector();

                // 创建接收数据的过滤器

                DefaultIoFilterChainBuilder chain = connector.getFilterChain();

                /*

                 * 这里注意点: 

                 * 1:TextLineCodecFactory设置这个过滤器一行一行(/r/n)的发送/读取数据

                 * 2.ObjectSerializationCodecFactory一般发送/接收的是对象等形象,以对象形式读取

                 */

                //chain.addLast("myChain",new ProtocolCodecFilter(new TextLineCodecFactory()));

                chain.addLast("myChain", new ProtocolCodecFilter(new ObjectSerializationCodecFactory()));

                Message msg = new Message("TObject");

                // 设置处理的类

                connector.setHandler(new MinaClientHandler(msg));

                // 设置时间

                connector.setConnectTimeoutMillis(300000);

                // 开始连接服务器

                ConnectFuture cf = connector.connect(new InetSocketAddress("localhost",PORT));

                // 等待连接结束

                cf.awaitUninterruptibly();

                cf.getSession().getCloseFuture().awaitUninterruptibly();

                connector.dispose();

        }



}