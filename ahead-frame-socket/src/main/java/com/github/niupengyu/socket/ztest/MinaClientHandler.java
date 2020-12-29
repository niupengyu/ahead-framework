package com.github.niupengyu.socket.ztest;



import org.apache.mina.core.service.IoHandlerAdapter;

import org.apache.mina.core.session.IoSession;

/**

 * @author html580

 * @site http://www.html580.com

 */

public class MinaClientHandler extends IoHandlerAdapter {



        public MinaClientHandler() {

        }



        private Object msg;



        public MinaClientHandler(Object message) {

                this.msg = message;

        }



        

        @Override

        public void sessionOpened(IoSession session) throws Exception {

                System.out.println("客户端->sessionOpened");

System.out.println("客户端->" + ((Message) msg).getMsgBody());

                session.write(msg);

        }



        @Override

        public void sessionClosed(IoSession arg0) throws Exception {

                System.out.println("客户端->sessionClosed");

        }



        @Override

        public void messageReceived(IoSession session, Object message)

                        throws Exception {

                Message msg = (Message) message;

                System.out.println("处理完的结果为" + msg.getMsgBody());

        }



        @Override

        public void messageSent(IoSession session, Object message) throws Exception {

                // session.write(strC);

                // super.messageSent(session, message);

        }



        @Override

        public void exceptionCaught(IoSession session, Throwable cause)

                        throws Exception {

                cause.printStackTrace();

                session.close(true);

        }



}
