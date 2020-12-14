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

    public SnUtil(int start,int length){
        sn=length;
        this.length=length;
        for(int i=0;i<length;i++){
            sn*=10;
        }
        sn+=start;
    }

    public long nextSn(){
        return sn=sn+1;
    }

    public String nextStringSn(){
        String next=String.valueOf(nextSn());
        System.out.println(next);
        return next.substring(1,length+1);
    }


    public static void main(String[] args) {
        System.out.println(new SnUtil(21,6).nextStringSn());
    }
}
