package com.github.niupengyu.core.util;

import java.net.InetAddress;
import java.net.UnknownHostException;

public class HostUtil {

    public static String ipAddr() throws UnknownHostException {
        InetAddress addr = InetAddress.getLocalHost();
        String ip=addr.getHostAddress().toString(); //获取本机ip
        return ip;
    }

}
