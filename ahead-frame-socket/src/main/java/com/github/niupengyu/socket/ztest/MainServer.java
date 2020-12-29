package com.github.niupengyu.socket.ztest;



import java.io.IOException;

import java.net.InetSocketAddress;



import org.apache.mina.core.filterchain.DefaultIoFilterChainBuilder;

import org.apache.mina.filter.codec.ProtocolCodecFilter;

import org.apache.mina.filter.codec.serialization.ObjectSerializationCodecFactory;

import org.apache.mina.transport.socket.SocketAcceptor;

import org.apache.mina.transport.socket.nio.NioSocketAcceptor;



/**

 * @author html580

 * @site http://www.html580.com

 */

public class MainServer {

        // 设置端口

        public static int PORT = 8991;



        /**

         * @param args

         * @throws IOException

         * @author html580

         */

        public static void main(String[] args) throws IOException {

                System.out.println("服务创建中");

                // 创建一个非阻塞的的server端socket,用NIO

                SocketAcceptor acceptor = new NioSocketAcceptor();



                // 创建接收数据的过滤器

                DefaultIoFilterChainBuilder chain = acceptor.getFilterChain();



                /*

                 * 这里注意点: 1:TextLineCodecFactory设置这个过滤器一行一行(/r/n)的读取数据

                 * 2.ObjectSerializationCodecFactory一般接收的是对象等形象,以对象形式读取

                 */

                // chain.addLast("chain", new ProtocolCodecFilter(new

                // TextLineCodecFactory()));

                chain.addLast("chain", new ProtocolCodecFilter(

                                new ObjectSerializationCodecFactory()));

                // 设定服务器端消息处理器.:就是我们创建的TimeServerHandler对象

                acceptor.setHandler(new MinaServerHandler());



                acceptor.bind(new InetSocketAddress(PORT));



                System.out.println("MINS 服务器监听的服务端口为" + PORT);



        }



}