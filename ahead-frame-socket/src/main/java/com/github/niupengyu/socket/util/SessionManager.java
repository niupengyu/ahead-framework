package com.github.niupengyu.socket.util;

import com.github.niupengyu.socket.bean.Message;
import org.apache.mina.core.session.IoSession;

import java.util.concurrent.ConcurrentHashMap;

public class SessionManager {

    public static ConcurrentHashMap<Long, IoSession> sessionsConcurrentHashMap =
            new ConcurrentHashMap<Long, IoSession>();

    public static ConcurrentHashMap<Long, StringBuffer> messagesConcurrentHashMap =
            new ConcurrentHashMap<Long, StringBuffer>();


    public static void sendMessage(Message message, Long id){
        IoSession session=sessionsConcurrentHashMap.get(Long.valueOf(id));
        if(session!=null){
            session.write(message.toJsonString());
        }
    }

    public static IoSession getSession(Long id){
        return sessionsConcurrentHashMap.get(id);
    }

    public static void putMessage(long id,String msg){
        if(messagesConcurrentHashMap.contains(id)){
            messagesConcurrentHashMap.get(id).append(msg);
        }else{
            messagesConcurrentHashMap.put(id,new StringBuffer().append(msg));
        }
    }

    public static StringBuffer getMessage(long id){
        return messagesConcurrentHashMap.get(id);
    }
}
