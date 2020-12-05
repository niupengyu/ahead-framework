package com.github.niupengyu.core.util.data;

public class SnUtil {

    private int length;

    private long sn;

    public SnUtil(int length){
        sn=length;
        this.length=length;
        for(int i=0;i<length;i++){
            sn*=10;
        }

    }

    public long nextSn(){
        return sn+1;
    }

    public String nextStringSn(){
        System.out.println(nextSn());
        return String.valueOf(nextSn()).substring(1,length+1);
    }


    public static void main(String[] args) {
        System.out.println(new SnUtil(6).nextStringSn());
    }
}
