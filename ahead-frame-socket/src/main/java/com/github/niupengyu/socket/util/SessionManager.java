package com.github.niupengyu.socket.util;

import com.github.niupengyu.socket.bean.Message;
import com.github.niupengyu.socket.bean.SessionInfo;
import org.apache.mina.core.session.IoSession;

import java.util.concurrent.ConcurrentHashMap;

public class SessionManager {

    public static ConcurrentHashMap<Long, SessionInfo> sessionsConcurrentHashMap =
            new ConcurrentHashMap<Long, SessionInfo>();

    public static ConcurrentHashMap<Long, StringBuffer> messagesConcurrentHashMap =
            new ConcurrentHashMap<Long, StringBuffer>();

    public static ConcurrentHashMap<String, Long> sessionsNodesHashMap =
            new ConcurrentHashMap<String, Long>();
    public static ConcurrentHashMap<Long, String> nodesSessionsHashMap =
            new ConcurrentHashMap();


    public static void sendMessage(Message message, Long id){
        IoSession session=getSession(id);
        if(session!=null){
            session.write(message.toJsonString());
        }
    }

    public static IoSession getSession(Long id){
        SessionInfo sessionInfo=getSessionInfo(id);
        if(sessionInfo!=null){
            IoSession session=sessionInfo.getSession();
            return session;
        }
        return null;
    }

    public static SessionInfo getSessionInfo(Long id){
        if(sessionsConcurrentHashMap.containsKey(id)){
            SessionInfo sessionInfo=sessionsConcurrentHashMap.get(Long.valueOf(id));
            return sessionInfo;
        }
        return null;
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

    public static void clear(long id) {
        SessionManager.sessionsConcurrentHashMap.remove(id);
        String node=nodesSessionsHashMap.get(id);
        SessionManager.sessionsNodesHashMap.remove(node);
        SessionManager.nodesSessionsHashMap.remove(id);
    }

    public static void putNode(String node,long id) {
        if(!SessionManager.sessionsNodesHashMap.containsKey(node)){
            SessionManager.sessionsNodesHashMap.put(node,id);
        }
        if(!SessionManager.nodesSessionsHashMap.containsKey(id)){
            SessionManager.nodesSessionsHashMap.put(id,node);
        }
    }

    public static String getNode(long id){
        return nodesSessionsHashMap.get(id);
    }
}
