package com.github.niupengyu.socket.ztest;



import org.apache.mina.core.service.IoHandlerAdapter;

import org.apache.mina.core.session.IdleStatus;

import org.apache.mina.core.session.IoSession;

/**

 * @author html580

 * @site http://www.html580.com

 */

public class MinaServerHandler extends IoHandlerAdapter {

        /****

         * session打开时,调用

         */

        @Override

        public void sessionOpened(IoSession session) throws Exception {

                System.out.println("服务端sessionOpened..." + session.getRemoteAddress());



        }



        /***

         * 连接关才时调用

         */

        @Override

        public void sessionClosed(IoSession session) throws Exception {

                System.out.println("服务端sessionClosed...........");

        }



        /**

         * 如果出异常,就关闭session

         */

        @Override

        public void exceptionCaught(IoSession session, Throwable cause)

                        throws Exception {

                cause.printStackTrace();

                session.close(true);

        }



        /**

         * 收到客户端信息时调用

         */

        @Override

        public void messageReceived(IoSession session, Object message)

                        throws Exception {

                System.out.println("服务端收信息...");

                Message msg = (Message) message;

                System.out.println("服务端->收到客户机发来的消息: " + msg.getMsgBody());

                msg.setMsgBody("服务端回应客户机发来的消息" + msg.getMsgBody());

                session.write(msg);

                System.out.println("服务端回写信息...");

        }



        /***

         * 空闲时调用

         */

        @Override

        public void sessionIdle(IoSession session, IdleStatus status)

                        throws Exception {

                System.out.println("服务端 sessionIdle " + session.getIdleCount(status));

        }

}