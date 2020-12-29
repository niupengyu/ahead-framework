package com.github.niupengyu.socket.ztest;



import java.io.Serializable;

/**

 * @author html580

 * @site http://www.html580.com

 */

public class Message  implements Serializable{

        private static final long serialVersionUID = 1L;

        private String msgBody;



        public Message(String msgBody) {

                this.msgBody = msgBody;

        }



        public String getMsgBody() {

                return msgBody;

        }



        public void setMsgBody(String msgBody) {

                this.msgBody = msgBody;

        }

}
