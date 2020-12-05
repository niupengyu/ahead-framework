package com.github.niupengyu.core.util.data;

public class SnUtil {

    private int length;

    private long sn;

    public SnUtil(int length){
        sn=length*10;
    }

    public long nextSn(){
        return sn+1;
    }

    public String nextStringSn(){
        return String.valueOf(nextSn()).substring(1);
    }

}
