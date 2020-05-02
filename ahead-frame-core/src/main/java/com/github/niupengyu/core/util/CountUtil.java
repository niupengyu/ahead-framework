package com.github.niupengyu.core.util;

public class CountUtil {

    public void count(int max,CountCall countCall){
        for(int i=0;i<max;i++){
            boolean flag=countCall.doCount(i);
            if(flag){
                break;
            }
        }
    }
    public interface CountCall{
        boolean doCount(int count);
    }
}
